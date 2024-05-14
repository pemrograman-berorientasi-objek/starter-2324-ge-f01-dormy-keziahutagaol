package pbo.f01.model;

import java.sql.PreparedStatement;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public class Student {
    public void studentAdd(String _studentId, String _studentName, Integer _studentYear, String _studentGender, String _grade, ContactDatabase _database) throws Exception {
        String sql = "INSERT INTO student (id, name, student_year, gender, student_dorm) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = _database.connection.prepareStatement(sql);
        preparedStatement.setString(1, _studentId);
        preparedStatement.setString(2, _studentName);
        preparedStatement.setInt(3, _studentYear);
        preparedStatement.setString(4, _studentGender);
        preparedStatement.setString(5, _grade);
        
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }
}
