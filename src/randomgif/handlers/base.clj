(ns randomgif.handlers.base
  (:require [hiccup.page :refer [html5]]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn header
  "Function to display HTML Header"
  []
  [:head (hiccup.page/include-css "styles.css")
   [:meta {:charset "UTF-8"}]
   [:title "Find GIFs"]])

(defn inputForm
  "Function to display HTML Text Input Form"
  []
  [:div {:class "search-form"}
   [:div {:class "form-header"} "Find a GIF!"]
   [:form {:action "/search" :method "GET"}
    [:input {:id "query"
             :type "text"
             :placeholder "Enter Search Term"}]]])

(defn home [request]
  (html5 {:lang "en"}
   (header)
   (inputForm)))
