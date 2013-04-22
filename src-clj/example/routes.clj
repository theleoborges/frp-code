(ns example.routes
  (:use compojure.core
        example.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [shoreleave.middleware.rpc :refer [defremote wrap-rpc remote-ns]]
            [example.services]))




(defremote ping [test]
  {:results {:a 10
             :b 20
             :c 15}})

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> main-routes
      wrap-rpc
      handler/site))
