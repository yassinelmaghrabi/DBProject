import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Employee {
    Connection con;
    Employeephonenumber EPN;

    public Employee(PharmacyAPI pharma){

        this.con = pharma.con;
        this.EPN = new Employeephonenumber(pharma.con);

    }
    public void insert(int SSN, String Name, int salary, int branchid,String phonenumber) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO employee VALUES(?,?,?,null,?)");
        Date date = new Date();
        ps.setInt(1,SSN);
        ps.setString(2,Name);
        ps.setInt(3,salary);
        ps.setInt(4,branchid);
        

        ps.executeUpdate();
        EPN.insert(SSN, phonenumber);

    }
    public void insert(int SSN, String Name, int salary, int branchid,int manager_id,String phonenumber) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?)");
        ps.setInt(1,SSN);
        ps.setString(2,Name);
        ps.setInt(3,salary);
        ps.setInt(5,branchid);
        ps.setInt(4,manager_id);

        ps.executeUpdate();
        EPN.insert(SSN, phonenumber);
    }
    public int updatename(int SSN,String Name) throws SQLException{
        PreparedStatement ps = con.prepareStatement("UPDATE employee SET name=? WHERE SSN=?");
        ps.setString(1,Name);
        ps.setInt(2,SSN);
        return ps.executeUpdate();
    }
    public int updatemanager(int SSN,int managerSSN) throws SQLException{
        PreparedStatement ps = con.prepareStatement("UPDATE employee SET Manager_SSN=? WHERE SSN=?");
        ps.setInt(1,managerSSN);
        ps.setInt(2,SSN);
        return ps.executeUpdate();
    }
    public int updatesalary(int SSN,int salary) throws SQLException{
        PreparedStatement ps = con.prepareStatement("UPDATE employee SET salary=? WHERE SSN=?");
        ps.setInt(1,salary);
        ps.setInt(2,SSN);
        return ps.executeUpdate();
    }
    public int deletePN(String phonenumber) throws SQLException{
        return EPN.delete(phonenumber);
    }


    public void insertPN(int SSN, String phonenumber) throws SQLException {
        EPN.insert(SSN, phonenumber);
    }

    public int delete(int id) throws SQLException{
        PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE SSN=?");
        ps.setInt(1,id);
        return ps.executeUpdate();
    }
    public ArrayList<ArrayList<String>> selectall() throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from employee", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result=ps.executeQuery();
        ArrayList<ArrayList<String>> twoDArrayList = new ArrayList<>();
        int size = 4;

        for(int i=0;i<size;i++){
            twoDArrayList.add(new ArrayList<>());
            result.beforeFirst();
            while (result.next()) {
                twoDArrayList.get(i).add(result.getString(i+1));
            }
        }

        return twoDArrayList;
    }
    public String get(int id) throws SQLException{
        PreparedStatement ps = con.prepareStatement("SELECT * from employee where SSN=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,id);
        ResultSet result=ps.executeQuery();
        ps = con.prepareStatement("SELECT * from Employee_Phone_number where SSN=?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,id);
        ResultSet result2=ps.executeQuery();
        result.first();
        StringBuilder stb = new StringBuilder();
        stb.append("SSN = ").append(id).append(": ");
        stb.append(" Name:").append(result.getString(2));
        stb.append(" Salary:").append(result.getString(3));
        stb.append(" Manager SSN:").append(result.getInt(4));
        stb.append(" Phone numbers:");
        result2.beforeFirst();
        while (result2.next()) {
            stb.append(" ").append(result2.getString(1)).append(",");
        }
        stb.replace(stb.length()-1,stb.length(),".");
        return stb.toString();
    }
}
