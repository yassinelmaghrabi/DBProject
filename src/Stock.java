import javax.lang.model.type.ArrayType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Stock {
    Connection con;

    Stock(PharmacyAPI pharma) {
        con = pharma.con;
    }

    private boolean checkIfExist(int medicine_id , int branch_id , ArrayList<ArrayList<String>> table){
        boolean flag = false;
        for(int i=0;i<table.get(0).size();i++){
            if(medicine_id == Integer.parseInt(table.get(2).get(i)) && branch_id == Integer.parseInt(table.get(1).get(i)) )
                flag=true;

        }

        return flag;
    }
    public void insert(int branchId,int medicineId , int amount ) throws SQLException {
        if(checkIfExist(branchId , medicineId , selectall())) {
           update(branchId,medicineId,amount);
        }else{
            PreparedStatement ps = con.prepareStatement("INSERT INTO STOCK (Branch_id,Medicine_id,amount) VALUES(?,?,?)");
            ps.setInt(1, branchId);
            ps.setInt(2, medicineId);
            ps.setInt(3, amount);
            ps.executeUpdate();
        }

    }
    public void update(int branchId,int medicineId , int amount ) throws SQLException {
        if(checkIfExist(branchId,medicineId,selectall())) {
            PreparedStatement ps = con.prepareStatement("UPDATE STOCK SET amount=? WHERE branch_id=?,medicine_id=?");
            ps.setInt(1, amount);
            ps.setInt(2, branchId);
            ps.setInt(3, medicineId);
            ps.executeUpdate();
        }else{
            insert(branchId,medicineId,amount);
        }
    }

    public int delete(int medicineId, int branchId) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Stock WHERE branch_id=?,medicine_id=?");
        ps.setInt(1, branchId);
        ps.setInt(2, medicineId);
        return ps.executeUpdate();
    }

    public int deleteM(int medicineId) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Stock WHERE medicine_id=?");
        ps.setInt(1, medicineId);
        return ps.executeUpdate();
    }
    public int deleteB(int branchId) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Stock WHERE branch_id=?");
        ps.setInt(1, branchId);
        return ps.executeUpdate();
    }
    public String get(int medicineId ,int branchId) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from stock where medicine_id=?,branch_id=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,medicineId);
        ps.setInt(2,branchId);
        ResultSet result=ps.executeQuery();
        result.first();
        StringBuilder stb = new StringBuilder();
        return String.valueOf(stb);
    }
    public ArrayList<ArrayList<String>> selectall() throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from Stock",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result=ps.executeQuery();
        ArrayList<ArrayList<String>> twoDArrayList = new ArrayList<>();
        int size = 5;

        for(int i=0;i<size;i++){
            twoDArrayList.add(new ArrayList<>());
            result.beforeFirst();
            while (result.next()) {
                twoDArrayList.get(i).add(result.getString(i+1));
            }
        }

        return twoDArrayList;
    }
}
