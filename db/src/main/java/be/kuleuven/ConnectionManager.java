package be.kuleuven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private Connection connection;
    public void createDb() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
        var s = connection.createStatement();
        s.executeUpdate("CREATE TABLE mijntabel(nr INT); INSERT INTO mijntabel(nr) VALUES(1);");
        s.close();
    }
    public void verifyDbContents() throws SQLException {
        var s = connection.createStatement();
        var result = s.executeQuery("SELECT COUNT(*) FROM mijntabel;");
        var count = result.getInt(0);
        s.close();

        assert count == 1;
    }
}
