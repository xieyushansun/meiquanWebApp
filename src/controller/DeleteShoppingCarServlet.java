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
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "DeleteShoppingCarServlet", urlPatterns = "/DeleteShoppingCarServlet")
public class DeleteShoppingCarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String id_commodity = request.getParameter("id_commodity");
        String phone = request.getParameter("phone");

        Writer writer = response.getWriter();

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("delete from shoppingcar where userphone = '%s' and id_commodity = '%s';", phone, id_commodity);
            int n = statement.executeUpdate(sql);
            if (n == 1){
                //删除成功
                writer.write("1");
            }else{
                //删除失败
                writer.write("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
