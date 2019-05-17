package controller;

import dao.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "AddToShoppingCarServlet", urlPatterns = "/AddToShoppingCarServlet")
public class AddToShoppingCarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Writer writer = response.getWriter();
        String userphone = request.getParameter("userphone");
        String id_commodity = request.getParameter("id_commodity");
        int flag = 0; //标志是否已经添加过购物车
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql1 = String.format("select id_commodity from shoppingcar where userphone = '%s';", userphone);
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                String temp = resultSet.getString("id_commodity");
                if (temp.compareTo(id_commodity) == 0){
                    writer.write("-1"); //已经添加过了
                    flag = 1;
                    break;
                }
            }
            if (flag == 0){
                String sql2 = String.format("insert into shoppingcar (userphone, id_commodity) VALUES ('%s', %s);", userphone, id_commodity);
                int n = statement.executeUpdate(sql2);
                if (n == 1){
                    //添加购物车成功
                    writer.write("1");
                }else {
                    writer.write("0");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
