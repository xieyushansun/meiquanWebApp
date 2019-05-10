package controller;

import dao.DBDAO;
import util.Urls;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "ChangeHeadImageServlet", urlPatterns = "/ChangeHeadImageServlet")
@MultipartConfig()
public class ChangeHeadImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String phone = request.getParameter("phone");
        String headimage_url;
        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("select headimage_url from user where phone = '%s';", phone);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                headimage_url = resultSet.getString("headimage_url");
                File file = new File(Urls.imageStorePath + headimage_url);
                if (file.exists()){
                    file.delete();
                }
            }

            resultSet.close();

            Part part = request.getPart("HeadImage");
            String headImageName = part.getSubmittedFileName(); //获取用户上传图片的图片名


            String directoryPath = Urls.imageStorePath + "/MeiQuanUserImage/" + phone;
            File file = new File(directoryPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String filePath = directoryPath + "/" + headImageName;
            part.write(filePath);  //将文件写入指定文件中

            String fileRelativePath = "/MeiQuanUserImage/" + phone + "/" + headImageName;
            //将新图片路径存在数据库中
            sql = String.format("update user set headimage_url = '%s' where phone = '%s';", fileRelativePath, phone);
            int n = statement.executeUpdate(sql);
            if (n == 1){
                writer.write("1");  //处理成功
            }else {
                writer.write("0");  //失败
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
