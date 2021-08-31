/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSProblem;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 *
 * @author hrspx
 */
public class NNTourSolver {

  private NNTourSolver() {

  }

  public static int[] generateNNSolution(double[][] distances_matrix, int starting_city) {
    int[] nn_solution = new int[distances_matrix.length];
    ArrayList<Integer> avilable_cities;
    avilable_cities = new ArrayList<>(
            IntStream.range(0, distances_matrix.length)
                    .boxed()
                    .collect(Collectors.toList()));

    avilable_cities.remove((Integer) starting_city);
    nn_solution[0] = starting_city;

    for (int ii = 1; ii < distances_matrix.length; ii++) {

      double current_min = distances_matrix[nn_solution[ii - 1]][avilable_cities.get(0)];
      int min_index = avilable_cities.get(0);
      for (Integer jj : avilable_cities) {
        double current_distance = distances_matrix[nn_solution[ii - 1]][jj];
        if (current_distance < current_min) {
          current_min = current_distance;
          min_index = jj;
        }
      }
      avilable_cities.remove((Integer) min_index);
      nn_solution[ii] = min_index;
    }
    return nn_solution;
  }

  public static int[] generateApproxNNTour(double[][] distnaces_matrix, int starting_city) {
    int[] nn_solution = new int[distnaces_matrix.length];
    ArrayList<Integer> avilable_cities;
    avilable_cities = new ArrayList<>(
            IntStream
                    .range(0, distnaces_matrix.length)
                    .boxed()
                    .collect(Collectors.toList()));

    avilable_cities.remove((Integer) starting_city);
    nn_solution[0] = starting_city;

    for (int ii = 1; ii < distnaces_matrix.length; ii++) {
      int n_avilable = avilable_cities.size();
      double[] current_distances = new double[n_avilable];
      int counter = 0;
      for (int city : avilable_cities) {
        current_distances[counter] = distnaces_matrix[nn_solution[ii - 1]][city];
        counter++;
      }
      double[] distances_inversed = DoubleStream.of(current_distances).map(p -> 1 / p).toArray();
      RouletteSelection selection = new RouletteSelection(distances_inversed);
      int nn_city = avilable_cities.get(selection.sampleIndex());
      avilable_cities.remove((Integer) nn_city);
      nn_solution[ii] = nn_city;
    }
    return nn_solution;
  }

  private static int findSmallestIndex(double[] arr) {
    int imin = 0;
    for (int ii = 0; ii < arr.length; ii++) {
      if (arr[ii] < arr[imin]) {
        imin = ii;
      }
    }
    return imin;
  }

}
