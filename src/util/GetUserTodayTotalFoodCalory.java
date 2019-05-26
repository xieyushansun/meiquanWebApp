package util;

import dao.DBDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class  GetUserTodayTotalFoodCalory {
    public double getFoodCalory(String phone){
        double total_foodcalory = 0;
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from foodrecord_foodcalory where phone = '"+phone+"' and DATE_FORMAT(date,'%Y-%m-%d') = CURDATE();";
            System.out.println("sdf");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int intake = 0;
                int calory = 0;
                intake = resultSet.getInt("intake");
                calory = resultSet.getInt("calory");
                total_foodcalory += calory*intake/100;
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total_foodcalory;
    }
}
