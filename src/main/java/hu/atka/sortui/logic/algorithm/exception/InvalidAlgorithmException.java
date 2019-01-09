package hu.atka.sortui.logic.algorithm.exception;

public class InvalidAlgorithmException extends Exception {
	public InvalidAlgorithmException(String message) {
		super("Algorithm does not exist: " + message);
	}
}
