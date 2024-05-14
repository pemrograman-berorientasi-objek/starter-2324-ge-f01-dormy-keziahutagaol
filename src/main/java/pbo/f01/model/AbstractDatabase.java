package pbo.f01.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 12S22014 Kezia Hutagaol
 * @author 12S22034 Mulyadi Yamin Siahaan
 */

public abstract class AbstractDatabase {

    protected String url ="jdbc:h2:./db/dormy";
    protected Connection connection = null;
    
    public AbstractDatabase(String url) throws SQLException {
        this.url = url;
        this.prepareTables();
    }

    protected Connection getConnection() throws SQLException {
        if (this.connection == null) {
            connection = DriverManager.getConnection(this.url);
        }
        return this.connection;
    }

    public void shutdown() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }
    
    protected abstract void prepareTables() throws SQLException;{
        this.createTables();
    }

    protected abstract void createTables() throws SQLException;{
        
    }


}
