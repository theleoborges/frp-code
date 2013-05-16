(ns example.repl
  (:require cljs.repl.browser))

(defn start! []
  (cemerick.piggieback/cljs-repl
   :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
               cljs.repl/-setup)))