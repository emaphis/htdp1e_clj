(ns htdp1e-clj.sec01.chap05
  (:require [clojure.test :refer :all]
            [same :refer [ish? zeroish?]]))

;;;; I - Processing Simple Forms of Data
;;;; 5 - Symbolic Information - pg 77.

(= 'Hello 'Hello)


(defn reply [s]
  (cond
    (= s 'GoodMorning) 'Hi
    (= s 'HowAreYou?) 'Fine
    (= s 'GoodAfternoon) 'INeedANap
    (= s 'GoodEvening) 'BoyAmITired))

(reply 'GoodMorning) ;; => Hi
(reply 'GoodAfternoon) ;; => INeedANap

;;; Exercises 5.1 - Finger Exercises with Symbols

;; Exercise 5.1.1 Evaluate (reply 'HowAreYou?) by hand and with DrScheme's
;; stepper. Formulate a complete set of examples for reply as boolean expressions
;; (using symbol=?).

(reply 'HowAreYou?)

(cond
  (= 'HowAreYou? 'GoodMorning) 'Hi
  (= 'HowAreYou? 'HowAreYou?) 'Fine
  (= 'HowAreYou? 'GoodAfternoon) 'INeedANap
  (= 'HowAreYou? 'GoodEvening) 'BoyAmITired)

(cond
  false 'Hi
  (= 'HowAreYou? 'HowAreYou?) 'Fine
  (= 'HowAreYou? 'GoodAfternoon) 'INeedANap
  (= 'HowAreYou? 'GoodEvening) 'BoyAmITired)

(cond
  (= 'HowAreYou? 'HowAreYou?) 'Fine
  (= 'HowAreYou? 'GoodAfternoon) 'INeedANap
  (= 'HowAreYou? 'GoodEvening) 'BoyAmITired)

(cond
  true  'Fine
  (= 'HowAreYou? 'GoodAfternoon) 'INeedANap
  (= 'HowAreYou? 'GoodEvening) 'BoyAmITired)

(deftest test-reply
  (is (= 'Hi (reply 'GoodMorning)))
  (is (= 'Fine (reply 'HowAreYou?)))
  (is (= 'INeedANap (reply 'GoodAfternoon)))
  (is (= 'BoyAmITired (reply 'GoodEvening))))


;; Exercise 5.1.2 Develop the function `check-guess`. It consumes two numbers,
;; guess and target. Depending on how guess relates to target, the function produces
;; one of the following three answers: 'TooSmall, 'Perfect, or 'TooLarge.
;;    The function implements one part of a two-player number guessing
;; game. One player picks a random number between 0 and 99999. The other
;; player's goal is to determine this number, called target, with the least number
;; of guesses. To each guess, the first player responds with one of the three
;; responses that `check-guess` implements.
;;   The function `check-guess` and the teachpack guess.ss implement the first
;; player. The teachpack picks the random number, pops up a window in
;; which the second player can choose digits, and hands over the guess and the
;; target to `check-guess`. To play the game, set the teachpack to guess.ss using
;; the Language I Set teachpack option. Then evaluate the expression

;;   (guess-with-gui check-guess)

;; after `check-guess` has been thoroughly tested

;; check-guess : number number -> symbol
;; Compares the magnitude of two numbers and produce symbol
;; 'TooSmall, ' Perfect, or 'TooLarge 
(defn check-guess [guess target]
  (cond
    (< guess target) 'TooSmall
    (= guess target) 'Perfect
    :else 'TooLarge))

(deftest test-check-guess
  (is (= 'TooSmall (check-guess 5 10)))
  (is (= 'Perfect (check-guess 10 10)))
  (is (= 'TooLarge (check-guess 20 10))))


;; Exercise 5.1.3 Develop the function `check-guess3`. It implements a larger
;; portion of the number guessing game of exercise 5.1.2 than the function
;; `check-guess`. Now the teachpack hands over the digits that the user guesses,
;; not the number that they form.

;; To simplify the problem a little bit, the game works with only three
;; numbers. Thus, `check-guess3` consumes three digits and a number. The first
;; digit is the least significant, the third one is the most significant. The number
;; is called target and represents the randomly chosen number. Depending
;; on how guess, the number determined by the three digits, relates to target,
;; `check-guess3` produces one of the following three answers: 'TooSmall, 'Perfect,
;; or 'TooLarge.

;;   The rest of the game is still implemented by guess.ss. To play the game
;; with check-guess3, evaluate

;; (guess-with-gui-3 check-guess3)

;; after the function has been thoroughly tested.
;; Hint: Remember to develop an auxiliary function per concept


;; convert-num : number number number -> number
;; given numbers representing ones, tens, hundreds convert to a three digit number
(defn convert-num [ones tens hundreds]
  (+ ones (* tens 10) (* hundreds 100)))

(deftest test-conver-num
  (is (= 123 (convert-num 3 2 1)))
  (is (= 321 (convert-num 1 2 3)))
  (is (= 0 (convert-num 0 0 0))))

;; check-guess3 : number number number number -> symbol
;;
(defn check-guess3 [ones tens hundreds guess]
  (check-guess (convert-num ones tens hundreds) guess))

(deftest test-check-guess3
  (is (= 'TooSmall  (check-guess3 3 2 1 124)))
  (is (= 'Perfect (check-guess3 3 2 1 123)))
  (is (= 'TooLarge (check-guess3 3 2 1 122))))


;; Exercise 5.1.4 Develop `what-kind`. The function consumes the coefficients a,
;; b, and c of a quadratic equation. It then determines whether the equation is
;; degenerate and, if not, how many solutions the equation has. The function
;; produces one of four symbols: 'degenerate, 'two, 'one, or ' none.
;; Hint: Compare with exercise 4.4.4.

;; descr : number number number number -> number
;; Calculates the descriminant of a quadratice equation of a b c.
(defn discr [a b c]
  (- (* b b) (* 4 a c)))

(deftest test-discr
  (is (= 124 (discr 6 10 -1)))  ; two solutions
  (is (= 0 (discr 9 12 4)))     ; one solutions
  (is (= -8 (discr 3 4 2))))    ; zero solutions

;; how-many : number number number -> symbol
;; computes the number of solutions to a quadratic equation.
(defn how-many [a b c]
  (cond
    (= a 0) 'degenerate
    (> (discr a b c) 0) 'two
    (= (discr a b c) 0) 'one
    (< (discr a b c) 0) 'none))

(deftest test-how-many
  (is (= 'two (how-many 6 10 -1)))
  (is (= 'one (how-many 9 12 4)))
  (is (= 'none (how-many 3 4 2)))
  (is (= 'degenerate (how-many 0 1 2))))


;; Exercise 5.1.5 Develop the function check-color. It implements a key portion
;; of a color guessing game. One player picks two colors for two squares;
;; we call those targets. The other one tries to guess which color is assigned
;; to which square; they are guesses. The first player's response to a guess is
;; to check the colors and to produce one of the following answers:

;; 1. 'Perfect, if the first target is equal to the first guess and the second
;; target is equal to the second guess;

;; 2. 'OneColorAtCorrectPosition, if the first guess is equal to the first target
;; or the second guess is equal to the second target;

;; 3. 'OneColorOccurs, if either guess is one of the two targets; and

;; 4. 'NothingCorrect, otherwise.

;; These four answers are the only answers that the first player gives. The
;; second player is to guess the two chosen target colors with as few guesses
;; as possible.

;; The function check-color simulates the first player's checking action. It
;; consumes four colors; for simplicity, we assume that a color is a symbol,
;; say, 'red. The first two arguments to check-color are "targets," the latter two
;; are "guesses." The function produces one of the four answers.

;; When the function is tested, use the teachpack to master.ss to play the
;; color-guessing game The teachpack provides the function master. Evaluate
;; (master check-color) and choose colors with the mouse.

;; check-color : symbol symbol symbol symbol -> symbol
;;

(defn check-color [target-1 target-2 guess-1 guess-2]
  (cond
    (and (= guess-1 target-1) (= guess-2 guess-2)) 'Perfect
    (or (= guess-1 target-1) (= guess-2 target-2)) 'OneColorAtCorrectPosition
    (or (= guess-1 target-2) (= guess-2 target-1)) 'OneColorOccurs
    :else 'NothingCorrect))

(deftest test-check-color
  (is (= 'Perfect (check-color 'red 'blue 'red 'blue)))
  (is (= 'OneColorOccurs (check-color 'red 'blue 'green 'red)))
  (is (= 'OneColorOccurs (check-color 'red 'blue 'blue 'green)))
  (is (= 'OneColorAtCorrectPosition (check-color 'red 'blue 'green 'blue)))
  (is (= 'NothingCorrect (check-color 'red 'blue 'green 'yellow))))
