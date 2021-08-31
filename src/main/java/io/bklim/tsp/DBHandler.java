/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.bklim.tsp;

import TSProblem.Coords;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrspx
 */
public class DBHandler {

  public static Connection createDatabaseConnection() {

    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }

    Connection connection = null;

    try {
      connection = DriverManager.getConnection("jdbc:sqlite:tsp_database");
      connection.setAutoCommit(false);
    } catch (SQLException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println("Connection succesfull!");
    return connection;
  }

  public static void updateCoordsDatabase(Connection db_connection, Coords[] coords) {

    Statement st = null;

    try {
      st = db_connection.createStatement();
    } catch (SQLException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }

    String sql_st = createQuery(coords);

    try {
      st.executeUpdate(sql_st);
      st.close();
      db_connection.commit();
    } catch (SQLException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public static String createQuery(Coords[] coords) {
    StringBuilder sql_st = new StringBuilder();
    sql_st.append("INSERT INTO 'cities_coords' ('date', 'city_number', 'coord_x', 'coord_y') VALUES \n");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String current_date = dtf.format(now);

    int ii_id = 0;
    for (Coords ii : coords) {
      sql_st.append("('")
              .append(current_date)
              .append("', '")
              .append(String.format("%d", ii_id))
              .append("', '")
              .append(String.format("%.4f", ii.getX()))
              .append("', '")
              .append(String.format("%.4f", ii.getY()))
              .append("'), \n");

      ii_id++;
    }

    String query_string = sql_st.toString().trim();
    query_string = query_string.substring(0, query_string.length() - 1) + ";";

    return query_string;
  }

}
