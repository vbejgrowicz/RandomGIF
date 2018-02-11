(ns randomgif.handlers.search
  (:require [hiccup.page :refer [html5]]
            [clojure.string :as string]
            [randomgif.handlers.gif :as gif]
            [randomgif.handlers.base :as base]))

(defn capitalize-words
  "Function to capitilize first letter of each word"
  [string]
  (-> string
      (string/split #" ")
      (->> (map string/capitalize)
           (string/join " "))))

(defn searchResults
  "Function to display Search Results"
  [input]
  (let [results (gif/fetch-gifs input)]
    [:div
      [:h1 (capitalize-words input)]
      (if (empty? results)
        [:div "No matches found"]
        [:ul
          (map (fn [each] [:li (gif/displayGIF each)]) results)])]))

(defn page [request]
  (html5 {:lang "en"}
   (base/header)
   (searchResults (get (:params request) "input"))))
