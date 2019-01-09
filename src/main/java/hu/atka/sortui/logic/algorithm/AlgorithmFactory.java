package hu.atka.sortui.logic.algorithm;

import hu.atka.sortui.logic.algorithm.exception.InvalidAlgorithmException;
import hu.atka.sortui.logic.algorithm.impl.BubbleAlgorithm;
import hu.atka.sortui.logic.algorithm.impl.InsertionAlgorithm;
import hu.atka.sortui.logic.algorithm.impl.SelectionAlgorithm;

public class AlgorithmFactory {

	public static Algorithm createAlgorithm(String algorithm, Integer size) throws InvalidAlgorithmException {
		switch (algorithm) {
			case "selection":
				return new SelectionAlgorithm(size);
			case "insertion":
				return new InsertionAlgorithm(size);
			case "bubble":
				return new BubbleAlgorithm(size);
			default:
				throw new InvalidAlgorithmException(algorithm);
		}
	}
}
