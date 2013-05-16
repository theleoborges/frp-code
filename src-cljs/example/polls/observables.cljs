(ns example.polls.observables
  (:require [shoreleave.remote])
  (:require-macros [shoreleave.remotes.macros :as srm]))


;;
;; Demo 1
;;
(defn results-observable
  "Returns an Observable that yields server-side questions/results"
  []
  (.create js/Rx.Observable
           (fn [observer]
             (srm/rpc
              (poll-results) [resp]
              (.onNext observer resp))               
             (fn [] (.log js/console "Disposed")))))

(def results-connectable
  "Zips results-observable with itself, but shifter by 1.
  This simulates a 'buffer' or 'window' of results"
  (let [obs (-> js/Rx.Observable
                (.interval 2000)
                (.selectMany results-observable)
                (.publish)
                (.refCount))
        obs-1 (.skip obs 1)]
    (.zip obs obs-1 (fn [prev curr]
                      {:prev prev
                       :curr curr}))))




;;
;; Demo 1.1
;; 
(def results-buffer
  "Returns an Observable with results buffered into a 2-elemnt vector"
  (-> js/Rx.Observable
      (.interval 2000)
      (.selectMany results-observable)
      (.bufferWithCount 2)))