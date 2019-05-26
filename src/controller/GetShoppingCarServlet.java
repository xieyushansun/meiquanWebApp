package controller;

import com.google.gson.JsonObject;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetShoppingCarServlet", urlPatterns = "/GetShoppingCarServlet")
public class GetShoppingCarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String userphone = request.getParameter("userphone");
        List<JsonObject> shoppingcar_jsonObjectList = new ArrayList<>();
        Connection connection = DBDAO.getConnection();
        Writer writer = response.getWriter();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select distinct * from shoppingcar_commodity where userphone = '%s';", userphone);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String id_commodity = resultSet.getString("id_commodity");
                String commodity_name = resultSet.getString("commodity_name");
                String price = resultSet.getString("price");
                String commodity_imageurl = resultSet.getString("commodity_imageurl");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id_commodity", id_commodity);
                jsonObject.addProperty("commodity_name", commodity_name);
                jsonObject.addProperty("price", price);
                jsonObject.addProperty("commodity_imageurl", commodity_imageurl);
                shoppingcar_jsonObjectList.add(jsonObject);
            }
            writer.write(shoppingcar_jsonObjectList.toString());
            writer.close();
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
