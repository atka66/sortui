package hu.atka.sortui.logic.algorithm.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import hu.atka.sortui.logic.algorithm.Algorithm;
import hu.atka.sortui.logic.algorithm.exception.InvalidAlgorithmStateException;

public class BogoAlgorithm extends Algorithm {

	public BogoAlgorithm(Integer size) {
		super(size);
	}

	@Override
	public String getName() {
		return "Bogosort algorithm";
	}

	@Override
	protected void stepAlgorithm() {
		switch (currentState) {
			case 0:
				isTerminal = false;
				if (isSorted()) {
					currentState = -1;
				} else {
					currentState++;
				}
				break;
			case 1:
				isTerminal = true;
				shuffleArray();
				currentState = 0;
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

	private boolean isSorted() {
		for (int i = 0; i < array.length - 1; i++) {
			allTouches++;
			if (array[i] > array[i + 1]) {
				return false;
			}
		}
		return true;
	}

	private void shuffleArray() {
		List<Integer> arrayAsList = Arrays.asList(array);
		Collections.shuffle(arrayAsList);
		allSwaps += array.length;
		array = arrayAsList.toArray(new Integer[0]);
	}
}
