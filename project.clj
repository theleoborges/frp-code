(defproject cljsbuild-example-simple "0.0.1"
  :description "Sample code for my FRP Talk"
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.0"]
                 [jayq "2.3.0"]
                 [com.cemerick/piggieback "0.0.4"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [shoreleave/shoreleave-remote-ring "0.3.0"]
                 [enfocus "1.0.1"]
                 [crate "0.2.4"]
				 [rx-cljs "0.0.1-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-ring "0.8.4"]]
  :cljsbuild
  {:builds [{;;:incremental false
             :source-paths ["src-cljs"]
             :compiler {:output-to "resources/public/js/main.js"
                        :optimizations :whitespace
                        :pretty-print true}}]}
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :ring {:handler example.routes/app})
