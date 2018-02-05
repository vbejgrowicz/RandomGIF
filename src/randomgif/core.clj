(ns randomgif.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.handler.dump :refer [handle-dump]]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [randomgif.handlers.base :as base]))

(defroutes app
  (GET "/" [] base/home)
  (GET "/debug" [] handle-dump)
  (route/resources "/")
  (route/not-found (html [:h1 "Page Not Found"])))
  (POST "/search" [req] base/search)

(defn -main
  "The entry point for the RandomGIF App"
  []
  (jetty/run-jetty (wrap-params #'app)
    {:port 8000}))

(defn -dev-main
  "The dev entry point for the RandomGIF App with app reload functionality"
  []
  (jetty/run-jetty (wrap-reload (wrap-params #'app))
    {:port 8000}))
