package hu.atka.sortui.logic.algorithm.exception;

public class InvalidAlgorithmStateException extends Exception {
	public InvalidAlgorithmStateException() {
		super("Invalid state reached during sorting.");
	}
}
