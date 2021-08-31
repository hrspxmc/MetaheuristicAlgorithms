/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSSolvers;

import TSProblem.TSProblem;
import java.util.Random;

/**
 *
 * @author hrspx
 */
public class SASolver {

  private final TSProblem ts_problem;
  private final MutateOperation mutate_operation;
  private final CoolingSchedule cooling_schedule;
  private int n_iters;
  private double temperature;
  private final double cooling_param;
  private int[] current_solution;
  private double current_score;

  private int[] best_solution;
  private double best_score;

  private final Random rng = new Random();

  public SASolver(TSProblem ts_problem, MutateOperation mutate_operation, CoolingSchedule cooling_schedule, double cooling_param) {
    this.ts_problem = ts_problem;
    this.mutate_operation = mutate_operation;
    this.cooling_schedule = cooling_schedule;
    this.temperature = setInitialTemperature();
    this.cooling_param = cooling_param;

    this.current_solution = ts_problem.generateRandomSolution();
    this.current_score = ts_problem.evaluateScore(current_solution);

    this.best_score = current_score;
    this.best_solution = current_solution;

    n_iters = 0;
  }

  public void next_iter() {
    int[] proposed_solution = mutate_operation.mutate(current_solution);
    double proposed_solution_score = ts_problem.evaluateScore(proposed_solution);
    double delta = proposed_solution_score - current_score;
    double transition_probability = Math.min(Math.exp(-delta / temperature), 1);
    if (transition_probability > rng.nextDouble()) {
      current_solution = proposed_solution;
      current_score = proposed_solution_score;
      if (proposed_solution_score < best_score) {
        best_score = proposed_solution_score;
        best_solution = proposed_solution;
      }
    }
    temperature = Math.max(cooling_schedule.cool(temperature, cooling_param, n_iters), 0.00000001);
    n_iters += 1;
  }

  private double setInitialTemperature() {
    int n_trials = 500;
    double scores_sum = 0;
    double diffs_sum = 0;
    for (int ii = 0; ii < n_trials; ii++) {
      double rand_sol_score = ts_problem.evaluateScore(ts_problem.generateRandomSolution());
      scores_sum += rand_sol_score;
      diffs_sum += (rand_sol_score - current_score);

    }
    double mean_score = scores_sum / n_trials;
    double mean_diff = diffs_sum / n_trials;
    //System.out.print(mean_diff / Math.log(mean_score));
    return (mean_diff / Math.log(mean_score));
  }

  public int[] getBestSolution() {
    return best_solution.clone();
  }

  public int[] getCurrentSolution() {
    return current_solution.clone();
  }

  public double getBestSolutionScore() {
    return best_score;
  }

  public double getCurrentSolutionScore() {
    return current_score;
  }

  public int getNIter() {
    return n_iters;
  }

}
