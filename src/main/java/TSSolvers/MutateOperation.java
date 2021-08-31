/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSSolvers;

import java.util.Random;

/**
 *
 * @author hrspx
 */
public enum MutateOperation {
  SWAP((int[] sol) -> swap(sol)),
  REVERSE((int[] sol) -> reverse(sol));

  private final MutateOperationInterface mutate_func;
  private static final Random rng = new Random();

  MutateOperation(MutateOperationInterface mutate_func) {
    this.mutate_func = mutate_func;
  }

  public int[] mutate(int[] solution) {
    return mutate_func.mutate(solution);
  }

  private static int[] swap(int[] solution) {
    int ii = rng.nextInt(solution.length - 1) + 1;
    int jj = rng.nextInt(solution.length - 1) + 1;
    while (jj == ii) {
      jj = rng.nextInt(solution.length - 1) + 1;
    }
    int tmp = solution[ii];
    int[] sol_copy = solution.clone();
    sol_copy[ii] = sol_copy[jj];
    sol_copy[jj] = tmp;
    return sol_copy;
  }

  private static int[] reverse(int[] solution) {
    int ii = rng.nextInt(solution.length - 1) + 1;
    int jj = rng.nextInt(solution.length - 1) + 1;
    while (jj == ii) {
      jj = rng.nextInt(solution.length - 1) + 1;
    }
    int start_id = Math.min(ii, jj);
    int end_id = Math.max(ii, jj);
    int span = Math.abs((ii - jj));
    int[] sol_copy = solution.clone();

    for (int ii_iter = 0; ii_iter < span; ii_iter++) {
      sol_copy[start_id + ii_iter] = solution[end_id - ii_iter - 1];
    }
    return sol_copy;
  }

}
