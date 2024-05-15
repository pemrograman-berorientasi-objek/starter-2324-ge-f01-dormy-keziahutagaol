package pbo.f01.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public class Student {
    public void studentAdd(String _studentId, String _studentName, Integer _studentYear, String _studentGender, String _grade, ContactDatabase _database) throws Exception {
        // Cek apakah ID siswa sudah ada dalam database
        String checkSql = "SELECT COUNT(*) FROM student WHERE id = ?";
        PreparedStatement checkStatement = _database.connection.prepareStatement(checkSql);
        checkStatement.setString(1, _studentId);
        ResultSet resultSet = checkStatement.executeQuery();
        
        resultSet.next();
        int count = resultSet.getInt(1);
        
        resultSet.close();
        checkStatement.close();
        
        if (count == 0) {
            // Jika ID tidak ada, lakukan INSERT
            String insertSql = "INSERT INTO student (id, name, student_year, gender, student_dorm) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = _database.connection.prepareStatement(insertSql);
            insertStatement.setString(1, _studentId);
            insertStatement.setString(2, _studentName);
            insertStatement.setInt(3, _studentYear);
            insertStatement.setString(4, _studentGender);
            insertStatement.setString(5, _grade);
            
            insertStatement.executeUpdate();
            insertStatement.close();
        } 
    }
}
