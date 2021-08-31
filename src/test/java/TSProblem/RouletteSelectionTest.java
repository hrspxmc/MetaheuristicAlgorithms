/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSProblem;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class RouletteSelectionTest {
  
  public RouletteSelectionTest() {
  }

  @Test
  public void testSampleIndex() {
    
    int n_samples = 10000000;
    double[] test = {1.,2.,3.,4.,5.};
    int[] sampled_indexes = new int[n_samples];
    
    var selection = new RouletteSelection(test);
    for(int ii = 0; ii < n_samples; ii++) {
      sampled_indexes[ii] = selection.sampleIndex();
    }
    
    Map<Integer, Long> sampled_map = Arrays
            .stream(sampled_indexes)
            .boxed()
            .collect(Collectors
                    .groupingBy(Integer::intValue,Collectors.counting()));

    System.out.print(sampled_map.toString());
    
    
  }
  
}
