# 27a SDC UFPB

## Ferramentas:

- https://leiningen.org/#install
- https://www.jetbrains.com/idea/
- https://cursive-ide.com/userguide/

## Libs

- https://github.com/clojure/data.json  
- https://github.com/cgrand/enlive

## Refs

- http://www.clojurenewbieguide.com/
- https://clojuredocs.org/
- https://practical.li/blog/posts/web-scraping-with-clojure-hacking-hacker-news/
- https://medium.com/geekculture/scraping-web-product-data-with-clojure-6594a86c2f00

---

## Intro Clojure

### History

- https://clojure.org/about/history

- Rich Hickey 2007

- Lisp
- Functional Programming
- Hosted JVM (CLR/JS*)
- Concurrency

- 2009 (v1.0) / 2022 (v1.11) ~ 12 anos
    - retro compatível (aka Java)
        - o que foi feito em versões mais antigas continua funcionando nas versões mais recentes
    - 1.10.0-alpha5 drops support for Java 6 and 7 and makes Java 8 the minimum requirement. Compilation will produce
      Java 8 level bytecode (which will not run on earlier versions of Java). This is the first change in bytecode
      version since Clojure 1.6. (2014)

- Killer Feature: Data Processing/Transformation

### Setup

- REPL + Setup IDE
    - compilar/avaliar expressões - **(ALT + SHIFT + E)**
    - compilar/avaliar top form/definicoes/fns (ALT + SHIFT + P)
    - compilar/avaliar todo o ns/load do arquivo para memória **(ALT + SHIFT + L)**
    - switch do *ns* para o arquivo corrente do editor **(ALT + SHIFT + R)**

- estrutura projeto (lein new)

- gestão libs/deps

### Intro

- https://clojure.org/guides/learn/syntax

### values & collections

- https://clojure.org/guides/learn/sequential_colls
- https://clojure.org/guides/learn/hashed_colls

### expressions, fn

- https://clojure.org/guides/learn/functions

### ns, def, let

- https://clojure.org/guides/learn/namespaces

### flow

- https://clojure.org/guides/learn/flow

---

## Functional Toolkit

- Map (n->n)
- Filter (n->[n..0])
- Reduce* (n->A)

- [https://purelyfunctional.tv/courses/3-functional-tools/](https://purelyfunctional.tv/courses/3-functional-tools/)
- [GOTO 2018 • Functional Programming in 40 Minutes • Russ Olsen](https://www.youtube.com/watch?v=0if71HOyVjY)

---

## Demo

```clojure
(ns clj-workshop.core
  (:require [clojure.data.json :as json]))

(def url "https://raw.githubusercontent.com/jeffreylancaster/game-of-thrones/master/data/characters.json")

(def content (slurp url))

(keyword "asdfsdaf")
(def characters (:characters (json/read-str content
                                            :key-fn
                                            keyword)))

(count characters)

(take 3 characters)

(reverse (sort (set (map :characterName characters))))

(filter (fn [character]
          (clojure.string/starts-with? character "King"))
        (map :characterName characters))
```

---

## IMDB scrapper

https://www.imdb.com/chart/top

```clojure
(ns clj-workshop.imdb
  (:require [net.cgrand.enlive-html :as html]
    [clojure.string :as str])
  (:import (java.net URL)))

(def url (URL. "https://www.imdb.com/chart/top"))

(def content (html/html-resource url))

(def rows (html/select content [:table :tbody.lister-list :tr]))
(defn- get-row-content [row path]
  (-> (html/select row path)
      first
      :content
      first))

(defn movie [row]
  (let [title (get-row-content row [:td.titleColumn :a])
        year (get-row-content row [:td.titleColumn :span.secondaryInfo])
        rating (get-row-content row [:td.ratingColumn :strong])]
    {:title  title
     :year   (Integer/parseInt
               (-> year
                   (str/replace "(" "")
                   (str/replace ")" "")))
     :rating (Double/parseDouble rating)}))

;; Exploring  

(def movies (map movie rows))

(take 10 (reverse (sort-by :rating movies)))

(count movies)

;; Have fun!!!!  
;  
;1. Quais filmes tiveram uma avaliação igual ou superior a 9? 
;  
;2. Quais filmes foram publicados em 2018?  
;  
;3. Quantos filmes do Piratas do Caribe estão na lista?  
;  
;4. Qual o filme mais antigo encontrado na lista?  
;  
;5. Imprima uma tabela formatada da lista de filmes utilizando (see clojure.pprint).
```

---


