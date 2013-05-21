(ns example.polls.observables
  (:require [shoreleave.remote]
	  		[rx-cljs.observable :as o])
  (:require-macros [shoreleave.remotes.macros :as srm]))


;;
;; Demo 1
;;
(defn results-observable
  "Returns an Observable that yields server-side questions/results"
  []
  (o/create (fn [observer]
              (srm/rpc
               (poll-results) [resp]
               (o/on-next observer resp))               
              (fn [] (.log js/console "Disposed")))))

(def results-connectable
  "Zips results-observable with itself, but shifter by 1.
  This simulates a 'buffer' or 'window' of results"
  (let [obs (-> (o/interval 2000)
			    (o/select-many results-observable)
			    o/publish
			    o/ref-count)
        obs-1 (o/skip obs 1)]
    (o/zip obs obs-1 (fn [prev curr]
                      {:prev prev
                       :curr curr}))))




;;
;; Demo 1.1
;; 
(def results-buffer
  "Returns an Observable with results buffered into a 2-element vector"
  (-> (o/interval 2000)
      (o/select-many results-observable)
      (o/buffer-with-count 2)))