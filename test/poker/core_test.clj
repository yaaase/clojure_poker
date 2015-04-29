(ns poker.core-test
  (:require [clojure.test :refer :all]
            [poker.core :refer :all]))

(def hand-one [(card 2 "S") (card 4 "S") (card 6 "C") (card 8 "C") (card 10 "S")])
(def hand-two [(card 14 "C") (card 2 "S") (card 4 "S") (card 6 "C") (card 9 "C")])

(def hand-one-pair [(card 2 "S") (card 2 "C") (card 4 "S") (card 6 "C") (card 8 "S")])
(def hand-two-aces [(card 14 "A") (card 14 "C") (card 2 "S") (card 4 "S") (card 9 "H")])
(def hand-two-pair [(card 2 "S") (card 2 "C") (card 4 "S") (card 4 "C") (card 8 "S")])
(def hand-three-of-a-kind [(card 2 "S") (card 2 "C") (card 2 "H") (card 6 "C") (card 7 "S")])
(def hand-full-house [(card 2 "S") (card 2 "C") (card 2 "D") (card 6 "D") (card 6 "H")])
(def hand-four-of-a-kind [(card 2 "C") (card 2 "H") (card 2 "S") (card 2 "D") (card 7 "S")])
(def hand-flush [(card 2 "H") (card 3 "H") (card 4 "H") (card 5 "H") (card 9 "H")])
(def hand-straight [(card 2 "H") (card 3 "C") (card 4 "S") (card 5 "D") (card 6 "S")])
(def hand-straight-ace-low [(card 14 "S") (card 2 "S") (card 3 "C") (card 4 "H") (card 5 "S")])
(def hand-straight-flush [(card 2 "S") (card 3 "S") (card 4 "S") (card 5 "S") (card 6 "S")])

(deftest high-card-test
  (testing "high card wins"
    (is (= hand-two (winner hand-one hand-two)))
    (is (= hand-two (winner hand-two hand-one)))))

(deftest one-pair-test
  (testing "it identifies one pair"
    (is (= false (pair hand-one)))
    (is (= true (pair hand-one-pair)))))

(deftest two-pair-test
  (testing "it identifies two pair"
    (is (= false (two-pair hand-one-pair)))
    (is (= true (two-pair hand-two-pair)))))

(deftest three-of-a-kind-test
  (testing "it identifies three of a kind"
    (is (= false (three-of-a-kind hand-one-pair)))
    (is (= true (three-of-a-kind hand-three-of-a-kind)))))

(deftest full-house-test
  (testing "it identifies a full house"
    (is (= false (full-house hand-three-of-a-kind)))
    (is (= true (full-house hand-full-house)))))

(deftest four-of-a-kind-test
  (testing "it identifies four of a kind"
    (is (= false (four-of-a-kind hand-full-house)))
    (is (= true (four-of-a-kind hand-four-of-a-kind)))))

(deftest flush-test
  (testing "it identifies a flush"
    (is (= false (suited-flush hand-one-pair)))
    (is (= true (suited-flush hand-flush)))))

(deftest straight-test
  (testing "it identifies a straight"
    (is (= false (straight hand-one-pair)))
    (is (= true (straight hand-straight)))
    (is (= true (straight hand-straight-ace-low)))))

(deftest straight-flush-test
  (testing "it identifies a straight flush"
    (is (= false (straight-flush hand-straight)))
    (is (= false (straight-flush hand-flush)))
    (is (= true (straight-flush hand-straight-flush)))))

(deftest better-one-pair-test
  (testing "it knows which pair is better"
    ; TODO this is a false positive, write a better test
    (is (= hand-two-aces (winner hand-two-aces hand-one)))))
