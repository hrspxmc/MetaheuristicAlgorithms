/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.bklim.tsp;

import TSProblem.TSProblem;
import TSSolvers.CoolingSchedule;
import TSSolvers.MutateOperation;
import TSSolvers.SASolver;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author hrspx
 */
public class PrimaryController implements Initializable {

  private TSProblem tsp_problem;
  private SASolver sa_solver;
  private Timeline computation_timeline;
  private Connection db_connection;

  @FXML
  private Canvas left_canvas;
  @FXML
  private Canvas right_canvas;
  @FXML
  private ChoiceBox<MutateOperation> mutate_checkbox;
  @FXML
  private TextField n_cities_txtfield;
  @FXML
  private ChoiceBox<CoolingSchedule> cooling_checkbox;
  @FXML
  private TextField cooling_txtfield;
  @FXML
  private Slider n_iter_slider;
  @FXML
  private TextField current_best_txt;
  @FXML
  private TextField ratio_txt;
  @FXML
  private TextField total_n_iter_txt;
  @FXML
  private TextField db_status_field;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    db_connection = DBHandler.createDatabaseConnection();
    TSPCanvasDrawer.fillBackground(left_canvas.getGraphicsContext2D());
    TSPCanvasDrawer.fillBackground(right_canvas.getGraphicsContext2D());
    cooling_checkbox.getItems().setAll(CoolingSchedule.values());
    mutate_checkbox.getItems().setAll(MutateOperation.values());
    cooling_checkbox.getSelectionModel().selectFirst();
    mutate_checkbox.getSelectionModel().selectFirst();
  }

  @FXML
  private void generateProblemAction(ActionEvent event) {
    if (computation_timeline != null) {
      computation_timeline.stop();
    }

    tsp_problem = new TSProblem(Integer.parseInt(n_cities_txtfield.getText()), 0);
    TSPCanvasDrawer.fillBackground(left_canvas.getGraphicsContext2D());
    TSPCanvasDrawer.fillBackground(right_canvas.getGraphicsContext2D());
    TSPCanvasDrawer.drawCities(left_canvas.getGraphicsContext2D(), tsp_problem.cities_coords);
    TSPCanvasDrawer.drawCities(right_canvas.getGraphicsContext2D(), tsp_problem.cities_coords);

    total_n_iter_txt.setText("-");
    ratio_txt.setText("-");
    current_best_txt.setText("-");
    db_status_field.setText(" ");
  }

  @FXML
  private void startAlgorithmAction(ActionEvent event) {
    sa_solver = new SASolver(tsp_problem,
            mutate_checkbox.getValue(),
            cooling_checkbox.getValue(),
            Math.pow(10, Double.parseDouble(cooling_txtfield.getText())));

    runComputations();

  }

  @FXML
  private void stopAlgorithmAction(ActionEvent event) {
    if (computation_timeline != null) {
      computation_timeline.stop();
    }
  }

  @FXML
  private void saveCoordsToDBAction(ActionEvent event) {

    if (tsp_problem != null) {
      new Thread(() -> {
        db_status_field.setText("Saving in progress...");
        try {
          Thread.sleep(5000);
          DBHandler.updateCoordsDatabase(db_connection, tsp_problem.cities_coords);
        } catch (InterruptedException ex) {
          Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        db_status_field.setText("Succes!");
      }).start();
    } else {
      db_status_field.setText("Coords uninitialized!");
    }

  }

  @FXML
  private void showNNAction(ActionEvent event) {
    if (computation_timeline != null) {
      computation_timeline.stop();
    }

    if (tsp_problem != null) {
      TSPCanvasDrawer.fillBackground(right_canvas.getGraphicsContext2D());
      TSPCanvasDrawer.drawSolution(right_canvas.getGraphicsContext2D(), tsp_problem.cities_coords, tsp_problem.nn_tour);
    }
  }

  private void runComputations() {

    if (computation_timeline != null) {
      computation_timeline.stop();
    }

    computation_timeline = new Timeline(new KeyFrame(
            Duration.millis(200),
            (ActionEvent ae) -> {
              double previous_best = sa_solver.getBestSolutionScore();
              for (int ii = 0; ii < n_iter_slider.getValue(); ii++) {
                sa_solver.next_iter();
              }
              total_n_iter_txt.setText(Integer.toString(sa_solver.getNIter()));
              TSPCanvasDrawer.fillBackground(left_canvas.getGraphicsContext2D());
              TSPCanvasDrawer.drawSolution(left_canvas.getGraphicsContext2D(), tsp_problem.cities_coords, sa_solver.getCurrentSolution());
              if (sa_solver.getBestSolutionScore() < previous_best) {
                TSPCanvasDrawer.fillBackground(right_canvas.getGraphicsContext2D());
                TSPCanvasDrawer.drawSolution(right_canvas.getGraphicsContext2D(), tsp_problem.cities_coords, sa_solver.getBestSolution());
                current_best_txt.setText(String.format("%.2f", sa_solver.getBestSolutionScore()));
                ratio_txt.setText(String.format("%.2f", sa_solver.getBestSolutionScore() / tsp_problem.nn_score));
              }
            }));

    computation_timeline.setCycleCount(Animation.INDEFINITE);
    computation_timeline.play();
  }

}
