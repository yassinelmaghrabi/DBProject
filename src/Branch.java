

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Branch {

    Connection con;

    Branch(PharmacyAPI pharma) {
        con = pharma.con;
    }


    public void insert(int id, String location) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO BRANCH VALUES(?,?)");
        ps.setInt(1, id);
        ps.setString(2, location);
        ps.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM branch WHERE Branch_id=?");
        ps.setInt(1, id);
        return ps.executeUpdate();
    }

    public String get(int id) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from branch where branch_id=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,id);
        ResultSet result=ps.executeQuery();
        result.first();
        StringBuilder stb = new StringBuilder();
        stb.append("Branch id = ").append(id).append(": ");
        stb.append(" Location:").append(result.getString(2));
        return stb.toString();
    }
    public ArrayList<ArrayList<String>> selectall() throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from branch",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
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