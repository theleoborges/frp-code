(ns example.services
  (:require [shoreleave.middleware.rpc :refer (defremote)]))

(defremote poll-results []
  {:results {:a 15
             :b 25
             :c 20}})