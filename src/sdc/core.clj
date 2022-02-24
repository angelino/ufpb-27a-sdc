(ns sdc.core
  (:require [clojure.data.json :as json]))

1
2.3
"asdf"
:keyword

[12 3 ""]

'(12 3 "")

{"nome"  "Lucas"
 "idade" 33}

#{1 2 3 4 5}

(def pessoa {:nome   "Lucas"
             "idade" 33})
(:nome pessoa)

pessoa

(assoc pessoa "profissao" "Desenvolvedor")

(let [p2 (assoc pessoa "profissao" "Desenvolvedor")]
  ; .......
  (println p2))
;p2 << deixa de ser conhecida no contexto (fora de escopo)

;;
;; Demonstração
;;

(def content (slurp "https://raw.githubusercontent.com/jeffreylancaster/game-of-thrones/master/data/characters.json"))

(def characters (:characters (json/read-str content :key-fn keyword)))

(reverse (sort (set (map :characterName characters))))

(frequencies (map :characterName characters))

(defn king? [name]
  (clojure.string/starts-with? name "King"))

(filter king? (map :characterName characters))



