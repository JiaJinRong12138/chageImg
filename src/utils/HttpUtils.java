package utils;

import interface_.DownloadFinishListener;
import interface_.RequestFinishListener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

    /**
    * 请求接口，获取图片地址
    * @param address
    * @param listener
    * */

    public static void doGet(String address, RequestFinishListener listener){

//        开启多线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream is = null;
                BufferedReader reader = null;
                StringBuffer sb = new StringBuffer(0);
                try {
//                    请求的URL
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
//                    设置读取超时
                    connection.setReadTimeout(5*1000);
//                    连接超时
                    connection.setConnectTimeout(3*1000);
                    connection.connect();
                    is = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is));
                    String lien = "";
                    while ((lien = reader.readLine()) != null){
                        sb.append(lien);
                    }
                    if(listener != null){
                        listener.finish(sb.toString());
                    }
                    reader.close();
                    is.close();

                } catch (Exception e) {
                    listener.error(e);
                } finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    /**
    * 下载图片
    *
    * @param address 图片网站
    * @param path 保存图片路径
    * @param name 图片名称
    * @throws IOException
    * */

    public static void download(String address, String path, String name, DownloadFinishListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                FileOutputStream out = null;
                InputStream is = null;
                File file = null;

                try {
                    file = new File(path + "\\" + name);
                    if(file.exists()){
                        file.delete();
                    }
                    out = new FileOutputStream(file);
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(5* 1000);
                    connection.setConnectTimeout(3*1000);
                    connection.connect();
                    is = connection.getInputStream();
                    int len = -1;
                    byte[] bs = new byte[1024];
                    while ((len = is.read(bs)) != -1){
                        out.write(bs, 0, len);
                    }
                    listener.finish();
                    out.close();
                    is.close();


                } catch (Exception e) {
                   if(file != null){
                       file.delete();
                   }
                } finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }


            }
        }).start();
    }
}
