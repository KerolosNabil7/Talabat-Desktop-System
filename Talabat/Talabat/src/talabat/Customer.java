package talabat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Customer extends User {

    Scanner input = new Scanner(System.in);
    String mobileNumber;
    String Address;
    ArrayList<History> Order = new ArrayList<History>();
    double totalPrice = 0;
    String username;
     String mealName;

    public Customer(String username, String password, String phone, String address) {
        super(username, password);
        mobileNumber = phone;
        Address = address;
    }

    @Override
    public void SIGN_UP() {
        super.connect();
        String user;
        String pass;
        String confirm;
        String sql;
        String address;
        int phone;
        while (true) {
            System.out.print("Enter your username:");
            user = console.nextLine();
            try {
                sql = "select username from Table2 where username='" + user + "'";
                rs1 = stat.executeQuery(sql);
                int count = 0;
                while (rs1.next()) {
                    count++;
                }
                if (count != 0) {
                    System.out.println("username already exists , try again");
                } else {
                    break;
                }
            } catch (Exception ex) {

            }
        }
        while (true) {
            System.out.print("Enter your password:");
            pass = console.nextLine();
            if (ValidPassword(pass)) {
                System.out.print("Confirm your password:");
                confirm = console.nextLine();
                if (confirmPassword(pass, confirm)) {
                    break;
                } else {
                    System.out.println("Password mismatch");
                    continue;
                }
            } else {
                System.out.print("Password must contain at least 8 characters and at most 20 characters.\n" + "Password must contain at least one digit, one upper case alphabet,one lower case alphabet, one special character which includes !@#$%&*()-+=^.and doesn’t contain any white space.\n");
            }
        }
        System.out.print("Enter Your Phone: ");
        phone = input.nextInt();
        System.out.print("Enter Your Adress: ");
        address = input.next();
        try {
            PasswordHashing p = new PasswordHashing();
            pass = p.GenerateHash(pass);
            String type = "customer";
            sql = "insert into Table2 values('" + user + "','" + pass + "','" + type +"','" + phone + "','" + address +"')";
            stat1.executeUpdate(sql);
            System.out.println("Account Created!");
        } catch (Exception ex) {
            System.out.println("kerp;ps");
        }
    }
//    public void Show() {
//        for (int i = 0; i < this.Order.size(); i++) {
//            System.out.printf("Order %d: %s\n", i + 1, this.Order.get(i).Name);
//            System.out.printf("Order Date: %s\n", this.Order.get(i).Date);
//            System.out.printf("Order Quantity: %d\n", this.Order.get(i).Quantity);
//            System.out.println();
//            if (i == Order.size() - 1) {
//                System.out.print("The Total Price is " + totalPrice + "\n");
//            }
//        }
//    }

//    public void showHistory() {
//        for (int i = 0; i < this.Order.size(); i++) {
//            this.Order.get(i).displayHistory();
//            if (i == Order.size() - 1) {
//                System.out.print("The Total Price is " + totalPrice + "\n");
//            }
//        }
//    }
    public void CheckforRestaurant() {
        Connection con;
        Statement stat;
        Statement stat1;
        ResultSet rs;
        Scanner console = new Scanner(System.in);
        try {
            RestaurantOwner o = new RestaurantOwner("", "", "");
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Select the number of the restaurant to see its menu:");
            String sql = "select restaurant_id , restaurant_name from restaurant ";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String RID = rs.getString("Restaurant_id");
                String Rname = rs.getString("Restaurant_name");
                System.out.println(RID + "-" + Rname);
            }
            System.out.println("Enter Your Choice: ");
            int choice = console.nextInt();
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + choice + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println(mID + "-" + mName + "," + Mprice + "," + mdetails);
            }
            int ans;
            do {
                System.out.print("To make an order Enter the meal name: ");
                mealName = input.next();
                sql = "Select meal_name from meals where meal_name='" + mealName + "'";
                rs = stat.executeQuery(sql);
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    sql = "select meal_price from meals where meal_name='" + mealName + "'";
                    rs = stat.executeQuery(sql);
                    double price = 0;
                    while (rs.next()) {
                        price = rs.getDouble("meal_price");
                    }
                    sql = "select quantity from meals where meal_name='" + mealName + "'";
                    rs = stat.executeQuery(sql);
                    int quantity = 0;
                    while (rs.next()) {
                        quantity = rs.getInt("quantity");
                    }
                    int orderedquantity;
                    System.out.println("Enter Quantity: ");
                    orderedquantity = input.nextInt();
                    if (orderedquantity < 0 || orderedquantity > quantity) {
                        System.out.println("The Quantity Unavaiable");
                    } else {
                        double TotalPrice = orderedquantity * price;
                        this.totalPrice += TotalPrice;
                        System.out.println("The Total Price is " + TotalPrice + "to Confirm order enter 1: ");
                        int conf = input.nextInt();
                        if (conf == 1) {
                            quantity -= orderedquantity;
                            sql = "UPDATE meals set quantity = '" + quantity + "' " + "Where meal_name = '" + mealName + "'";
                            stat1.executeUpdate(sql);
                            System.out.println("The Order Is Done");
                            setCustomerHistory();
                            //String d = java.time.LocalDate.now().toString() + " " + java.time.LocalTime.now().toString();
                            //History date = new History(d, mealName, orderedquantity);
                            //o.history.add(date);
                            //History order = new History(d, mealName, orderedquantity);
                            //this.Order.add(order);
                        } else {
                            System.out.println("Thanks For Using Our System");
                            return;
                        }
                    }
                } else {
                    System.out.println("The Meal Unavaiable");
                }
                System.out.print("to Make another order enter 1 else enter any number: ");
                ans = input.nextInt();
            } while (ans == 1);
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }
    //#################################################################################################################################
    public void setCustomerHistory()
    {
        Connection con;
        Statement stat;
        Statement stat1;
        ResultSet rs;
        String mealPrice = "";
        String rId = "";
        String restName = "";
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select meal_price , restaurant_id from meals where meal_name='" + mealName + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
             mealPrice = rs.getString("meal_price");
             rId =  rs.getString("restaurant_id");
            }
            sql = "select restaurant_name from Restaurant where restaurant_id='" + rId + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
             restName = rs.getString("restaurant_name");
            }
             sql = "insert into customer_history values('" + username +  "','" + restName  + "','" + mealName + "','" + mealPrice +"')";
            stat1.executeUpdate(sql);
            } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }
    
     //#################################################################################################################################
    public void showCustomerHistory()
    {
        Connection con;
        Statement stat;
        Statement stat1;
        ResultSet rs;
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select restaurant_name , meal_name , meal_price from customer_history where customer_name='" + username + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            String restName = rs.getString("restaurant_name");    
            String mName = rs.getString("meal_name");    
            String mealPrice = rs.getString("meal_price");
             System.out.println("restaurant name: " +restName + " , Meal name: "+mName + " , meal price: "+mealPrice + " LE .");
            }
            } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }
    public void Login_Customer() {
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
             username = console.nextLine();
            this.user = username;
            System.out.print("Enter your Password: ");
            String pass = console.nextLine();
            this.pass = pass;
            try {
                PasswordHashing p = new PasswordHashing();
                pass = p.GenerateHash(pass);
                String type = "customer";
                String sql = "select username,password from Table2 where username='" + username + "'and password='" + pass + "'and type='" + type + "'";
                rs = stat.executeQuery(sql);
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                    while (true) {
                        System.out.println("1.Browse Restaurants");
                        System.out.println("2.Order History");
                        System.out.println("3.Log out");
                        System.out.print("Please Enter Your Choice: ");
                        String Choice = input.next();
                        if (Choice.equals("1")) {
                            this.CheckforRestaurant();
                        } else if (Choice.equals("2")) {
                            this. showCustomerHistory();
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
                        Login_Customer();
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