package talabat;

import java.sql.*;
import java.util.*;

public class RestaurantOwner extends User {

    private String confirm;
    private String address;
    private String type;
    private String restuarantName;

//    public RestaurantOwner(String username, String password, String restuarantname) {
//        super(username, password);
//        this.restuarantName = restuarantname;
//    }
//    
    @Override
    public void SIGN_UP() {
        super.connect();
        String sql;
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
                System.out.println("Error");
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
        try {
            PasswordHashing p = new PasswordHashing();
            pass += user;
            pass = p.GenerateHash(pass);
            System.out.print("Enter Your Phone: ");
            int phone = console.nextInt();
            System.out.print("Enter Your Adress: ");
            address = console.next();
            type = "owner";
            sql = "insert into Table2 values('" + user + "','" + pass + "','" + type + "','" + phone + "','" + address + "')";
            stat1.executeUpdate(sql);
            System.out.println("Account Created!");
        } catch (Exception ex) {
        }
        addRestaurant(user);
    }

    public static void addRestaurant(String ownerName) {
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.print("Enter restaurant name:");
            String name = console.nextLine();
            System.out.print("Enter restaurant address:");
            String add = console.nextLine();
            System.out.print("Enter restaurant number:");
            String phone = console.next();
            String sql = "select * from restaurant";
            rs = stat.executeQuery(sql);
            int counter = 1;
            while (rs.next()) {
                counter++;
            }
            sql = "insert into restaurant values('" + counter + "','" + name + "','" + add + "','" + phone + "','" + ownerName + "')";
            stat1.executeUpdate(sql);
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void addMeal() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select restaurant_id from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            System.out.print("meal Name: ");
            String mealName = console.next();
            System.out.print("Meal Details: ");
            String mealDetails = console.next();
            System.out.print("Meal Price: ");
            double mealprice = console.nextDouble();
            System.out.print("meal Quantity:");
            int quantity = console.nextInt();
            sql = "select * from meals where restaurant_id ='" + id + "'";
            rs = stat.executeQuery(sql);
            int counter = 1;
            while (rs.next()) {
                counter++;
            }
            String sql1 = "insert into meals values('" + id + "','" + mealName + "','" + mealprice + "','" + mealDetails + "','" + counter + "','" + quantity + "')";
            stat1.executeUpdate(sql1);
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void showRestaurantHistory() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select restaurant_name from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String restName = "";
            while (rs.next()) {
                restName = rs.getString("restaurant_name");
            }
            sql = "select customer_name , meal_name , meal_price ,order_date , notes, quantity from customer_history where restaurant_name='" + restName + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String custName = rs.getString("customer_name");
                String mName = rs.getString("meal_name");
                String mealPrice = rs.getString("meal_price");
                String date = rs.getString("order_date");
                String notes = rs.getString("notes");
                int quantity = rs.getInt("quantity");
                System.out.println("Customer name: " + custName);
                System.out.println("Meal Name: " + mName);
                System.out.println("Quantity: " + quantity);
                System.out.println("Additional Notes: " + notes);
                System.out.println("Order Date: " + date);
                System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
            }
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void Login_Restaurantowner() {
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
                type = "owner";
                String sql = "select username,password from Table2 where username='" + user + "'and password='" + pass + "'and type='" + type + "'";
                rs = stat.executeQuery(sql);
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                if (count == 1) {
                } else {
                    System.out.println("User not found,if you want to try again press 1\n if you want to create an account press 2:");
                    int choice = console.nextInt();
                    if (choice == 1) {
                        Login_Restaurantowner();
                    } else {
                        this.SIGN_UP();
                    }
                }
            } catch (Exception ex) {

            }
            break;
        }
    }
    
    public void EditMeal(String newname) {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        try {
            String sql = "select restaurant_id from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + id + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println("Meal Name: "+mName);
                System.out.println("Price: " + Mprice);
                System.out.println("Details: " + mdetails);
                System.out.println("---------------------------------------------------------");
            }
            System.out.print("Enter the meal name to be edited: ");
            String choice = console.next();

            sql = "update meals set meal_name='" + newname + "'" + "Where meal_name ='" + choice + "'";
            stat1.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void EditMeal(double newprice) {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        try {
            String sql = "select restaurant_id from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + id + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println("Meal Name: "+mName);
                System.out.println("Price: " + Mprice);
                System.out.println("Details: " + mdetails);
                System.out.println("---------------------------------------------------------");
            }
            System.out.print("Enter the meal name to be edited: ");
            String choice = console.next();

            sql = "update meals set meal_price='" + newprice + "'" + "Where meal_name ='" + choice + "'";
            stat1.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void EditMeal_details(String newdetails) {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        try {
            String sql = "select restaurant_id from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + id + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println("Meal Name: "+mName);
                System.out.println("Price: " + Mprice);
                System.out.println("Details: " + mdetails);
                System.out.println("---------------------------------------------------------");
            }
            System.out.print("Enter the meal name to be edited: ");
            String choice = console.next();

            sql = "update meals set meal_details='" + newdetails + "'" + "Where meal_name ='" + choice + "'";
            stat1.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void EditMeal_quantity(int newQuantity) {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        try {
            String sql = "select restaurant_id from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + id + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println("Meal Name: "+mName);
                System.out.println("Price: " + Mprice);
                System.out.println("Details: " + mdetails);
                System.out.println("---------------------------------------------------------");
            }
            System.out.print("Enter the meal name to be edited: ");
            String choice = console.next();
            sql = "update meals set quantity='" + newQuantity + "'" + "Where meal_name ='" + choice + "'";
            stat1.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void Remove_Meal() {
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select restaurant_id from restaurant where restaurant_owner='" + user + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + id + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println("Meal Name: "+mName);
                System.out.println("Price: " + Mprice);
                System.out.println("Details: " + mdetails);
                System.out.println("---------------------------------------------------------");
            }
            System.out.print("Enter the meal name to be removed: ");
            String choice = console.next();
            String sql1 = "delete from meals where meal_name = '" + choice + "' and restaurant_id ='" + id + "';";
            stat1.executeUpdate(sql1);
            System.out.println("Deleted successfully!");

        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }
}