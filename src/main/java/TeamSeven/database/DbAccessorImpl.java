package TeamSeven.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by joshoy on 16/4/17.
 */
public class DbAccessorImpl extends DbAccessor {

    public DbAccessorImpl() {
        try {
            //  Register JDBC driver
            Class.forName(this.JDBC_DRIVER);
            System.out.println("Connecting to database...");
            this.dbConn = DriverManager.getConnection(this.DB_URL, this.USERNAME, this.PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeSQLQueryStatement(String sql) throws SQLException {
        if (this.stmt == null) {
            stmt = this.dbConn.createStatement();
        }
        else {
            this.stmt.close();
            stmt = this.dbConn.createStatement();
        }
        ResultSet rs = this.stmt.executeQuery(sql);
        return rs;
    }

    @Override
    public void executeSQLUpdateStatement(String sql) throws SQLException {
        if (this.stmt == null) {
            stmt = this.dbConn.createStatement();
        }
        else {
            this.stmt.close();
            stmt = this.dbConn.createStatement();
        }
        stmt.executeUpdate(sql);
    }

    @Override
    public void executeSQLStatement(String sql) throws SQLException {
        if (this.stmt == null) {
            stmt = this.dbConn.createStatement();
        }
        else {
            this.stmt.close();
            stmt = this.dbConn.createStatement();
        }
        stmt.execute(sql);
    }

    @Override
    public void endStatement() {
        if (this.stmt != null) {
            try {
                this.stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.stmt = null;
        }
    }

    @Override
    public void endConnection() {
        if (this.stmt != null) {
            this.endStatement();
        }
        if (this.dbConn != null) {
            try {
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbConn = null;
        }
    }

}
