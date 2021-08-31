package TSProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TSProblem {

  public final int n_cities, starting_city;
  public final Coords[] cities_coords;
  public final int[] nn_tour;
  public final double nn_score;
  public final double[][] distances_matrix;

  private final Random rng = new Random();
  private final double min_coord_range = 0.02;
  private final double max_coord_range = 0.98;

  public TSProblem(int n_cities, int starting_city) {
    this.n_cities = n_cities;
    this.starting_city = starting_city;
    this.cities_coords = generateRandomCoords(n_cities);
    this.distances_matrix = calculateDistances(cities_coords);
    this.nn_tour = NNTourSolver.generateNNSolution(distances_matrix, starting_city);
    this.nn_score = evaluateScore(nn_tour);
  }

  private Coords[] generateRandomCoords(int n_cities) {
    Coords[] random_coords = new Coords[n_cities];
    for (int ii = 0; ii < n_cities; ii++) {
      random_coords[ii] = new Coords(
              min_coord_range + (max_coord_range - min_coord_range) * rng.nextFloat(),
              min_coord_range + (max_coord_range - min_coord_range) * rng.nextFloat());
    }
    return random_coords;
  }

  private double[][] calculateDistances(Coords[] coords) {
    double[][] dist_matrix = new double[coords.length][coords.length];
    for (int ii = 0; ii < coords.length; ii++) {
      for (int jj = 0; jj < coords.length; jj++) {
        if (ii == jj) {
          dist_matrix[ii][jj] = Double.MAX_VALUE;
        } else {
          dist_matrix[ii][jj] = euclideanDistance(coords[ii], coords[jj]);
        }
      }
    }
    return dist_matrix;
  }

  private double euclideanDistance(Coords x1, Coords x2) {
    return Math.pow(Math.pow(x2.getX() - x1.getX(), 2) + Math.pow(x2.getY() - x1.getY(), 2), 0.5);
  }

  public final double evaluateScore(int[] solution) {
    double total_score = 0;
    for (int ii = 1; ii < solution.length; ii++) {
      total_score += this.distances_matrix[solution[ii - 1]][solution[ii]];
    }
    return total_score;
  }

  public final int[] generateApproxNNTour() {
    return NNTourSolver.generateApproxNNTour(distances_matrix, starting_city);
  }

  public int[] generateRandomSolution() {
    ArrayList<Integer> random_solution;
    random_solution = new ArrayList<>(
            IntStream
                    .range(0, n_cities)
                    .boxed()
                    .collect(Collectors.toList()));

    random_solution.remove((Integer) starting_city);
    Collections.shuffle(random_solution);
    random_solution.add(0, starting_city);
    int[] random_solution_array = random_solution.stream().mapToInt(i -> i).toArray();
    return random_solution_array;
  }

}
