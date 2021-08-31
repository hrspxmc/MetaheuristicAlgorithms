/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSProblem;

import java.util.Random;
import java.util.stream.DoubleStream;

/**
 *
 * @author hrspx
 */
public class RouletteSelection {

  private final double[] cummulated_values;
  private final Random rng = new Random();

  public RouletteSelection(double[] values) {
    this.cummulated_values = cummulativeSum(values);
  }

  private double[] cummulativeSum(double[] vals) {
    double[] cume_vals = new double[vals.length];
    double cume_value = 0;
    for (int ii = 0; ii < vals.length; ii++) {
      cume_value += vals[ii];
      cume_vals[ii] = cume_value;
    }
    double total_sum = cume_vals[cume_vals.length - 1];
    cume_vals = DoubleStream.of(cume_vals).map(x -> x / total_sum).toArray();
    return cume_vals;
  }

  public int sampleIndex() {
    double sampled_value = rng.nextFloat();
    for (int ii = 0; ii < cummulated_values.length; ii++) {
      if (cummulated_values[ii] > sampled_value) {
        return ii;
      }
    }
    return cummulated_values.length - 1;
  }
}
