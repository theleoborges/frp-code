(ns example.views
  (:require
    [hiccup
      [page :refer [html5]]
      [page :refer [include-js]]]))

(defn index-page []
  (html5
   [:head
    [:meta {:charset "utf-8"}]    
    [:title "Hello World"]
    (include-js "http://code.jquery.com/jquery-1.9.1.min.js")
    (include-js "/js/rx.min.js")    
    (include-js "/js/main.js")]
   [:body
    [:h1 "Hello World"]]))