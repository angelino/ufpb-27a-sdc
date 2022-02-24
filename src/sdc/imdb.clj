(ns sdc.imdb
  (:require [net.cgrand.enlive-html :as html])
  (:import (java.net URL)))

(def url (new URL "https://www.imdb.com/chart/top"))

(def content (html/html-resource url))

(def rows (html/select content [:table :tbody.lister-list :tr]))

(defn movie [row]
  {:title  (first (:content (first (html/select row [:td.titleColumn :a]))))
   :year   (first (:content (first (html/select row [:td.titleColumn :span.secondaryInfo]))))
   :rating (first (:content (first (html/select row [:td.ratingColumn :strong]))))})

(movie (last rows))

(def movies (map movie rows))

(take 10 (reverse (sort-by :rating movies)))
