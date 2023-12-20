// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    PharmacyAPI pharma = new PharmacyAPI("jdbc:mysql://localhost:3306/pharmacy","root","password");
    Employee employee = new Employee(pharma);
    Branch branch = new Branch(pharma);
    pharma.con.prepareStatement("Delete From Employee_Phone_number where SSN=1").executeUpdate();
    employee.delete(1);
    branch.delete(1);
    branch.insert(1,"victoria");


    employee.insert(1,"a7a",123,1,"01023348752");
    employee.insertPN(1,"01024448752");
    employee.insertPN(1,"01024448753");
    System.out.println(employee.get(1));

    }
}