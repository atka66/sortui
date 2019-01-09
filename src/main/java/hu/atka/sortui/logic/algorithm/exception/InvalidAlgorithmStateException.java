package hu.atka.sortui.logic.algorithm.exception;

public class InvalidAlgorithmStateException extends RuntimeException {
	public InvalidAlgorithmStateException() {
		super("Invalid state reached during sorting.");
	}
}
