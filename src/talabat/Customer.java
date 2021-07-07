package talabat;

import java.sql.*;

public class Customer extends User {

    private String confirm;
    private String sql;
    private String address;
    private int numphone;
    private String mealPrice = "";
    private String rId = "";
    private String restName = "";
    private int orderedquantity;
    private String order_date = java.time.LocalDate.now().toString() + " " + java.time.LocalTime.now().toString();
    private String addtional_notes;
    private String mealName;
    
//    public Customer(String username, String password, int phone, String address) {
//        super(username, password);
//        numphone = phone;
//        address = this.address;
//    }

    @Override
    public void SIGN_UP() {
        super.connect();
        while (true) {
            System.out.print("Enter your username:");
            user = console.next();
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
            pass = console.next();
            if (ValidPassword(pass)) {
                System.out.print("Confirm your password:");
                confirm = console.next();
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
        numphone = console.nextInt();
        System.out.print("Enter Your Adress: ");
        address = console.next();
        try {
            PasswordHashing p = new PasswordHashing();
            pass += user;
            pass = p.GenerateHash(pass);
            String type = "customer";
            sql = "insert into Table2 values('" + user + "','" + pass + "','" + type + "','" + numphone + "','" + address + "')";
            stat1.executeUpdate(sql);
            System.out.println("Account Created!");
        } catch (Exception ex) {
            System.out.println("kerpps");
        }
    }

    public void CheckforRestaurant() {
        try {
            RestaurantOwner o = new RestaurantOwner();
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
                System.out.println(mID+"- "+mName);
                System.out.println("Price: " + Mprice);
                System.out.println("Details: " + mdetails);
                System.out.println("---------------------------------------------------------");
            }
            int ans;
            do {
                System.out.print("To make an order Enter the meal name: ");
                mealName = console.next();
                sql = "Select meal_name from meals where meal_name='" + mealName + "'";
                rs = stat.executeQuery(sql);
                boolean isfound = false;
                while (rs.next()) {
                    isfound = true;
                }
                if (isfound) {
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
                    
                    System.out.println("Additional Notes:");
                    addtional_notes = console.next();
                    System.out.println("Enter Quantity: ");
                    orderedquantity = console.nextInt();
                    if (orderedquantity < 0 || orderedquantity > quantity) {
                        System.out.println("The Quantity Unavaiable");
                    } else {
                        double totalPrice = 0;
                        totalPrice += orderedquantity * price;
                        System.out.println("The Total Price is " + totalPrice + "to Confirm order enter 1: ");
                        int conf = console.nextInt();
                        if (conf == 1) {
                            quantity -= orderedquantity;
                            sql = "UPDATE meals set quantity = '" + quantity + "' " + "Where meal_name = '" + mealName + "'";
                            stat1.executeUpdate(sql);
                            System.out.println("The Order Is Done");
                            setCustomerHistory();
                        } else {
                            System.out.println("Thanks For Using Our System");
                            return;
                        }
                    }
                } else {
                    System.out.println("The Meal Unavaiable");
                }
                System.out.print("to Make another order enter 1 else enter any number: ");
                ans = console.nextInt();
            } while (ans == 1);
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void setCustomerHistory() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select meal_price , restaurant_id from meals where meal_name='" + mealName + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                mealPrice = rs.getString("meal_price");
                rId = rs.getString("restaurant_id");
            }
            sql = "select restaurant_name from Restaurant where restaurant_id='" + rId + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                restName = rs.getString("restaurant_name");
            }
            sql = "insert into customer_history values('" + user + "','" + restName + "','" + mealName + "','" + mealPrice + "','" + orderedquantity + "','" + order_date + "','" + addtional_notes + "')";
            stat1.executeUpdate(sql);
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void showCustomerHistory() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select restaurant_name , meal_name , meal_price ,quantity,order_date from customer_history where customer_name='" + user + "'";
            rs = stat.executeQuery(sql);
            int t = 0;
            while (rs.next()) {
                String restName = rs.getString("restaurant_name");
                String mName = rs.getString("meal_name");
                String date = rs.getString("order_date");
                int mealPrice = rs.getInt("meal_price");
                int quan = rs.getInt("quantity");
                System.out.println("Restaurant Name: " + restName);
                System.out.println("Meal Name: " + mName);
                System.out.println("Meal Price: " + mealPrice + "EGP");
                System.out.println("Quantity: " + quan);
                System.out.println("Date: " + date);
                t += mealPrice * quan;
                System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
            }
            System.out.println("Total= " + t+"EGP");
            System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void Login_Customer() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        while (true) {
            System.out.print("Enter your username: ");
            user = console.next();
            System.out.print("Enter your Password: ");
            pass = console.next();
            pass += user;
            try {
                PasswordHashing p = new PasswordHashing();
                pass = p.GenerateHash(pass);
                String type = "customer";
                String sql = "select username,password from Table2 where username='" + user + "'and password='" + pass + "'and type='" + type + "'";
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
                        String Choice = console.next();
                        if (Choice.equals("1")) {
                            this.CheckforRestaurant();
                        } else if (Choice.equals("2")) {
                            this.showCustomerHistory();
                        } else if (Choice.equals("3")) {
                            break;
                        } else {
                            System.out.print("unknown option \n");
                        }
                    }
                } else {
                    System.out.println("User not found,if you want to try again press 1\n if you want to create an account press 2:");
                    int choice = console.nextInt();
                    if (choice == 1) {
                        Login_Customer();
                    } else {
                        this.SIGN_UP();
                    }
                }
            } catch (Exception ex) {

            }
            break;
        }
    }
}