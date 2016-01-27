package com.peerless2012.qingniantuzhai.interfaces;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/27 23:26
 * @Version V1.0
 * @Description
 */
public interface IOnImgDownloadCompleteListener {
    /**
     * 下载完成回调
     * @param downloadUrl null if fail
     */
    public void onImgDownloadComplete(String downloadUrl,String path);
}
