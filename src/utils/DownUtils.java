package utils;

import interface_.DownloadFinishListener;
import interface_.RequestFinishListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DownUtils {
    private final static int FAILED = 0;
    private final static int SUCCESS = 1;


    /**
    *
    * 检查当天下载是否已经执行，防止重复下载
    *
    * @return
    * */
    //存放日志文件
    private static boolean check(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String name = sdf.format(date);
        File file1 = new File("E:\\wallpapers\\logs\\" + name + ".txt");
        File file2 = new File("E:\\wallpapers\\error\\" + name + ".txt");
        if(file1.exists() || file2.exists()){
            return true;
        }
        return false;
    }


    public static void downWallPaper(){
//        判断当天是否已经下载过
        if(!check()){
            String requestAddress = "http://guolin.tech/api/bing_pic";
            HttpUtils.doGet(requestAddress, new RequestFinishListener() {
                @Override
                public void finish(String response) {
                    //请求完成
                    downPic(response);
                }

                @Override
                public void error(Exception e) {
                    //请求失败
                    CommUtils.saveLog(FAILED, "请求图片网址异常"+e.toString());
                }
            });

        }
    }


    //下载图片
    public static void downPic(String picAddr){
        String dir = "E:\\wallpapers";
        File saveFile = new File(dir);
        if(!saveFile.exists()){
            saveFile.mkdir();
        }
        File[] allFiles = saveFile.listFiles();
        List<File> files = new ArrayList<File>();
        for(File file : allFiles){
            if(file.isFile()){
                files.add(file);
            }
        }
        if(files.size() == 0){
//            文件夹不存在文件
            String name = "1.jpg";
            HttpUtils.download(picAddr, dir, name, new DownloadFinishListener() {
                @Override
                public void finish() {
                    //执行完成
                    CommUtils.saveLog(SUCCESS, "下载成功");
                }

                @Override
                public void error(Exception e) {
                    //执行失败
                    CommUtils.saveLog(FAILED, "请求图片网址异常"+e.toString());
                }
            });
        }else{
            int names[] = new int[files.size()];
            for (int i = 0; i<files.size(); i++){
                String fileName = files.get(i).getName();
                names[i] = Integer.parseInt(fileName.substring(0, fileName.lastIndexOf(".")));
            }
            String name = CommUtils.getMax(names) + 1 + ".jpg";
            HttpUtils.download(picAddr, dir, name, new DownloadFinishListener() {
                @Override
                public void finish() {
                    CommUtils.saveLog(SUCCESS, "下载成功");
                }

                @Override
                public void error(Exception e) {
                    CommUtils.saveLog(FAILED, "请求图片网址异常"+e.toString());
                }
            });
        }
    }

}
