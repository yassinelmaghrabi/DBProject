import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillMedicine {
    Connection con;
    public BillMedicine(Connection con){
        this.con = con;
    }

    public void insert(int billCode,int medicineId,int amount) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO billmedicine VALUES(?,?,?)");
        ps.setInt(1,medicineId);
        ps.setInt(2,billCode);
        ps.setInt(3,amount);
        updateStock(billCode,medicineId,amount);
        ps.executeUpdate();
    }
    public void updateStock(int branchId , int medicineId , int amount) throws SQLException{
            PreparedStatement ps = con.prepareStatement("UPDATE STOCK SET amount=amount-? WHERE branch_id=? and medicine_id=? and amount>=?");
            ps.setInt(1, amount);
             ps.setInt(4, amount);
            ps.setInt(2, branchId);
            ps.setInt(3, medicineId);
            ps.executeUpdate();
    }
    public int getprice(int bill_code) throws SQLException {
        int price = 0;
        ResultSet result2;
        PreparedStatement ps = con.prepareStatement("select Medicine_id,amount from billmedicine where bill_code=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,bill_code);
        ResultSet result=ps.executeQuery();
        result.beforeFirst();


        while (result.next()){
            ps = con.prepareStatement("select price from medicine where medicine_id=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1,result.getInt(1));
            result2 = ps.executeQuery();
            result2.first();
            price += result2.getInt(1)*result.getInt(2);

        }
        return price;
    }

}
