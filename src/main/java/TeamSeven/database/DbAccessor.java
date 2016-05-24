package TeamSeven.database;

import TeamSeven.util.config.ConfigManager;
import TeamSeven.util.config.ConfigManagerImpl;

import java.sql.*;

/**
 * Created by joshoy on 16/4/17.
 */
public abstract class DbAccessor {

    protected static ConfigManager cm = new ConfigManagerImpl("demoServerConfig.conf");

    // JDBC driver name and database URL
    protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://" + cm.getString("database.host") + ":" + cm.getString("database.port") + "/" + cm.getString("database.dbName");
    //  Database credentials
    protected static final String USERNAME = cm.getString("database.username");
    protected static final String PASSWORD = cm.getString("database.password");
    protected static Connection dbConn;
    protected static Statement stmt;

    public abstract ResultSet executeSQLQueryStatement(String sql) throws SQLException;
    public abstract void executeSQLUpdateStatement(String sql) throws SQLException;
    public abstract void executeSQLStatement(String sql) throws SQLException;

    public abstract void endStatement();

    public abstract void endConnection();
}
