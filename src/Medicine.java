
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Medicine {
    Connection con;

    Medicine(PharmacyAPI pharma) {
        con = pharma.con;
    }


    public void insert(int medicineId , int price, String medicineName, int mCode) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO MEDICINE VALUES(?,?,?,?)");
        ps.setInt(1, medicineId);
        ps.setInt(2, price);
        ps.setString(3, medicineName);
        ps.setInt(4, mCode);
        ps.executeUpdate();
    }

    public int update(int medicineId ,int price) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE MEDICINE SET PRICE=? WHERE MEDICINE_ID=?");
        ps.setInt(1, price);
        ps.setInt(2, medicineId);
        return ps.executeUpdate();
    }

    public int delete(int medicineId) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM MEDICINE WHERE MEDICINE_ID=?");
        ps.setInt(1, medicineId);
        return ps.executeUpdate();
    }

    public String get(int medicineId) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from MEDICINE where MEDECINE_ID=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,medicineId);
        ResultSet result=ps.executeQuery();
        result.first();
        StringBuilder stb = new StringBuilder();
        stb.append("Medicine name = ").append(medicineId).append(": ");
        stb.append("Price : ").append(result.getInt(2));
        stb.append("Manufacturer Name").append(result.getString(3));
        return stb.toString();
    }
    public ArrayList<ArrayList<String>> selectall() throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from MEDICINE",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result=ps.executeQuery();
        ArrayList<ArrayList<String>> twoDArrayList = new ArrayList<>();
        int size = 4;

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