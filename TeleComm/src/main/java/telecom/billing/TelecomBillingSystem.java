package telecom.billing;

//TelecomBillingSystem.java
import java.sql.SQLException;
import java.util.Scanner;

public class TelecomBillingSystem {

  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      CustomerManager customer = new CustomerManager();
      PlanManager plan = new PlanManager();
      BillingManager billing = new BillingManager();

      while (true) {
          System.out.println("Telecommunication Billing System");
          System.out.println("1. Customer Management");
          System.out.println("2. Plan Management");
          System.out.println("3. Billing Management");
          System.out.println("4. Exit");
          System.out.print("Enter your choice: ");
          int choice = sc.nextInt();

          try {
              switch (choice) {
                  case 1:
                      customer.manageCustomers(sc);
                      break;
                  case 2:
                      plan.managePlans(sc);
                      break;
                  case 3:
                      billing.manageBilling(sc);
                      break;
                  case 4:
                      System.out.println("Exiting...");
                      sc.close();
                      return;
                  default:
                      System.out.println("Invalid choice. Please try again.");
              }
          } catch (SQLException e) {
              System.err.println("Error: " + e.getMessage());
          }
      }
  }
}
