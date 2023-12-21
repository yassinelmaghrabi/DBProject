import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bill extends BillMedicine{
    Connection con;

    static int currBillCode;
    int employeeSSN;
    int branchId;


    public Bill(PharmacyAPI pharma,int branchId,int employeeSSN) throws SQLException {
        super(pharma.con);
        this.con = pharma.con;
        this.branchId=branchId;
        this.employeeSSN=employeeSSN;
        currBillCode = getlastbill();
    }

    private int getlastbill() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT BILL_CODE FROM BILL",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = ps.executeQuery();
        int last=0;
        result.beforeFirst();
        while (result.next()){
            last=result.getInt(1);
        }

        return last+1;
    }
    public void insert (String phoneNumber)throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO bill VALUES (?,null,?,?,?,?)");
        ps.setInt(1,currBillCode);
        ps.setTimestamp(2,java.sql.Timestamp.from(java.time.Instant.now()));
        ps.setInt(3,branchId);
        ps.setInt(4,employeeSSN);
        ps.setString(5,phoneNumber);
        ps.executeUpdate();

    }
    public void insertbm(int billCode,int medicineId,int amount) throws SQLException {
        super.insert(billCode,medicineId,amount);
        this.updatetotalprice(billCode);
        super.updateStock(branchId,medicineId,amount);
    }
    public void updatetotalprice(int billcode) throws SQLException {
        System.out.println(super.getprice(1001));
        PreparedStatement ps = con.prepareStatement("UPDATE Bill SET Total_price = ? WHERE Bill_code = ?");
        ps.setInt(1,super.getprice(billcode));
        ps.setInt(2,billcode);
        ps.executeUpdate();
    }
    public String get(int Bill_code) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from bill where bill_code=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,Bill_code);
        ResultSet result=ps.executeQuery();
        ps = con.prepareStatement("SELECT * from billmedicine where bill_code=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,Bill_code);
        ResultSet result2=ps.executeQuery();
        result.first();
        StringBuilder stb = new StringBuilder();
        stb.append("Bill Code = ").append(Bill_code).append(": ");
        stb.append(" Total price :").append(result.getString(2));
        stb.append(" Medicine ID:").append(result.getString(3));
        stb.append(" Branch ID :").append(result.getInt(4));
        stb.append(" Employee ssn :").append(result.getInt(5));
        stb.append(" MedicineID+amount:");
        result2.beforeFirst();
        while (result2.next()) {
            stb.append(" ").append(result2.getString(1)).append(": ").append(result2.getString(3)).append(',');
        }
        stb.replace(stb.length()-1,stb.length(),".");
        return stb.toString();
    }





}
