package talabat;

import java.sql.*;
import java.util.Scanner;

public class Login {

    Statement stat;

    public void Login_Restaurantowner() {
        Connection con;
        ResultSet rs;
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        while (true) {
            System.out.print("Enter your username: ");
            String user = console.nextLine();
            System.out.print("Enter your Password: ");
            String pass = console.nextLine();
            try {
                PasswordHashing p = new PasswordHashing();
                pass = p.GenerateHash(pass);
                String sql = "select username,password from Table2 where username='" + user + "'and password='" + pass + "'";
                rs = stat.executeQuery(sql);
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    System.out.println("Logged in successfully!");
                    return;
                } else {
                    System.out.println("User not found,if you want to try again press 1\n if you want to create an account press 2:");
                    int choice = console.nextInt();
                    if (choice == 1) {
                        Login_Restaurantowner();

                    } else {
                        SignUp signUp = new SignUp();
                        return;
                    }
                }
            } catch (Exception ex) {

            }
            break;
        }
    }

    public void Login_Customer(Customer c1) {
        Connection con;
        ResultSet rs;
        Scanner input = new Scanner(System.in);
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        while (true) {
            System.out.print("Enter your username: ");
            String user = console.nextLine();
            System.out.print("Enter your Password: ");
            String pass = console.nextLine();
            try {
                PasswordHashing p = new PasswordHashing();
                pass = p.GenerateHash(pass);
                String sql = "select username,password from Table2 where username='" + user + "'and password='" + pass + "'";
                rs = stat.executeQuery(sql);
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    while (true) {
                        System.out.println("1.Browse Restaurant");
                        System.out.println("2.Order History");
                        System.out.println("3.Log out");
                        System.out.print("Please Enter Your Choice: ");
                        String Choice = input.next();
                        if (Choice.equals("1")) {
                            c1.CheckforRestaurant();
                        } else if (Choice.equals("2")) {
                            c1.Show();
                        } else if (Choice.equals("3")) {
                            break;
                        } else {
                            System.out.print("unknown option \n");
                        }
                    }
                    return;
                } else {
                    System.out.println("User not found,if you want to try again press 1\n if you want to create an account press 2:");
                    int choice = console.nextInt();
                    if (choice == 1) {
                        Login_Customer(c1);
                    } else {
                        SignUp signUp = new SignUp();
                        return;
                    }
                }
            } catch (Exception ex) {

            }
            break;
        }
    }
}
