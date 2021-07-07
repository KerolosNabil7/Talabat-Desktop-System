package talabat;

import java.sql.*;
import java.util.*;

public class RestaurantOwner extends User {

    Scanner input = new Scanner(System.in);
    String restuarantName;
//    ArrayList<Meal> mealList = new ArrayList<Meal>() ;
//    ArrayList<History> history = new ArrayList<History>();

    public RestaurantOwner(String username, String password, String restuarantname) {
        super(username, password);
        this.restuarantName = restuarantname;
    }

    @Override
    public void SIGN_UP() {
        super.connect();
        String user;
        String pass;
        String resName;
        String confirm;
        String sql;
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
                System.out.println("Error");
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
        try {
            PasswordHashing p = new PasswordHashing();
            pass = p.GenerateHash(pass);
            System.out.print("Enter Your Phone: ");
            int phone = input.nextInt();
            System.out.print("Enter Your Adress: ");
            String address = input.next();
            String type = "owner";
            sql = "insert into Table2 values('" + user + "','" + pass + "','" + type +"','" + phone + "','" + address +"')";
            stat1.executeUpdate(sql);
            System.out.println("Account Created!");
        } catch (Exception ex) {
        }
        addRestaurant(user);
    }

    public static void addRestaurant(String ownerName) {
        Connection con;
        Statement stat1;
        Statement stat;
        ResultSet rs;
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
            sql = "insert into restaurant values('" + counter + "','" + name + "','" + add + "','" + phone + "','" + ownerName +"')";
            stat1.executeUpdate(sql);
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }

    public void addMeal() {
        Connection con;
        Statement stat1;
        Statement stat;
        ResultSet rs;
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Enter restaurant name:");
            String name = console.nextLine();
            String sql = "select restaurant_id from restaurant where restaurant_name='" + name + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            System.out.println("mealName:");
            String mealName = console.nextLine();
            System.out.println("mealdetails:");
            String mealDetails = console.nextLine();
            System.out.println("mealPrice:");
            double mealprice = console.nextDouble();
            System.out.println("meal Quantity:");
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
//###########################################################################################################
       public void showRestaurantHistory()
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
            System.out.println("Enter restaurant name: ");
            String restName = console.nextLine();
             String sql = "select customer_name , meal_name , meal_price from customer_history where restaurant_name='" +restName + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
            String custName = rs.getString("customer_name");    
            String mName = rs.getString("meal_name");    
            String mealPrice = rs.getString("meal_price");
             System.out.println("customer name: " +custName + " , Meal name: "+mName + " , meal price: "+mealPrice + " LE .");
            }
            } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }
//    public void Show() {
//        for (int i = 0; i < this.mealList.size(); i++) {
//            System.out.printf("Meal %d:\n", i + 1);
//            System.out.println("Name: " + this.mealList.get(i).Name);
//            System.out.println("Price: " + this.mealList.get(i).Price + " $");
//            System.out.println("Calories: " + this.mealList.get(i).Calories);
//            System.out.println("Time: " + this.mealList.get(i).Time + " Minute");
//            System.out.println("Quantity: " + this.mealList.get(i).Quantity);
//            System.out.println();
//        }
//    }
//
//    public void showHistory() {
//        System.out.print(this.history.size() + "\n");
//        for (int i = 0; i < this.history.size(); i++) {
//            this.history.get(i).displayHistory();
//        }
//    }

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
            this.user = user;
            System.out.print("Enter your Password: ");
            String pass = console.nextLine();
            this.pass = pass;
            try {
                PasswordHashing p = new PasswordHashing();
                pass = p.GenerateHash(pass);
                String type = "owner";
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
                        SignUp signUp = new SignUp();
                        return;
                    }
                }
            } catch (Exception ex) {

            }
            break;
        }
    }

    public void EditMeal_name(String newname) {
        String oldname;
        Connection con;
        Statement stat = null;
        ResultSet rs;
        Scanner input = new Scanner(System.in);
        Statement stat1 = null;
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        System.out.print("Enter the old name: ");
        oldname = input.nextLine();
        try {
            String sql = "Select meal_name from meals where meal_name = '" + oldname + "'";
            rs = stat.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
                sql = "update meals set meal_name='" + newname + "'" + "Where meal_name ='" + oldname + "'";
                stat1.executeUpdate(sql);
            } else {
                System.out.println("The Meal not found");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void EditMeal_price(double newprice) {
        String oldname;
        Connection con;
        Statement stat = null;
        ResultSet rs;
        Scanner input = new Scanner(System.in);
        Statement stat1 = null;
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        System.out.print("Enter the old name: ");
        oldname = input.nextLine();
        try {
            String sql = "Select meal_name from meals where meal_name = '" + oldname + "'";
            rs = stat.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
                System.out.print("Enter New price: ");
                newprice = input.nextDouble();
                sql = "update meals set meal_price='" + newprice + "'" + "Where meal_name ='" + oldname + "'";
                stat1.executeUpdate(sql);
            } else {
                System.out.println("The Meal not found");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void EditMeal_details(String newdetails) {
        String oldname;
        Connection con;
        Statement stat = null;
        ResultSet rs;
        Scanner input = new Scanner(System.in);
        Statement stat1 = null;
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        System.out.print("Enter the meal name to be edited: ");
        oldname = input.nextLine();
        try {
            String sql = "Select meal_name from meals where meal_name = '" + oldname + "'";
            rs = stat.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
                System.out.print("Enter New details: ");
                newdetails = input.nextLine();
                sql = "update meals set details='" + newdetails + "'" + "Where meal_name ='" + oldname + "'";
                stat1.executeUpdate(sql);
            } else {
                System.out.println("The Meal not found");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void EditMeal_quantity(int newQuantity) {
        String oldname;
        Connection con;
        Statement stat = null;
        ResultSet rs;
        Scanner input = new Scanner(System.in);
        Statement stat1 = null;
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://C:Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
        System.out.print("Enter the meal name to be edited: ");
        oldname = input.nextLine();
        try {
            String sql = "Select meal_name from meals where meal_name = '" + oldname + "'";
            rs = stat.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 1) {
                System.out.print("Enter New Quantity: ");
                newQuantity = input.nextInt();

                sql = "update meals set quantity='" + newQuantity + "'" + "Where meal_name ='" + oldname + "'";
                stat1.executeUpdate(sql);
            } else {
                System.out.println("The Meal not found");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void Remove_Meal() {
        Connection con;
        Statement stat1;
        Statement stat;
        ResultSet rs;
        Scanner console = new Scanner(System.in);
        try {
            con = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat = con.createStatement();
            stat1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Enter restaurant name:");
            String name = console.nextLine();
            String sql = "select restaurant_id from restaurant where restaurant_name='" + name + "'";
            rs = stat.executeQuery(sql);
            String id = "0";
            while (rs.next()) {
                id = rs.getString("restaurant_id");
            }
            //System.out.println("Select which meal you want to delete");
            sql = "select meal_id , meal_name , meal_price , details from meals where restaurant_id='" + id + "'";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                String mID = rs.getString("meal_id");
                String mName = rs.getString("meal_name");
                String Mprice = rs.getString("meal_price");
                String mdetails = rs.getString("details");
                System.out.println(mID + "-" + mName + "," + Mprice + "," + mdetails);
            }
            System.out.print("Enter the meal number to be removed: "); //////////////////////////////////////////////////////////////////////////////////
            int choice = console.nextInt();
            String sql1 = "delete from meals where meal_id = '" + choice + "' and restaurant_id ='" + id + "';";
            stat1.executeUpdate(sql1);
            System.out.println("Deleted successfully!");

        } catch (SQLException e) {
            for (Throwable ex : e) {
                System.err.println("Error occurred " + ex);
            }
        }
    }
}
