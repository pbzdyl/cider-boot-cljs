(set-env!
 :source-paths    #{"src"}
 :resource-paths  #{"html" "build"}
 :dependencies    '[[adzerk/boot-cljs          "1.7.228-1"]
                    [adzerk/boot-reload        "0.4.5"]
                    [org.clojure/clojure       "1.8.0"]
                    [org.clojure/clojurescript "1.8.40"]
                    [pandeiro/boot-http        "0.7.0"]
                    [adzerk/boot-cljs-repl   "0.3.0"]
                    [com.cemerick/piggieback "0.2.1"  :scope "test"]
                    [weasel                  "0.7.0"  :scope "test"]
                    [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(require
 '[adzerk.boot-cljs   :refer [cljs]]
 '[adzerk.boot-reload :refer [reload]]
 '[pandeiro.boot-http :refer [serve]]
 '[adzerk.boot-cljs-repl :refer  [cljs-repl start-repl]])

(task-options!
 repl {:middleware '[cemerick.piggieback/wrap-cljs-repl]})

(deftask dev []
  (comp
   (watch)
   (cljs-repl)
   (reload :on-jsload 'frontend.dev/refresh)
   (cljs)
   (serve :port 8080)))

(deftask release []
  (comp
   (cljs :ids #{"main"} :optimizations :advanced)
   (sift :include #{#"(^index\.html|^main\.js)"})
   (target :dir #{"target"})))
