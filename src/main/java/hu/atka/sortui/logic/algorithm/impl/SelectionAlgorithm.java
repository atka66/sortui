package hu.atka.sortui.logic.algorithm.impl;

import hu.atka.sortui.logic.algorithm.Algorithm;
import hu.atka.sortui.logic.algorithm.exception.InvalidAlgorithmStateException;

// PSEUDO-CODE:
// 0-1. 1. FOR i <- 1 TO n - 1 DO
//      2.   min <- i
// 2-3. 3.   FOR j <- i + 1 TO n DO
//      4.     IF a[j] < a[min] THEN
//      5.       min <- j
//      6.     END IF
//      7.   END FOR
// 4.   8.   a[i] <-> a[min]
//      9. END FOR

public class SelectionAlgorithm extends Algorithm {

	private Integer min;

	public SelectionAlgorithm(Integer size) {
		super(size);
		currentLoopVariants = new int[2];
	}

	@Override
	public String getName() {
		return "Selection algorithm";
	}

	@Override
	protected void stepAlgorithm() {
		switch (currentState) {
			case 0:
				isTerminal = false;
				currentLoopVariants[0] = -1;
				currentState++;
				break;
			case 1:
				isTerminal = false;
				currentLoopVariants[0]++;
				if (currentLoopVariants[0] < array.length - 1) {
					min = currentLoopVariants[0];
					currentState++;
				} else {
					currentState = -1;
				}
				break;
			case 2:
				isTerminal = false;
				currentLoopVariants[1] = currentLoopVariants[0];
				currentState++;
				break;
			case 3:
				isTerminal = true;
				currentLoopVariants[1]++;
				if (currentLoopVariants[1] < array.length) {
					this.touchedIndexes.add(currentLoopVariants[1]);
					this.touchedIndexes.add(min);
					allTouches++;
					if (array[currentLoopVariants[1]] < array[min]) {
						min = currentLoopVariants[1];
					}
				} else {
					currentState = 4;
				}
				break;
			case 4:
				isTerminal = true;
				Integer tmp = array[currentLoopVariants[0]];
				array[currentLoopVariants[0]] = array[min];
				array[min] = tmp;

				this.swappedIndexes.add(currentLoopVariants[0]);
				this.swappedIndexes.add(min);
				this.allSwaps++;

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
