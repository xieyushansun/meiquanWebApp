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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "AddUserNewsServlet", urlPatterns = "/AddUserNewsServlet")
@MultipartConfig()
public class AddUserNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String phone = request.getParameter("phone");
        String content = request.getParameter("content");

        String flag_isImage = request.getParameter("flag_isImage");

        String sql = "";

        if (flag_isImage.compareTo("1") == 0){
            //获取年月日时分秒
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateTime = simpleDateFormat.format(new Date());
            //处理图片
            Part part = request.getPart("image");
            String headImageName = part.getSubmittedFileName(); //获取用户上传图片的图片名
            String directoryPath = Urls.imageStorePath + "/MeiQuanUserNews/" + dateTime + phone;
            File file = new File(directoryPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String filePath = directoryPath + "/" + headImageName;
            part.write(filePath);  //将文件写入指定文件中
            String fileRelativePath = "/MeiQuanUserNews/" + dateTime + phone + "/" + headImageName;
            sql = String.format("insert into usernews (phone, content, image_url) VALUES ('%s', '%s', '%s');", phone, content, fileRelativePath);
        }

        Writer writer = response.getWriter();
        Connection connection = DBDAO.getConnection();
        try {
            Statement statement = connection.createStatement();

            if (flag_isImage.compareTo("0") == 0){
                sql = String.format("insert into usernews (phone, content) VALUES ('%s', '%s');", phone, content);
            }

            int n = statement.executeUpdate(sql);
            if (n == 1){
                writer.write("1");
            }else {
                writer.write("0");
            }
            writer.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
