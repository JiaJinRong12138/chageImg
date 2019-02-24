package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class CommUtils {
    public static int getMax(int[] array){
        Arrays.sort(array);
        return array[array.length - 1];
    }

    /**
    *
    * 日志记录
    *
    * @param state 日志分类状态
    * @param message 日志详情
    * */
    public static void saveLog(int state, String message){


        FileOutputStream fileOutputStream = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String name = sdf.format(date);
        File saveDir = null;
        if(state == 1){
            saveDir = new File("E:\\wallpapers\\logs");
        }else if(state == 0){
            saveDir = new File("E:\\wallpapers\\error");
        }
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+"\\"+name+".txt");
        try {
            fileOutputStream = new FileOutputStream(file);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(date);
            String log = "时间：" + time + "，Msg:" + message;
            fileOutputStream.write(log.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
