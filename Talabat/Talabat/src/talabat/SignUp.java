package talabat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class SignUp {

    Connection con1;
    Statement stat1;
    Statement stat;
    ResultSet rs1;
    Scanner console = new Scanner(System.in);

    public SignUp() {
        connect();
        frame();

    }

    public void connect() {
        try {
            con1 = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stat = con1.createStatement();
        } catch (Exception ex) {
            System.out.print("something wrong");
        }
    }

    public void frame() {
        String user;
        String pass;
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
            sql = "insert into Table2 values('" + user + "','" + pass + "')";
            stat1.executeUpdate(sql);
            System.out.println("Account Created!");
        } catch (Exception ex) {

        }
    }

    public static boolean ValidPassword(String password) {
        // Regex to check valid password. 
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean confirmPassword(String password, String confirm) {
        return password.equals(confirm);
    }
}
