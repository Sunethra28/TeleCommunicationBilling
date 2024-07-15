package telecom.billing;

//PlanManager.java
import java.sql.*;
import java.util.Scanner;

public class PlanManager {

  public void managePlans(Scanner sc) throws SQLException {
      while (true) {
          System.out.println("Plan Management");
          System.out.println("1. Add new plan");
          System.out.println("2. View plan details");
          System.out.println("3. Update plan information");
          System.out.println("4. Remove plan");
          System.out.println("5. Back to main menu");
          System.out.print("Enter your choice: ");
          int choice = sc.nextInt();

          switch (choice) {
              case 1:
                  addPlan(sc);
                  break;
              case 2:
                  viewPlan(sc);
                  break;
              case 3:
                  updatePlan(sc);
                  break;
              case 4:
                  removePlan(sc);
                  break;
              case 5:
                  return;
              default:
                  System.out.println("Invalid choice. Please try again.");
          }
      }
  }

  private void addPlan(Scanner sc) throws SQLException {
      System.out.print("Enter name: ");
      String name = sc.next();
      System.out.print("Enter description: ");
      String description = sc.next();
      //scanner.next();
      System.out.print("Enter price: ");
      double price = sc.nextDouble();

      String sql = "INSERT INTO Plan (name, description, price) VALUES (?, ?, ?)";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setString(1, name);
          st.setString(2, description);
          st.setDouble(3, price);
          st.executeUpdate();
          System.out.println("Plan added successfully.");
      }catch (SQLException e) {
    	    e.printStackTrace();
      } 
  }

  private void viewPlan(Scanner sc) throws SQLException {
      System.out.print("Enter plan ID: ");
      int planId = sc.nextInt();

      String sql = "SELECT * FROM Plan WHERE plan_id = ?";
      
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setInt(1, planId);
          try{
        	  ResultSet resultSet = st.executeQuery();
              if (resultSet.next()) {
                  System.out.println("Plan ID: " + resultSet.getInt("plan_id"));
                  System.out.println("Name: " + resultSet.getString("name"));
                  System.out.println("Description: " + resultSet.getString("description"));
                  System.out.println("Price: " + resultSet.getDouble("price"));
              } else {
                  System.out.println("Plan not found.");
              }
          }catch (SQLException e) {
        	    e.printStackTrace();
          } 
  }

  private void updatePlan(Scanner sc) throws SQLException {
      System.out.print("Enter plan ID: ");
      int planId = sc.nextInt();
      System.out.print("Enter new name: ");
      String name = sc.next();
      System.out.print("Enter new description: ");
      String description = sc.next();
      System.out.print("Enter new price: ");
      double price = sc.nextDouble();

      String sql = "UPDATE Plan SET name = ?, description = ?, price = ? WHERE plan_id = ?";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setString(1, name);
          st.setString(2, description);
          st.setDouble(3, price);
          st.setInt(4, planId);
          int rowsUpdated = st.executeUpdate();
          if (rowsUpdated > 0) {
              System.out.println("Plan updated successfully.");
          } else {
              System.out.println("Plan not found.");
          }
      }catch (SQLException e) {
  	    e.printStackTrace();
    } 
  }

  private void removePlan(Scanner sc) throws SQLException {
      System.out.print("Enter plan ID: ");
      int planId = sc.nextInt();

      String sql = "DELETE FROM Plan WHERE plan_id = ?";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setInt(1, planId);
          int rowsDeleted = st.executeUpdate();
          if (rowsDeleted > 0) {
              System.out.println("Plan removed successfully.");
          } else {
              System.out.println("Plan not found.");
          }
      }catch (SQLException e) {
    	    e.printStackTrace();
      } 
  }
}
