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
public interface CoolingScheduleInterface {

  double cool(double temp, double param, int n_iter);
}
