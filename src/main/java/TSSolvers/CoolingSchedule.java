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
public enum CoolingSchedule {
  LINEAR((double temp, double param, int n_iter) -> temp - param * n_iter),
  LOGARITHMIC((double temp, double param, int n_iter) -> (temp) / Math.log10(1 + n_iter*param)),
  GEOMETRIC((double temp, double param, int n_iter) -> temp * Math.pow(1-param, n_iter));

  private final CoolingScheduleInterface cooling_func;

  CoolingSchedule(CoolingScheduleInterface cooling_func) {
    this.cooling_func = cooling_func;
  }

  public double cool(double temp, double param, int n_iter) {
    return cooling_func.cool(temp, param, n_iter);
  }
}
