/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSSolvers;

import TSProblem.TSProblem;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hrspx
 */
public class SASolverTest {
  
  public SASolverTest() {
  }

  /**
   * Test of next_iter method, of class SASolver.
   */
  @Test
  public void testNext_iter() {
    var ts_problem = new TSProblem(50, 0);
    var sa_solver = new SASolver(ts_problem, MutateOperation.SWAP, CoolingSchedule.LINEAR, 0.00001);
    System.out.println((sa_solver.getBestSolutionScore()));
    for(int ii = 0; ii < 1000000; ii++) {
      sa_solver.next_iter();
    }
    System.out.println((sa_solver.getBestSolutionScore()));
  }

  
}
