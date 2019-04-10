package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Search {
    Connection connection = DBDAO.getConnection();
    Statement statement;

    {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
