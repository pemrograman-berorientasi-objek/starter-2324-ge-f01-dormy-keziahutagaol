package pbo.f01.model;

import java.sql.PreparedStatement;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public class Dorm {
    public void dormAdd(String _dormName, Integer _dormCapacity,String _dormGender, ContactDatabase _database) throws Exception {
        String sql = "INSERT INTO dorm (name, dorm_capacity, dorm_gender) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = _database.connection.prepareStatement(sql);
        preparedStatement.setString(1, _dormName);
        preparedStatement.setInt(2, _dormCapacity);
        preparedStatement.setString(3, _dormGender);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }
}
