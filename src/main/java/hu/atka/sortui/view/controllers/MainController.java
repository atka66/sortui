package hu.atka.sortui.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import hu.atka.sortui.logic.algorithm.Algorithm;
import hu.atka.sortui.logic.algorithm.AlgorithmFactory;
import hu.atka.sortui.logic.algorithm.Settings;
import hu.atka.sortui.logic.algorithm.exception.InvalidAlgorithmException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MainController implements Initializable {

	@FXML
	private BorderPane borderPane;

	@FXML
	private Spinner<Integer> spinnerSize;

	@FXML
	private Spinner<Integer> spinnerSpeed;

	@FXML
	private ToggleGroup toggleGroupSort;

	@FXML
	private Canvas canvas;
	private GraphicsContext gcCanvas;

	private Timeline timeline;

	public void initialize(URL location, ResourceBundle resources) {
		borderPane.setPrefWidth(Settings.WINDOW_WIDTH);
		borderPane.setPrefHeight(Settings.WINDOW_HEIGHT);

		canvas.setWidth(Settings.CANVAS_WIDTH);
		canvas.setHeight(Settings.CANVAS_HEIGHT);

		SpinnerValueFactory<Integer> sizeValueFactory =
			new SpinnerValueFactory.IntegerSpinnerValueFactory(
				Settings.SPINNER_SIZE_MIN_VALUE, Settings.SPINNER_SIZE_MAX_VALUE, Settings.SPINNER_SIZE_INITIAL_VALUE
			);
		spinnerSize.setValueFactory(sizeValueFactory);

		SpinnerValueFactory<Integer> speedValueFactory =
			new SpinnerValueFactory.IntegerSpinnerValueFactory(
				Settings.SPINNER_SPEED_MIN_VALUE, Settings.SPINNER_SPEED_MAX_VALUE, Settings.SPINNER_SPEED_INITIAL_VALUE
			);
		spinnerSpeed.setValueFactory(speedValueFactory);

		gcCanvas = canvas.getGraphicsContext2D();
		gcCanvas.setFont(Font.font(Settings.CANVAS_TEXT_SIZE));

		clear();
	}

	@FXML
	private void handleButtonStartClick(ActionEvent event) {
		try {
			Algorithm algorithm = AlgorithmFactory.createAlgorithm(
				toggleGroupSort.getSelectedToggle().getUserData().toString(),
				spinnerSize.getValue()
			);

			if (timeline == null || !timeline.getStatus().equals(Animation.Status.RUNNING)) {
				timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / spinnerSpeed.getValue()), ae -> {
					algorithm.tick();
					render(algorithm);
					if (algorithm.isDone()) {
						timeline.stop();
					}
				}));
				timeline.setCycleCount(Animation.INDEFINITE);
				timeline.play();
			}
		} catch (InvalidAlgorithmException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	private void render(Algorithm algorithm) {
		clear();

		renderArray(algorithm);
		renderInfo(algorithm);
	}

	private void renderArray(Algorithm algorithm) {
		Integer[] array = algorithm.getArray();
		double heightRatio = (double) Settings.CANVAS_HEIGHT / array.length;
		double widthRatio = (double) Settings.CANVAS_WIDTH / array.length;
		for (int i = 0; i < array.length; i++) {
			if (algorithm.getSwappedIndexes().contains(i)) {
				gcCanvas.setStroke(Settings.CANVAS_RECT_SWAPPED_STROKE_COLOR);
				gcCanvas.setFill(Settings.CANVAS_RECT_SWAPPED_FILL_COLOR);
			} else {
				if (algorithm.getTouchedIndexes().contains(i)) {
					gcCanvas.setStroke(Settings.CANVAS_RECT_TOUCHED_STROKE_COLOR);
					gcCanvas.setFill(Settings.CANVAS_RECT_TOUCHED_FILL_COLOR);
				} else {
					gcCanvas.setStroke(Settings.CANVAS_RECT_DEFAULT_STROKE_COLOR);
					gcCanvas.setFill(Settings.CANVAS_RECT_DEFAULT_FILL_COLOR);
				}
			}
			gcCanvas.fillRect(
				i * widthRatio + Settings.CANVAS_RECT_GAP,
				Settings.CANVAS_HEIGHT - (heightRatio * array[i]) + Settings.CANVAS_RECT_GAP,
				widthRatio - (Settings.CANVAS_RECT_GAP * 2),
				(heightRatio * array[i]) - (Settings.CANVAS_RECT_GAP * 2)
			);
			gcCanvas.fillRect(
				i * widthRatio + Settings.CANVAS_RECT_GAP,
				Settings.CANVAS_HEIGHT - (heightRatio * array[i]) + Settings.CANVAS_RECT_GAP,
				widthRatio - (Settings.CANVAS_RECT_GAP * 2),
				(heightRatio * array[i]) - (Settings.CANVAS_RECT_GAP * 2)
			);
		}
	}

	private void renderInfo(Algorithm algorithm) {
		renderText(algorithm.getName(), 1);
		renderText("Array length: " + algorithm.getArray().length, 2);
		renderText("Element accesses: " + algorithm.getAllTouches(), 3);
		renderText("Element swaps: " + algorithm.getAllSwaps(), 4);
		renderText(algorithm.isDone() ? "DONE" : "IN PROGRESS", 5);
	}

	private void renderText(String text, int row) {
		gcCanvas.setStroke(Settings.CANVAS_TEXT_STROKE_COLOR);
		gcCanvas.setFill(Settings.CANVAS_TEXT_FILL_COLOR);
		gcCanvas.strokeText(text, Settings.CANVAS_TEXT_PADDING, Settings.CANVAS_TEXT_PADDING * row);
		gcCanvas.fillText(text, Settings.CANVAS_TEXT_PADDING, Settings.CANVAS_TEXT_PADDING * row);
	}

	private void clear() {
		gcCanvas.setStroke(Color.BLACK);
		gcCanvas.setFill(Color.BLACK);
		gcCanvas.fillRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT);
	}
}
