(ns example.services
  (:require [shoreleave.middleware.rpc :refer (defremote)]))

(def poll-ids       [1 2 3 4])
(def poll-questions ["Which is the best music style?"
                     "Which instrument do you like best?"
                     "Who's the biggest Rock 'n Roll Legend?"
                     "How many software engineers does it take to learn FRP?"])

(def polls (atom (mapcat (fn [id question]
                             (for [n (range 5)]
                               {:id id
                                :question question
                                :results {:a (rand-int 1000)
                                          :b (rand-int 1000)
                                          :c (rand-int 1000)}}))
                         poll-ids
                         poll-questions)))

(defremote poll-results []
  (let [resp (first @polls)]
    (swap! polls #(concat (rest %) [(first %)]))
    resp))