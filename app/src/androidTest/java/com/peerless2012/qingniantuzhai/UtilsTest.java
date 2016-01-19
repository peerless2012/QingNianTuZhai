package com.peerless2012.qingniantuzhai;

import android.test.AndroidTestCase;
import android.util.Log;

import com.peerless2012.qingniantuzhai.utils.FileUtils;

/**
 * Created by Administrator on 2016/1/19.
 */
public class UtilsTest extends AndroidTestCase{
    private static final String TAG = "UtilsTest";

    public void testWriteFile(){
        String cacheDir = getContext().getExternalCacheDir().getAbsolutePath();
        FileUtils.saveJson(cacheDir,"{\"name\":\"xiaobai\",\"age\":34}","name.json");
    }

    public void testReadFile(){
        String cacheDir = getContext().getExternalCacheDir().getAbsolutePath();
        String json = FileUtils.readJson(cacheDir, "name.json");
        Log.i(TAG,"结果 : " + json);
    }
}
