(ns randomgif.handlers.base
  (:require [hiccup.page :refer [html5]]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]))

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

(defn get-GIF-src
  [gif]
  (-> gif
    (get "images")
    (get "fixed_height_downsampled")
    (get "url")))

(defn displayGIF
  [gif]
  (if (empty? (get gif "images"))
    [:img {:src (get gif "fixed_height_downsampled_url")}]
    [:li
      [:img {:src (get (get (get gif "images") "fixed_height_downsampled") "url")}]]))

(defn searchURL
  ([search limit]
   (str "http://api.giphy.com/v1/gifs/search?q=" search "&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&rating=g&lang=en&limit=" limit))
  ([search]
   (str "http://api.giphy.com/v1/gifs/search?q=" search "&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&rating=g&lang=en"))
  ([]
   (str "http://api.giphy.com/v1/gifs/random?&api_key=Yk5zrWe68FsF56yeZkDNMvZHIdM7ePbh&rating=g&lang=en")))

(defn parseResponse
  [response]
  (get (json/read-str (:body response)) "data"))

(defn handleSearch
  "Function to handle search request"
  ([{input "input"}]
   (let [response (client/get (searchURL input))]
     (parseResponse response)))
  ([]
   (let [response (client/get (searchURL))]
     (parseResponse response))))

(defn capitalize-words
  "Function to capitilize first letter of each word"
  [string]
  (string/join " " (map string/capitalize (string/split string #" "))))

(defn searchResults
  "Function to display Search Results"
  [search]
  (let [results (handleSearch search)]
    [:div
      [:h1 (capitalize-words (get search "input"))]
      (if (empty? results)
        [:div "No matches found"]
        [:ul
          (map (fn [each] (displayGIF each)) results)])]))

(defn home [request]
  (html5 {:lang "en"}
   (header)
   (inputForm)
   (displayGIF (handleSearch))))

(defn search [request]
  (html5 {:lang "en"}
   (header)
   (searchResults (:params request))))
