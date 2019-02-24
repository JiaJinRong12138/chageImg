package interface_;

public interface RequestFinishListener {
//    监听请求完成接口
    void finish(String response);
    void error(Exception e);
}

