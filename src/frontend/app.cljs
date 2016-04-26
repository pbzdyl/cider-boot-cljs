(ns frontend.app)

(enable-console-print!)

(defn init []
  (.log js/console "Init"))

(println (cljs.core/+ 10 10))
