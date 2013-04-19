(ns example.hello
  (:use [jayq.core :only [$ css inner ajax]])
  (:use-macros [jayq.macros :only [ready let-deferred]]))

(def feed "http://data.dev.ninemsn.com.au/Services/Service.axd?ServiceName=Highlight&ServiceAction=GetWithEndDate&SiteID=6670393&SectionID=0&SubSectionID=0&GroupID=6746725&ServiceFormat=jsonp&callback=?")

(defn fetch-stuff []
  (let-deferred
   [a (.getJSON js/$ feed)
    b (.getJSON js/$ feed)]
   (.log js/console "a: " a)
   (.log js/console "b: " b)
   (.log js/console js/Rx.Observable)))


(defn rx-stuff []
  (-> (.fromArray js/Rx.Observable (clj->js [1 2 3]))
      (.subscribe (fn [v]
                    (.log js/console "Received value: " v)))))

(ready
 (-> ($ :h1)
     (css {:background "blue"})
     (inner "Yo dawg, I heard you like Clojure so I put Clojure in your javascript so you can FP while you JS!"))
 (rx-stuff)
 (fetch-stuff))


