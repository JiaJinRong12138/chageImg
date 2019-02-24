package interface_;

public interface DownloadFinishListener {
//    监听下载完成接口
    void finish();
    void error(Exception e);
}
