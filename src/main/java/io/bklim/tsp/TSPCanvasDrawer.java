/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.bklim.tsp;

import TSProblem.Coords;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author hrspx
 */
public class TSPCanvasDrawer {

  private static final double circle_radius = 10;
  private static final double picture_scale = 525;

  private TSPCanvasDrawer() {

  }

  public static void fillBackground(GraphicsContext canvas_context) {
    canvas_context.setFill(Color.AQUA);
    canvas_context.fillRect(0, 0, picture_scale, picture_scale);

    canvas_context.setStroke(Color.BLACK);
    canvas_context.setLineWidth(1);
    canvas_context.strokeLine(0, 0, 0, picture_scale);
    canvas_context.strokeLine(0, 0, picture_scale, 0);
    canvas_context.strokeLine(picture_scale, 0, picture_scale, picture_scale);
    canvas_context.strokeLine(0, picture_scale, picture_scale, picture_scale);

  }

  public static void drawCities(GraphicsContext canvas_context, Coords[] cities_coords) {
    canvas_context.setFill(Color.GREEN);
    for (Coords ii : cities_coords) {
      canvas_context.fillOval(
              ii.getX() * picture_scale - circle_radius / 2,
              ii.getY() * picture_scale - circle_radius / 2,
              circle_radius, circle_radius);
    }
  }

  public static void drawSolution(GraphicsContext canvas_context, Coords[] cities_coords, int[] solution) {
    canvas_context.setStroke(Color.RED);
    canvas_context.setLineWidth(1);
    for (int ii = 1; ii < solution.length; ii++) {
      canvas_context.strokeLine(
              cities_coords[solution[ii - 1]].getX() * picture_scale,
              cities_coords[solution[ii - 1]].getY() * picture_scale,
              cities_coords[solution[ii]].getX() * picture_scale,
              cities_coords[solution[ii]].getY() * picture_scale);
    }
    drawCities(canvas_context, cities_coords);
  }
}
