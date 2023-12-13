import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class PharmacyAPI {
   static private String url = "jdbc:mysql://localhost:3306/pharmacy";
   static private String username = "root";
   static private String password = "password";
   Connection con = null;

    public static void setUrl(String url) {
        PharmacyAPI.url = url;
    }

    public static void setPassword(String password) {
        PharmacyAPI.password = password;
    }

    public static void setUsername(String username) {
        PharmacyAPI.username = username;
    }
    public PharmacyAPI() {
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
    public void insert_into(int id,String Name,int Pharmacy_id,double SSN,int Managed_by) throws SQLException {
        Statement st = con.createStatement();
        StringBuilder stb = new StringBuilder("INSERT INTO EMPLOYEE");
        stb.append(" values(");
        stb.append(id).append(",");
        stb.append("'" +Name+"'").append(",");
        stb.append(Pharmacy_id).append(",");
        stb.append(SSN).append(",");
        stb.append(Managed_by);
        stb.append(");");
        System.out.println(stb.toString());
        int result=st.executeUpdate(stb.toString());
    }

}
