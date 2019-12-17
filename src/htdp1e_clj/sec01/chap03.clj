(ns htdp1e-clj.sec01.chap03
  (:require [clojure.test :refer :all]
            [same :refer [ish? zeroish?]]))

;;;; I - Processing Simple Forms of Data
;;;; 3 - Programs are Function Plus Variable Definitions
;;   pg. 52


;;; 3.1 Composing functions

;; Example

;; Imagine the owner of a movie theater who has complete freedom
;; in setting ticket prices. The more he charges, the fewer
;; the people who can afford tickets. In a recent experiment the
;; owner determined a precise relationship between the price of a
;; ticket and average attendance. At a price of $5.00 per ticket, 120
;; people attend a performance. Decreasing the price by a dime
;; ($. 10) increases attendance by 15. Unfortunately, the increased
;; attendance also comes at an increased cost. Every performance
;; costs the owner $180. Each attendee costs another four cents
;; ($0.04) . The owner would like to know the exact relationship between
;; profit and ticket price so that he can determine the price
;; at which he can make the highest profit.

;; attendees : number -> number
;; Compute the number of `attendees` given `ticket-price`.
;; 5.00 -> 120
;; 4.00 -> 270
;; 3.00 -> 420
;; 2.00 -> 570
(defn attendees [ticket-price]
  (+ 120 (* 150 (- 5.00 ticket-price))))

(deftest test-attendees
  (is (= 120.0 (attendees 5.00)))
  (is (= 270.0 (attendees 4.00)))
  (is (= 420.0 (attendees 3.00)))
  (is (= 570.0 (attendees 2.00))))

;; cost : number -> number
;; Compute the `cost` given `ticket-price`
;; formula -> 180 + (attendees * 0.04)
;; (2.00 -> 570) -> 180 + 570 * 0.04 => 202.8
;; (3.00 -> 420) -> 180 + 420 * 0.04 => 196.8
;; (4.00 -> 270) -> 180 + 270 * 0.04 => 190.8
;; (5.00 -> 120) -> 180 + 120 * 0.04 => 184.8
(defn cost [ticket-price]
  (+ 180 (* (attendees ticket-price) 0.04)))

(deftest test-cost
  (is (= 202.8 (cost 2.00)))
  (is (= 196.8 (cost 3.00)))
  (is (= 190.8 (cost 4.00)))
  (is (= 184.8 (cost 5.00))))

;; revenue : number -> number
;; Compute the `revenue`, given the `ticket-price`
;; formula -> ticket-price * attendees
;; 2.00 * 570 => 1140.00
;; 3.00 * 420 => 1260.00
;; 4.00 * 270 => 1080.00
;; 5.00 * 120 =>  600.00

(defn revenue [ticket-price]
  (* ticket-price (attendees ticket-price)))

;; profit : number -> number
;; Compute the `profit` as the difference between `revenue` and `costs`
;; at some ticket price
;; formula -> revenue - cost
;; 2.00 ->  1140.00 - 202.80 =>  937.20
;; 3.00 ->  1260.00 - 196.80 => 1063.2
;; 4.00 ->  1080.00 - 190.80 =>  889.2
;; 5.00 ->   600.00 - 184.80 =>  415.2

(defn profit [ticket-price]
  (- (revenue ticket-price)
     (cost ticket-price)))

(deftest test-profit
  (is (= 937.2 (profit 2.00)))
  (is (= 1063.2 (profit 3.00)))
  (is (= 889.2 (profit 4.00)))
  (is (= 415.2 (profit 5.00))))


;;; Exercises 3.1

;; Exercise 3.1.1 The next step is to make up examples for each of the functions.
;; Determine how many attendees can afford a show at a ticket price
;; of $3.00, $4.00, and $5.00. Use the examples to formulate a general rule
;; that shows how to compute the number of attendees from the ticket price.
;; Make up more examples if needed.

;; formula - decrease price by 0.10 increases attendance by 15.
;; (+ 120 (* 150 (- 5.00 x)))

;; 5.00 -> 120
;; 4.80 -> 135
;; 4.60 -> 150
;; decrease by 1.00 increase 150
;; 4.00 -> 270
;; 3.00 -> 420
;; 2.00 -> 570


;; Exercise 3.1.2 Use the results of exercise 3.1.1 to determine how much it
;; costs to run a show at $3.00, $4.00, and $5.00. Also determine how much
;; revenue each show produces at those prices. Finally, figure out how much
;; profit the monopolistic movie owner can make with each show. Which is
;; the best price (of these three) for maximizing the profit

;; cost
;; formula -> 180 + (attendees * 0.04)
;; (2.00 -> 570) -> 180 + (570 * 0.04) => 202.8
;; (3.00 -> 420) -> 180 + (420 * 0.04) => 196.8
;; (4.00 -> 270) -> 180 + (270 * 0.04) => 190.8
;; (5.00 -> 120) -> 180 + (120 * 0.04) => 184.8

;; revenue
;; formula -> ticket-price * attendees
;; 2.00 * 570 => 1140.00
;; 3.00 * 420 => 1260.00
;; 4.00 * 270 => 1080.00
;; 5.00 * 120 =>  600.00

;; profit
;; formula -> revenue - cost
;; 2.00 ->  1140.00 - 202.80 =>  937.20
;; 3.00 ->  1260.00 - 196.80 => 1063.20  ***
;; 4.00 ->  1080.00 - 190.80 =>  889.20
;; 5.00 ->   600.00 - 184.80 =>  415.20
