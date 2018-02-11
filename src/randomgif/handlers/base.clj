(ns randomgif.handlers.base
  (:require [hiccup.page :refer [html5]]
            [randomgif.handlers.gif :as gif]))

(defn header
  "Function to display HTML Header"
  []
  [:head (hiccup.page/include-css "styles.css") (hiccup.page/include-js "app.js")
   [:meta {:charset "UTF-8"}]
   [:title "Find GIFs"]])

(defn inputForm
  "Function to display HTML Text Input Form"
  []
  [:div {:class "search-form"}
   [:h1 "Find GIFs!"]
   [:form {:id "myform" :action "/search" :method "POST"}
      [:input {:id "search"
               :name "input"
               :type "search"
               :placeholder "Enter Search Term"}]]])

(defn page [request]
  (html5 {:lang "en"}
   (header)
   (inputForm)
   (gif/displayGIF (gif/fetch-gifs))))
