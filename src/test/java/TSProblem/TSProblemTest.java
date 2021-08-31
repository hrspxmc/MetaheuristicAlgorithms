/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSProblem;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hrspx
 */
public class TSProblemTest {
  
  public TSProblemTest() {
  }

  @Test
  public void testGenerateApproxNNTour() {
   var a = new TSProblem(3, 0);
   //var sol1 = a.generateApproxNNTour();
   System.out.println(Arrays.deepToString(a.distances_matrix));
   System.out.println(Arrays.toString(a.nn_tour));
   
  }
  
}
