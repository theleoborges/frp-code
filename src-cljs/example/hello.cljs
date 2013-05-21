(ns example.hello
  (:use [jayq.core :only [$ css inner ajax append remove]])
  (:use-macros [jayq.macros :only [ready let-deferred]]
               [crate.def-macros :only [defpartial]])
  (:require [clojure.browser.repl :as repl]
            [shoreleave.remote]
            [crate.core :as crate]
            [example.polls.observables :as obs]
			[rx-cljs.observable :as o]))

(defn render-poll [{:keys [results question]}]
  (inner ($ :#countdown) "")
  (inner ($ :#question-text) question)
  (js/updateGraph (clj->js (vals results))))

(defn countdown-and-do [n f]
  (if (> n 0)
    (do (.log js/console "Countdown: " n)
        (inner ($ :#countdown)
               (str "A new question will be available in " n " secs. In the meantime check out the results from our previous poll."))
        (.setTimeout js/window
                     (fn [] (countdown-and-do (dec n) f))
                     1000))
    (do (.log js/console "All done.")        
        (f))))

;;
;; Demo 1
;;
(defn start []
  (.log js/console "Starting.")  
  (let [tk (-> obs/results-connectable
               (o/subscribe (fn [resp]
                             (if (= (-> resp :curr :id)
                                    (-> resp :prev :id))
                               (render-poll (:curr resp))
                               (do (o/dispose js/tk)
                                   (countdown-and-do 10 start))))))]))



;;
;; Demo 1.1
;;
(defn start-1 []
  (let [token (-> obs/results-buffer
                  (o/subscribe (fn [[prev curr]]
                                (if (= (:id prev)
                                       (:id curr))
                                  (render-poll curr)
                                  (do (o/dispose js/token)
                                      (countdown-and-do 10 start-1))))))]))

;;(start-1)

(ready
 (-> ($ :#question-text)
     (inner "Nothing here. Yet."))
 (start)
 (repl/connect "http://localhost:9000/repl"))