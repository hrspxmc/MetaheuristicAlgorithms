/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.bklim.tsp;

import TSProblem.Coords;
import org.junit.jupiter.api.Test;

/**
 *
 * @author hrspx
 */
public class DBHandlerTest {
  
  public DBHandlerTest() {
  }

  /**
   * Test of updateCoordsDatabase method, of class DBHandler.
   */
  @Test
  public void testUpdateCoordsDatabase() {
    
    int n_coords = 10;
    Coords[] coords = new Coords[n_coords];
    
    for(int ii = 0; ii < n_coords; ii++) {
      coords[ii] = new Coords(ii/2., ii/2.);
    }
    
    String query = DBHandler.createQuery(coords);
    
    
    System.out.println(query);
  }
  
}
