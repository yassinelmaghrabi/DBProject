// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.sql.*;
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//
//        String url = "jdbc:mysql://localhost:3306/pharmacy";
//        String username = "root";
//        String password = "password";
//        String query
//                = "insert into pharmacy values(109,'egypt')";
//
//
//        Connection con = DriverManager.getConnection(
//                url, username, password);
//
//
//        Statement st = con.createStatement();
//
//        int count = st.executeUpdate(query);
//        System.out.println(
//                "number of rows returned by this query= "
//                        + count);
//
//        con.close();

        PharmacyAPI pharma = new PharmacyAPI();
        pharma.insert_into(2,"yassin",1,23,2);

    }
}