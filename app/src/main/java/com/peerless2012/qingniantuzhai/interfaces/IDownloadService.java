package com.peerless2012.qingniantuzhai.interfaces;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/28 0:03
 * @Version V1.0
 * @Description
 */
public interface IDownloadService {
    public void add(String url,IOnImgDownloadCompleteListener l);
    public void removeListener(String url, IOnImgDownloadCompleteListener l);
    public void save(String url);
}
