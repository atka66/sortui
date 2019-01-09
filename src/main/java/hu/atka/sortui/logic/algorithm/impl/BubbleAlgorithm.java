package hu.atka.sortui.logic.algorithm.impl;

import hu.atka.sortui.logic.algorithm.Algorithm;

// PSEUDO-CODE:
// 0.   1. i <- n - 1
// 1.   2. WHILE i >= 1 DO
//      3.   lastSwap <- 1
// 2-3. 4.   FOR j <- 1 TO i DO
//      5.     IF a[j] > a[j + 1] THEN
// 4.   6.       a[j] <-> a[j + 1]
//      7.       lastSwap <- j
//      8.     END IF
//      9.   END FOR
// 5.   10.  i <- lastSwap - 1
//      11.END WHILE

public class BubbleAlgorithm extends Algorithm {

	private Integer lastSwap;

	public BubbleAlgorithm(Integer size) {
		super(size);
		currentLoopVariants = new int[2];
	}

	@Override
	public String getName() {
		return "Bubble algorithm";
	}

	@Override
	protected void stepAlgorithm() {
		switch (currentState) {
			case 0:
				isTerminal = false;
				currentLoopVariants[0] = array.length - 1;
				currentState++;
				break;
			case 1:
				isTerminal = false;
				if (currentLoopVariants[0] >= 1) {
					lastSwap = 0;
					currentState++;
				} else {
					currentState = -1;
				}
				break;
			case 2:
				isTerminal = false;
				currentLoopVariants[1] = -1;
				currentState++;
				break;
			case 3:
				isTerminal = false;
				currentLoopVariants[1]++;
				if (currentLoopVariants[1] < currentLoopVariants[0]) {
					if (array[currentLoopVariants[1]] > array[currentLoopVariants[1] + 1]) {
						currentState++;
					}
				} else {
					currentState = 5;
				}
				break;
			case 4:
				isTerminal = true;
				Integer tmp = array[currentLoopVariants[1]];
				array[currentLoopVariants[1]] = array[currentLoopVariants[1] + 1];
				array[currentLoopVariants[1] + 1] = tmp;

				this.touchedIndexes.add(currentLoopVariants[1]);
				this.touchedIndexes.add(currentLoopVariants[1] + 1);

				lastSwap = currentLoopVariants[1];

				currentState = 3;
				break;
			case 5:
				isTerminal = false;

				currentLoopVariants[0] = lastSwap;

				currentState = 1;
				break;
			case -1:
				isTerminal = true;
				done = true;
				break;
			default:
				isTerminal = true;
				done = true;
				throw new RuntimeException("Oops, something happened during sorting!");
		}
	}
}
