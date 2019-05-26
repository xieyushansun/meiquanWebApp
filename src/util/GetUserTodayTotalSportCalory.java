package util;

import dao.DBDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetUserTodayTotalSportCalory {
    public double getSportCalory(String phone){
        double total_sportcalory = 0;
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from sportrecord_sportcalory where phone = '"+phone+"' and DATE_FORMAT(date,'%Y-%m-%d') = CURDATE();";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                double duration = 0;
                int calory = 0;
                duration = resultSet.getInt("duration");
                calory = resultSet.getInt("calory");
                total_sportcalory += calory*duration;
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total_sportcalory;
    }
}
