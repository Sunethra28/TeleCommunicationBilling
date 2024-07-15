package telecom.billing;

//CustomerManager.java
import java.sql.*;
import java.util.Scanner;

public class CustomerManager {

  public void manageCustomers(Scanner sc) throws SQLException {
      while (true) {
          System.out.println("Customer Management");
          System.out.println("1. Add new customer");
          System.out.println("2. View customer details");
          System.out.println("3. Update customer information");
          System.out.println("4. Remove customer");
          System.out.println("5. Back to main menu");
          System.out.print("Enter your choice: ");
          int choice = sc.nextInt();

          switch (choice) {
              case 1:
                  addCustomer(sc);
                  break;
              case 2:
                  viewCustomer(sc);
                  break;
              case 3:
                  updateCustomer(sc);
                  break;
              case 4:
                  removeCustomer(sc);
                  break;
              case 5:
                  return;
              default:
                  System.out.println("Invalid choice. Please try again.");
          }
      }
  }

  private void addCustomer(Scanner sc) throws SQLException {
      System.out.print("Enter name: ");
      String name = sc.next();
      System.out.print("Enter email: ");
      String email = sc.next();
      System.out.print("Enter contact number: ");
      String contactNumber = sc.next();
      System.out.print("Enter address: ");
      String address = sc.next();

      String sql = "INSERT INTO Customer (name, email, contact_number, address) VALUES (?, ?, ?, ?)";
      try {
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setString(1, name);
          st.setString(2, email);
          st.setString(3, contactNumber);
          st.setString(4, address);
          st.executeUpdate();
          System.out.println("Customer added successfully.");
      }catch (SQLException e) {
    	    e.printStackTrace();
      } 
      
  }

  private void viewCustomer(Scanner sc) throws SQLException {
      System.out.print("Enter customer ID: ");
      int customerId = sc.nextInt();

      String sql = "SELECT * FROM Customer WHERE customer_id = ?";
      
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setInt(1, customerId);
          try {
        	  ResultSet resultSet = st.executeQuery();
              if (resultSet.next()) {
                  System.out.println("Customer ID: " + resultSet.getInt("customer_id"));
                  System.out.println("Name: " + resultSet.getString("name"));
                  System.out.println("Email: " + resultSet.getString("email"));
                  System.out.println("Contact Number: " + resultSet.getString("contact_number"));
                  System.out.println("Address: " + resultSet.getString("address"));
              } else {
                  System.out.println("Customer not found.");
              }
          }
          catch (SQLException e) {
      	    e.printStackTrace();
        } 
      }

  private void updateCustomer(Scanner sc) throws SQLException {
      System.out.print("Enter customer ID: ");
      int customerId = sc.nextInt();
      System.out.print("Enter new name: ");
      String name = sc.next();
      System.out.print("Enter new email: ");
      String email = sc.next();
      System.out.print("Enter new contact number: ");
      String contactNumber = sc.next();
      System.out.print("Enter new address: ");
      String address = sc.next();

      String sql = "UPDATE Customer SET name = ?, email = ?, contact_number = ?, address = ? WHERE customer_id = ?";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setString(1, name);
          st.setString(2, email);
          st.setString(3, contactNumber);
          st.setString(4, address);
          st.setInt(5, customerId);
          int rowsUpdated = st.executeUpdate();
          if (rowsUpdated > 0) {
              System.out.println("Customer updated successfully.");
          } else {
              System.out.println("Customer not found.");
          }
      }catch (SQLException e) {
    	    e.printStackTrace();
      } 
  }

  private void removeCustomer(Scanner sc) throws SQLException {
      System.out.print("Enter customer ID: ");
      int customerId = sc.nextInt();

      String sql = "DELETE FROM Customer WHERE customer_id = ?";
      try{
    	  Connection con = DBConnection.getConnection();
          PreparedStatement st = con.prepareStatement(sql);
          st.setInt(1, customerId);
          int rowsDeleted = st.executeUpdate();
          if (rowsDeleted > 0) {
              System.out.println("Customer removed successfully.");
          } else {
              System.out.println("Customer not found.");
          }
      }catch (SQLException e) {
  	    e.printStackTrace();
    } 
  }
}
