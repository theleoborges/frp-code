(ns example.hello
  (:use [jayq.core :only [$ css inner ajax append remove]])
  (:use-macros [jayq.macros :only [ready let-deferred]]
               [crate.def-macros :only [defpartial]])
  (:require [clojure.browser.repl :as repl]
            [shoreleave.remote]
            [crate.core :as crate])
  (:require-macros [shoreleave.remotes.macros :as srm]))

(def feed "http://data.dev.ninemsn.com.au/Services/Service.axd?ServiceName=Highlight&ServiceAction=GetWithEndDate&SiteID=6670393&SectionID=0&SubSectionID=0&GroupID=6746725&ServiceFormat=jsonp&callback=?")

(defn fetch-stuff []
  (let-deferred
   [a (.getJSON js/$ feed)
    b (.getJSON js/$ feed)]
   (.log js/console "a: " a)
   (.log js/console "b: " b)))

(defn rx-stuff []
  (-> js/Rx.Observable
      (.fromArray (clj->js [1 2 3]))
      (.subscribe (fn [v]
                    (.log js/console "Received value: " v)))))

(defn results-observable []
  (.create js/Rx.Observable
           (fn [observer]
             (srm/rpc
              (poll-results) [resp]
              (.onNext observer resp))               
             (fn [] (.log js/console "Disposed")))))

(defpartial results-partial [results]
  [:ul
   (for [[label votes] results]
     [:li (str label " - " votes)])])

(defn render-poll [{:keys [results question]}]
  (inner ($ :h1) question)
  (inner ($ "#poll-results") (results-partial results)))

(defn compute-results-key [resp]
  (clojure.string/join "" (map (fn [[k v]] (str k v)) (:results resp))))

(def results-connectable
  (let [obs (-> js/Rx.Observable
                        (.interval 2000)
                        (.selectMany results-observable)
                        (.publish)
                        (.refCount))
        obs-1 (.skip obs 1)]
    (.zip obs obs-1 (fn [prev curr]
                      {:prev prev
                       :curr curr}))))

(defn countdown-and-do [n f]
  (if (> n 0)
    (do (.log js/console "Countdown: " n)        
        (.setTimeout js/window
                     (fn [] (countdown-and-do (dec n) f))
                     1000))
    (do (.log js/console "All done.")
        (f))))

;;(countdown 10)

(defn start []
  (.log js/console "Starting.")  
  (let [tk (-> results-connectable
               (.subscribe (fn [resp]
                             (if (= (-> resp :curr :id)
                                    (-> resp :prev :id))
                               (do (.log js/console "Value is: " (clj->js resp))
                                   (render-poll (:curr resp)))
                               (do (.log js/console "New poll. Dispose and countdown")
                                   (.dispose js/tk)
                                   (countdown-and-do 10 start))))))]))

;;(start)

(def tk (-> results-connectable
            (.subscribe (fn [resp]
                          (if (= (-> resp :curr :id)
                                 (-> resp :prev :id))
                            (do (.log js/console "Value is: " (clj->js resp))
                                (render-poll (:curr resp)))
                            (do ))))))
(.dispose tk)


(.setTimeout js/window
             (fn [] (.log js/console "should restart here"))
             1000)

;; (def token (-> results-connectable
;;                (.distinctUntilChanged compute-results-key)
;;                (.subscribe (fn [resp]
;;                              (.log js/console "Rendering results: " (clj->js resp))
;;                              (render-results (:results resp))))))

;; (def token-1 (-> results-connectable
;;                 (.subscribe (fn [resp]
;;                               (.log js/console "Last updated on: " (js/Date.))
;;                               (-> ($ "#last-updated-at")
;;                                   (inner (js/Date.)))))))

;; (def connection (.connect results-connectable))
;; (.dispose connection)
;; (.dispose token)
;; (.dispose token-1)




;; (let [observable (results-observable)]
;;   (-> observable
;;       (.distinctUntilChanged)
;;       (.subscribe (fn [resp]
;;                     (.log js/console "Rendering results: " (clj->js resp))
;;                     (render-results (:results resp)))))
;;   (.subscribe observable
;;               (fn [_]
;;                 (.log js/console "Last updated on: " (js/Date.))
;;                 (-> ($ "#last-updated-at")
;;                     (inner (js/Date.))))))





(ready
 (-> ($ :h1)
     (css {:background "blue"})
     (inner "Yo dawg, I heard you like Clojure so I put Clojure in your javascript so you can FP while you JS!"))
 (rx-stuff)
 (repl/connect "http://localhost:9000/repl"))