package hu.atka.sortui.logic.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hu.atka.sortui.logic.algorithm.exception.InvalidAlgorithmStateException;

public abstract class Algorithm {

	protected boolean done;
	protected Integer[] array;
	protected List<Integer> touchedIndexes;

	protected int[] currentLoopVariants;
	protected int currentState;

	protected boolean isTerminal;

	public Algorithm(Integer size) {
		this.done = false;
		this.array = randomArray(size);
		this.currentLoopVariants = new int[0];
		this.currentState = 0;
		this.isTerminal = false;
	}

	public final boolean isDone() {
		return done;
	}

	public abstract String getName();

	public final Integer[] getArray() {
		return array;
	}

	public final List<Integer> getTouchedIndexes() {
		return touchedIndexes;
	}

	public final void tick() {
		this.touchedIndexes = new ArrayList<>();
		do {
			this.stepAlgorithm();
		} while (!isTerminal);
	}

	protected abstract void stepAlgorithm() throws InvalidAlgorithmStateException;

	private Integer[] randomArray(int size) {
		List<Integer> arrayAsList = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			arrayAsList.add(i);
		}
		Collections.shuffle(arrayAsList);
		return arrayAsList.toArray(new Integer[0]);
	}

}
