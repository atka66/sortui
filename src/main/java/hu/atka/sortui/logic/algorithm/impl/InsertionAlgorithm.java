package hu.atka.sortui.logic.algorithm.impl;

import hu.atka.sortui.logic.algorithm.Algorithm;
import hu.atka.sortui.logic.algorithm.exception.InvalidAlgorithmStateException;

// PSEUDO-CODE:
// 0-1. 1. FOR i <- 2 TO n DO
//      2.   key <- a[i]
//      3.   j <- i - 1
// 2.   4.   WHILE j > 0 AND a[j] > key DO
//      5.     a[j + 1] <- a[j]
//      6.     j <- j - 1
//      7.   END WHILE
// 3.   8.   a[j + 1] <- key
//      9. END FOR

public class InsertionAlgorithm extends Algorithm {

	private Integer key;

	public InsertionAlgorithm(Integer size) {
		super(size);
		currentLoopVariants = new int[2];
	}

	@Override
	public String getName() {
		return "Insertion algorithm";
	}

	@Override
	protected void stepAlgorithm() {
		switch (currentState) {
			case 0:
				isTerminal = false;
				currentLoopVariants[0] = 0;
				currentState++;
				break;
			case 1:
				isTerminal = false;
				currentLoopVariants[0]++;
				if (currentLoopVariants[0] < array.length) {
					key = array[currentLoopVariants[0]];
					currentLoopVariants[1] = currentLoopVariants[0] - 1;
					currentState++;
				} else {
					currentState = -1;
				}
				break;
			case 2:
				isTerminal = false;
				if (currentLoopVariants[1] > -1 && array[currentLoopVariants[1]] > key) {
					array[currentLoopVariants[1] + 1] = array[currentLoopVariants[1]];
					this.touchedIndexes.add(currentLoopVariants[1] + 1);
					currentLoopVariants[1]--;
				} else {
					currentState++;
				}
				break;
			case 3:
				isTerminal = true;
				array[currentLoopVariants[1] + 1] = key;
				this.touchedIndexes.add(currentLoopVariants[1] + 1);
				currentState = 1;
				break;
			case -1:
				isTerminal = true;
				done = true;
				break;
			default:
				isTerminal = true;
				done = true;
				throw new InvalidAlgorithmStateException();
		}
	}
}
