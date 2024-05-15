package pbo.f01.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public class ContactDatabase extends AbstractDatabase {



    public ContactDatabase(String url) throws SQLException {
        super(url);
    }

    protected void createTables() throws SQLException {
        String Student = "CREATE TABLE IF NOT EXISTS student (" + 
            "id VARCHAR(255) NOT NULL PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "student_year INT NOT NULL," +
            "gender VARCHAR(255) NOT NULL," +
            "student_dorm VARCHAR(255) NOT NULL" +
            ")";
        

        String Dorm = "CREATE TABLE IF NOT EXISTS dorm (" + 
            "name VARCHAR(255) NOT NULL PRIMARY KEY," +
            "dorm_capacity INT NOT NULL," +
            "dorm_gender VARCHAR(255) NOT NULL" +
            ")";
        
        Statement statement = this.getConnection().createStatement();

        statement.execute("DROP TABLE IF EXISTS student");
        statement.execute("DROP TABLE IF EXISTS dorm");      

        statement.execute(Student);
        statement.execute(Dorm);

        statement.close();
    }


    //PRINT ALL STUDENTS
    public void printAll() throws SQLException {

        Statement statement = this.getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM dorm ORDER BY name ASC");

        while (resultSet.next()){
            String dormy = resultSet.getString("name");
            Integer capacity = this.Countcapacity(dormy);

            System.out.println(
                resultSet.getString("name") + "|" +
                resultSet.getString("dorm_gender") + "|" +
                resultSet.getInt("dorm_capacity") + "|" +
                capacity
            );


            //System.out.println(dormy);
           
            String sql = "SELECT * FROM student WHERE student_dorm = ? ORDER BY name ASC";
            PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
            pStatement.setString(1, dormy);
            ResultSet dorm = pStatement.executeQuery();

            while (dorm.next()){


                System.out.println(

                    dorm.getString("id") + "|" +
                    dorm.getString("name") + "|" +
                    dorm.getInt("student_year")

                );
            }
            
        }

        resultSet.close();
        statement.close();

    }

    private int Countcapacity(String dorm) throws SQLException{
        int total = 0;
           
        String sql = "SELECT * FROM student WHERE student_dorm = ?";
        PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
        pStatement.setString(1, dorm);
        ResultSet dormed= pStatement.executeQuery();

        while (dormed.next()){
            total++;
        }

        dormed.close();
        pStatement.close();

        return total;
    }

    public String CekGender(String dorm) throws SQLException{

        
        System.out.println(dorm);
        String sql = "SELECT * FROM dorm WHERE name = ?";
        PreparedStatement pStatement = this.getConnection().prepareStatement(sql);
        pStatement.setString(1, dorm);
        ResultSet dormed= pStatement.executeQuery();

            //String gender = dormed.getString("dorm_gender");

        //}


        return dormed.getString("dorm_gender");

    }

    


    //PRINT ALL ENROLLMENT
    public void AssignDorm(String id, String dorm) throws SQLException {
        String gender = "";

        String sqldorm = "SELECT * FROM dorm WHERE name = ?";
        PreparedStatement statementgdorm = this.getConnection().prepareStatement(sqldorm);
        statementgdorm.setString(1, dorm);
        ResultSet dormed= statementgdorm.executeQuery();

        while (dormed.next()){
            gender = dormed.getString("dorm_gender");
        }

        //String cekgender = CekGender(dorm);

        String sql = "UPDATE student SET student_dorm = ? WHERE id = ? AND gender = ?";
        PreparedStatement pStatement = this.getConnection().prepareStatement(sql);

        //ResultSet student = pStatement.executeQuery();

        // String doit = "SELECT * FROM dorm where name = ?";

        // PreparedStatement statement = this.getConnection().prepareStatement(doit);

        // statement.setString(1, dorm);

        // Integer capacity = ((ResultSet) statement).getInt("dorm_capacity");

        Integer maxcapa = this.Countcapacity(dorm);

        if ( maxcapa < 5){

        
            pStatement.setString(1, dorm);
            pStatement.setString(2, id);
            pStatement.setString(3, gender);
        //}
        } else{
            pStatement.setString(1, "None");
            pStatement.setString(2, id);
            pStatement.setString(3, gender);
        }
        pStatement.executeUpdate();

        pStatement.close();
    }
       
    @Override
    protected void prepareTables() throws SQLException {
        this.createTables();
        //this.seedTables();
    }

}