package com.ec.proyectodemoandroid.controllers;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AdministracionFiles {

    public static void createDirectorys(String p_dir) {
        try {
            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" + p_dir + "/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean exiteFile(String p_dir, String p_name) {
        try {
            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" + p_dir + "/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            File f = new File(rootPath + p_name);
            if (f.exists()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countFile(String p_dir, String p_name) {
        try {
            int c = 0;
            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + p_dir + "/";
            File f = new File(rootPath);
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!file.isDirectory())
                    if (file.getName().contains(p_name))
                        c++;
            }
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String allNameFileByOrden(String p_dir, String p_name) {
        try {
            String c="";
            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+p_dir+"/";
            File f = new File(rootPath);
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!file.isDirectory())
                    if(file.getName().contains(p_name))
                        c+= file.getName() + "|";
            }
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public String readFileAsString(String p_dir, String p_name) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + p_dir;
        fullPath += "/" + p_name;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(new File(fullPath)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
