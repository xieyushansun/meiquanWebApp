package controller;

import com.google.gson.JsonObject;
import dao.DBDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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

@WebServlet(name = "GetCommodityServlet", urlPatterns = "/GetCommodityServlet")
@MultipartConfig()
public class GetCommodityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String commodity_type = request.getParameter("commodity_type");
        List<JsonObject> commodity_jsonObjectList = new ArrayList<>();

        Writer writer = response.getWriter();

        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "";
            if (commodity_type.compareTo("allcommodity") == 0){
                sql = "select * from commodity;";
            }else {
                sql = String.format("select * from commodity where commodity_type = '%s';", commodity_type);
            }
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String commodity_name = resultSet.getString("commodity_name");
                String commodity_price = resultSet.getString("price");
                String commodity_imageurl = resultSet.getString("commodity_imageurl");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("commodity_name", commodity_name);
                jsonObject.addProperty("commodity_price", commodity_price);
                jsonObject.addProperty("commodity_imageurl", commodity_imageurl);
                commodity_jsonObjectList.add(jsonObject);
            }
            writer.write(commodity_jsonObjectList.toString());
            writer.close();
            statement.close();
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
