package com.hoomsun.app.api.help;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;


public class DownloadHelper {

	public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        String fileName = "Operator.doc".toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0){
            	response.getOutputStream().write(b, 0, len);
            }
                
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void downloadNet(String path,String savepath) throws IOException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(path);  //网络路径 

        URLConnection conn = url.openConnection();
        InputStream inStream = conn.getInputStream();
        FileOutputStream fs = new FileOutputStream(savepath);  //保存路径

        byte[] buffer = new byte[1204];
        while ((byteread = inStream.read(buffer)) != -1) {
             bytesum += byteread;
             fs.write(buffer, 0, byteread);
        }
        fs.close();
    }
    
    
    /** 
     * 判断网络图片是否存在 
     * posturl 图片地址链接 
     */  
    public static boolean isImagesTrue(String posturl) throws IOException { 
    	posturl=posturl.replaceAll("\\\\", "/");   //将\ 替换成/,特殊要用\\\\ 表示/
        URL url = new URL(posturl);  
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();  
        urlcon.setRequestMethod("POST");  
        urlcon.setRequestProperty("Content-type","application/x-www-form-urlencoded");  
        if (urlcon.getResponseCode() == HttpURLConnection.HTTP_OK) {  
            System.out.println(HttpURLConnection.HTTP_OK + posturl + "200:posted ok!");  
            return true;  
        } else {  
            System.out.println(urlcon.getResponseCode() + posturl  + "404:Bad post...");  
            return false;  
        }  
    }  
    
    
    public static void main(String[] args) throws IOException {
		System.out.println(isImagesTrue("http://192.168.3.14:8081/static/product/15135657727961.png"));
		
	}
}
