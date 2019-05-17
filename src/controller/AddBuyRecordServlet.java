package controller;

import dao.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "AddBuyRecordServlet", urlPatterns = "/AddBuyRecordServlet")
public class AddBuyRecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Writer writer = response.getWriter();

        String userphone = request.getParameter("userphone");
        String commodityid = request.getParameter("commodityid");
        String acceptaddress = request.getParameter("acceptaddress");
        String buynumber = request.getParameter("buynumber");
        String totalmoney = request.getParameter("totalmoney");
        String contackphone = request.getParameter("contackphone");

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("insert into buyrecord(userphone, commodityid, buynumber, totalmoney, acceptaddress, contackphone) values ('%s', %s, %s, %s, '%s','%s');",
                    userphone, commodityid, buynumber, totalmoney, acceptaddress, contackphone);

            int n = statement.executeUpdate(sql);
            if (n == 1){
                //更新成功
                writer.write("1");
            }else{
                //更新失败
                writer.write("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
