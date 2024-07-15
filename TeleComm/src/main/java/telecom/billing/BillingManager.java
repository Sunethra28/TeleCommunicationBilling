package telecom.billing;

//BillingManager.java
import java.sql.*;
import java.util.Scanner;

public class BillingManager {

  public void manageBilling(Scanner sc) throws SQLException {
      while (true) {
          System.out.println("Billing Management");
          System.out.println("1. Generate bill");
          System.out.println("2. View billing details");
          System.out.println("3. Update billing information");
          System.out.println("4. Remove billing record");
          System.out.println("5. Back to main menu");
          System.out.print("Enter your choice: ");
          int choice = sc.nextInt();

          switch (choice) {
              case 1:
                  generateBill(sc);
                  break;
              case 2:
                  viewBillingDetails(sc);
                  break;
              case 3:
                  updateBillingInformation(sc);
                  break;
              case 4:
                  removeBillingRecord(sc);
                  break;
              case 5:
                  return;
              default:
                  System.out.println("Invalid choice. Please try again.");
          }
      }
  }

  private void generateBill(Scanner sc) throws SQLException {
      System.out.print("Enter customer ID: ");
      int customerId = sc.nextInt();

      String sql = "SELECT SUM(p.price * u.usage_amount) AS total_amount FROM CUsage u JOIN Plan p ON u.plan_id = p.plan_id WHERE u.customer_id = ?";
    
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setInt(1, customerId);
        	  ResultSet resultSet = st.executeQuery();
              if (resultSet.next()) {
                  double totalAmount = resultSet.getDouble("total_amount");
                  String insertBillSQL = "INSERT INTO Bill (customer_id, total_amount, billing_date) VALUES (?, ?, CURDATE())";
                  try {
                	  PreparedStatement insertStatement = con.prepareStatement(insertBillSQL);
                      insertStatement.setInt(1, customerId);
                      insertStatement.setDouble(2, totalAmount);
                      insertStatement.executeUpdate();
                      System.out.println("Bill generated successfully.");
                  }catch (SQLException e) {
              	    e.printStackTrace();
                  } 
              } else {
                  System.out.println("No usage records found for this customer.");
              }
  }

  private void viewBillingDetails(Scanner sc) throws SQLException {
      System.out.print("Enter bill ID: ");
      int billId = sc.nextInt();

      String sql = "SELECT * FROM Bill WHERE bill_id = ?";
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setInt(1, billId);
          try{
        	  ResultSet resultSet = st.executeQuery();
              if (resultSet.next()) {
                  System.out.println("Bill ID: " + resultSet.getInt("bill_id"));
                  System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
                  System.out.println("Total Amount: " + resultSet.getDouble("total_amount"));
                  System.out.println("Billing Date: " + resultSet.getDate("billing_date"));
              } else {
                  System.out.println("Bill not found.");
              }
          }catch (SQLException e) {
        	    e.printStackTrace();
            } 
  }

  private void updateBillingInformation(Scanner sc) throws SQLException {
      System.out.print("Enter bill ID: ");
      int billId = sc.nextInt();
      System.out.print("Enter new total amount: ");
      double totalAmount = sc.nextDouble();

      String sql = "UPDATE Bill SET total_amount = ? WHERE bill_id = ?";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setDouble(1, totalAmount);
          st.setInt(2, billId);
          int rowsUpdated = st.executeUpdate();
          if (rowsUpdated > 0) {
              System.out.println("Billing information updated successfully.");
          } else {
              System.out.println("Bill not found.");
          }
      }catch (SQLException e) {
  	    e.printStackTrace();
      } 
  }

  private void removeBillingRecord(Scanner sc) throws SQLException {
      System.out.print("Enter bill ID: ");
      int billId = sc.nextInt();

      String sql = "DELETE FROM Bill WHERE bill_id = ?";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql); 
          st.setInt(1, billId);
          int rowsDeleted = st.executeUpdate();
          if (rowsDeleted > 0) {
              System.out.println("Billing record removed successfully.");
          } else {
              System.out.println("Bill not found.");
          }
      }catch (SQLException e) {
    	    e.printStackTrace();
        } 
  }
}