package com.peerless2012.qingniantuzhai.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void saveJson(String path,String json,String fileName){
        int i = fileName.lastIndexOf("/");
        File file = new File(path,fileName.substring(i + 1));
        if (!file.exists()) file.delete();
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json);
            bufferedWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readJson(String path,String fileName){
        int i = fileName.lastIndexOf("/");
        File file = new File(path,fileName.substring(i + 1));
        if (!file.exists()) return null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        CharArrayWriter charArrayWriter = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            charArrayWriter = new CharArrayWriter();
            char[] buffer = new char[512];
            int len = 0;
            while ((len = bufferedReader.read(buffer)) != -1){
                charArrayWriter.write(buffer,0,len);
            }
            return  charArrayWriter.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            if (charArrayWriter != null) {
                charArrayWriter.close();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static File createDir(String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }
}
