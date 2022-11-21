package com.jinpinghu.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DownloadImage {

	public DownloadImage() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://121.42.25.6:3306/jinpinghu?serverTimezone=GMT%2B8", "root", "Hzlq82228388");
		
		PreparedStatement statement = connection.prepareStatement("SELECT file_url FROM `tb_file` where file_url like '%220.189.248.54%' ");
		ResultSet resultSet = statement.executeQuery();
		
		try {
			int i = 1;
			while(resultSet.next()) {
				System.out.println(i++);
				String fileUrl = resultSet.getString("file_url");
				int byteread = 0;

		        URL url = new URL(fileUrl);
		        try {
		            URLConnection conn = url.openConnection();
		            InputStream inStream = conn.getInputStream();
		            FileOutputStream fs = new FileOutputStream("d:/pic/"+fileUrl.substring(fileUrl.lastIndexOf("/")+1));

		            byte[] buffer = new byte[1024];
		            while ((byteread = inStream.read(buffer)) != -1) {
		                fs.write(buffer, 0, byteread);
		            }
		            fs.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			resultSet.close();
			statement.close();
			connection.close();
		}
		
		
		

	}

}
