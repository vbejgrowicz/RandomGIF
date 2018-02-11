(ns randomgif.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.handler.dump :refer [handle-dump]]
            [ring.util.response :refer [redirect]]
            [randomgif.handlers.base :as base]
            [randomgif.handlers.search :as search]))

(defn redirect-unknown-route
  [request]
  (redirect "/"))

(defroutes app
  (route/resources "/")
  (GET "/" [req] base/page)
  (POST "/search" [req] search/page)
  (GET "/debug" [req] handle-dump)
  (ANY "*" [req] redirect-unknown-route))

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
