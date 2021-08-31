/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSSolvers;

/**
 *
 * @author hrspx
 */
@FunctionalInterface
public interface MutateOperationInterface {

  int[] mutate(int[] solution);
}
