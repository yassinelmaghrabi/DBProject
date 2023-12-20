import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employeephonenumber {
    Connection con;
    public Employeephonenumber(Connection con){
        this.con = con;
    }
    public void insert(int SSN,String phonenumber) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Employee_Phone_number VALUES(?,?)");
        ps.setString(1,phonenumber);
        ps.setInt(2,SSN);
        ps.executeUpdate();
    }
    public int delete(String phonenumber) throws SQLException{
        PreparedStatement ps = con.prepareStatement("DELETE FROM Employee_Phone_number where Phone_number=? ");
        ps.setString(1,phonenumber);

        return ps.executeUpdate();
    }
    //add get method
}
