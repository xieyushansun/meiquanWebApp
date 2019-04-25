package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库操作
 */
public class DBDAO {
    // 驱动类名
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    // 数据库名
    static String dataBase = "meiquan";
    // 账户
    static String user = "root";
    // 密码
    static String password = "mysql";


    /**
     * 获取一个数据库连接对象
     * @return  Connection 对象
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            String url = String.format("jdbc:mysql://localhost:3306/%s?useSSL=false", dataBase);
            // 加载驱动
            Class.forName(JDBC_DRIVER);
            // 建立连接
            con = DriverManager.getConnection(url, user, password); // 创建连接对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

}
