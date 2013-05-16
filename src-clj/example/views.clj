(ns example.views
  (:require [hiccup
             [page :refer [html5]]
             [page :refer [include-js]]]))

(defn index-page []
  (html5
   [:head
    [:meta {:charset "utf-8"}]    
    [:title "Hello World"]
    (include-js "http://code.jquery.com/jquery-1.9.1.min.js")
    (include-js "/js/rx.min.js")
    (include-js "/js/rx.binding.min.js")    
    (include-js "/js/rx.time.min.js")
    (include-js "/js/rx.aggregates.min.js")
    (include-js "/js/rx.coincidence.min.js")
    (include-js "/js/d3.min.js") 
    (include-js "/js/main.js")]
   [:body
    [:h1
     [:span {:id "countdown"}]
     [:p]
     [:span {:id "question-text"}]]
    [:div {:id "graph"}]    
    (include-js "/js/graph.js")]))


