(ns htdp1e-clj.core-test
  (:require [clojure.test :refer :all]
            [htdp1e-clj.core :refer :all]
            [same :refer [ish? zeroish?]]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(is (= 4 (+ 2 2)))
(is (instance? java.lang.Long 256))
(is (.startsWith "abcde" "ab"))

(is (thrown? ArithmeticException (/ 1 0)))

(is (thrown-with-msg? ArithmeticException #"Opps!! Divide by zero"
                      (/ 1 0)))

;;; Documenting tests

(is (= 5 (+ 2 2)) "Crazy arithmetic !!")

(testing "Arithmetic"
  (testing "with positive integers"
    (is (= 4 (+ 2 2)))
    (is (= 7 (+ 3 4))))
  (testing "with negative integers"
    (is (= -4 (+ -2 -2)))
    (is (= -1 (+ 3 -4)))))


;;; Defining tests

(with-test
  (defn my-function [x y]
    (+ x y))
  (is (= 4 (my-function 2 2)))
  (is (= 7 (my-function 3 4))))

(deftest addition
  (is (= 4 (+ 2 2)))
  (is (= 7 (+ 3 4))))

(deftest subtraction
  (is (= 1 (- 4 3)))
  (is (= 3 (- 7 4))))


(addition)
(subtraction)

(deftest arithmetic
  (addition)
  (subtraction))


;;; test 'ish?

(deftest ish-test
  (let [one-ish (-> 1.0 (/ 49) (* 49))]
    ;;(is (==  one-ish 1.0))
    (is (ish? one-ish 1.0))))

(defn f [x]
  (* (/ 1. x) x))

(deftest f-test
  (is (ish? 1.0 (f 49.0))))


(defn g [x]
  {:a x
   :b [(* x x) (Math/sqrt x)]})

#_(deftest g-test
    (is (ish? {:a 23 :b [529 4.7958315233]}
              (g 23.0))))

x1
;;;; Running tests
*ns*
(run-tests 'htdp1e-clj.core-test)
;;(run-tests 'htdp1e-clj.sec01.chap02)





