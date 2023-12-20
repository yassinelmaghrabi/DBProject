import java.sql.*;


public class PharmacyAPI {


    public Connection con = null;


    public PharmacyAPI(String url,String username,String password) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed");
            throw new RuntimeException(e);
        }
        try {
            this.con = DriverManager.getConnection(
                    url, username, password);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            System.out.println("Cannot form connection");
            throw new RuntimeException(e);
        }

    }


}
