package com.peerless2012.qingniantuzhai.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/27 23:25
 * @Version V1.0
 * @Description
 */
public class DownloadImgsService extends Service{

    private WorkThread workeThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (workeThread != null) {
            workeThread.stopWorkThread();
        }
        super.onDestroy();
    }

    class WorkThread extends Thread{

        private boolean isRunning = true;

        public void stopWorkThread(){
            isRunning = false;
        }

        @Override
        public void run() {

        }
    }

}
