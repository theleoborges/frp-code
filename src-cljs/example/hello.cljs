(ns example.hello
  (:use [jayq.core :only [$ css inner ajax]])
  (:use-macros [jayq.macros :only [ready let-deferred]])
  (:require [clojure.browser.repl :as repl]
            [shoreleave.remote])
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
              (.onNext observer (clj->js resp)))               
             (fn [] (.log js/console "Disposed")))))

(-> (results-observable)
    (.subscribe (fn [v]
                  (.log js/console "Received value: " v))))

(ready
 (-> ($ :h1)
     (css {:background "blue"})
     (inner "Yo dawg, I heard you like Clojure so I put Clojure in your javascript so you can FP while you JS!"))
 (rx-stuff)
 (repl/connect "http://localhost:9000/repl"))

;; (-> ($ :h1)
;;     (css {:background "red"}))

;; (-> (.getJSON js/$ feed)
;;     (.done (fn [data]
;;              (.log js/console (->> (js->clj data)
;;                                   "HighlightList"
;;                                   "Highlight"
;;                                   "LinkList"
;;                                   "Link"
;;                                   (map (fn [link]
;;                                          {:title (-> link "Title" "value")}))
;;                                   clj->js)))))