(ns poker.core)

(defn card
  "Model a playing card"
  [rank suit]
  {:rank rank :suit suit})

(def ranks {"2" 2
            "3" 3
            "4" 4
            "5" 5
            "6" 6
            "7" 7
            "8" 8
            "9" 9
            "T" 10
            "J" 11
            "Q" 12
            "K" 13
            "A" 14})

(def one-pair-hand-count [1 1 1 2])
(def two-pair-hand-count [1 2 2])
(def three-of-a-kind-hand-count [1 1 3])
(def full-house-hand-count [2 3])
(def four-of-a-kind-hand-count [1 4])

(defn evaluate-hand-pairs
  "Break a hand into k/v sets to count occurences of cards"
  [hand]
  (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} (map #(:rank %) hand)))

(defn greater-high-card
  "Compare the high card between two different hands"
  [one two]
  (> (apply max (map #(:rank %) one)) (apply max (map #(:rank %) two))))

(defn pair
  "Determine if a hand has a pair"
  [hand]
  (= one-pair-hand-count (vals (evaluate-hand-pairs hand))))

(defn two-pair
  "Determine if a hand has two pair"
  [hand]
  (= two-pair-hand-count (vals (evaluate-hand-pairs hand))))

(defn three-of-a-kind
  "Determine if a hand is three of a kind"
  [hand]
  (= three-of-a-kind-hand-count (vals (evaluate-hand-pairs hand))))

(defn full-house
  "Determine if a hand is a full house"
  [hand]
  (= full-house-hand-count (vals (evaluate-hand-pairs hand))))

(defn four-of-a-kind
  "Determine if a hand is four of a kind"
  [hand]
  (= four-of-a-kind-hand-count (vals (evaluate-hand-pairs hand))))

(defn suited-flush
  "Determine if a hand is a flush"
  [hand]
  (= 1 (count (set (map #(:suit %) hand)))))

(defn straight
  "Determine if a hand is a straight"
  [hand]
  (let [sorted (sort (map #(:rank %) hand))]
    (if (= 4 (- (last sorted) (first sorted)))
      true
      (if (= [2 3 4 5 14] sorted)
        true
        false))))

(defn straight-flush
  "Determine if a hand is a straight flush"
  [hand]
  (if (straight hand)
    (if (suited-flush hand)
      true
      false)
    false))

(defn winner
  "Determine the better of two hands of poker"
  [one two]
  (if (greater-high-card one two)
    one)
  two)
