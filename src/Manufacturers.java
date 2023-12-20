
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Manufacturers {

    Connection con;

    Manufacturers(PharmacyAPI pharma) {
        con = pharma.con;
    }
    public void insert(int mCode, String name) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO MANUFACTURERS VALUES(?,?)");
        ps.setInt(1, mCode);
        ps.setString(2, name);
        ps.executeUpdate();
    }

    public int update(int mCode, String name) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE MANUFACTURERS SET NAME=? WHERE M_CODE=?");
        ps.setString(1, name);
        ps.setInt(2, mCode);
        return ps.executeUpdate();
    }

    public int delete(int mCode) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM MANUFACTURERS WHERE M_CODE=?");
        ps.setInt(1, mCode);
        return ps.executeUpdate();
    }

    public String get(int mCode) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from MANUFACTURERS where M_CODE=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,mCode);
        ResultSet result=ps.executeQuery();
        result.first();
        StringBuilder stb = new StringBuilder();
        stb.append("Manofacturers id = ").append(mCode).append(": ");
        stb.append("Manofactorers Name:").append(result.getInt(2));

        return stb.toString();
    }
    public ArrayList<ArrayList<String>> selectall() throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from MANUFACTURERS",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result=ps.executeQuery();
        ArrayList<ArrayList<String>> twoDArrayList = new ArrayList<>();
        int size = 2;

        for(int i=0;i<size;i++){
            twoDArrayList.add(new ArrayList<>());
            System.out.println(i);
            result.beforeFirst();
            while (result.next()) {
                twoDArrayList.get(i).add(result.getString(i+1));
            }
        }

        return twoDArrayList;
    }
}

