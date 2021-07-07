package talabat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class User {

    protected String user;
    protected String pass;
    protected static Connection con;
    protected static Statement stat;
    protected static ResultSet rs;
    protected static Connection con1;
    protected static Statement stat1;
    protected static ResultSet rs1;
    Scanner console = new Scanner(System.in);
//    public User(String username, String password) {
//        this.user = username;
//        this.pass = password;
//    }
    public abstract void SIGN_UP();
    public void connect() {
        try {
            con1 = DriverManager.getConnection("jdbc:ucanaccess://Database11.accdb");
            stat1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stat = con1.createStatement();
        } catch (Exception ex) {
            System.out.println("something wrong");
        }
    }
    public static boolean ValidPassword(String password) {
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