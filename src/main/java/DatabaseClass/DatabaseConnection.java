package DatabaseClass;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DatabaseConnection {
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nfz",
            "root","pkbcilwl");
    private final Statement statement = connection.createStatement();

    public DatabaseConnection() throws SQLException {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE);
    }

    public ResultSet result(String commandSQL) throws SQLException {
        return statement.executeQuery(commandSQL);
    }

    public void addUser(String commandSQL) throws SQLException {
        statement.executeUpdate(commandSQL);
    }
}
