package com.example.amitkumarx86.videobookmark;


import java.io.File;
import java.util.ArrayList;

public class GetVideos {

    private ArrayList<String> fileList = new ArrayList<>();
    public ArrayList<String> GetVideosMethod(File root_sd){
        File listFile[] = root_sd.listFiles();
        if (listFile != null && listFile.length > 0){
            for (int i = 0; i < listFile.length; i++){
                //fileList.add(listFile[i].getName());
                if (listFile[i].isDirectory()){
                    GetVideosMethod(listFile[i]);
                }
                else{
                    if(listFile[i].getName().endsWith(".mp4")){
                            fileList.add(listFile[i].getPath());
                        }
                    }
                }
            }
            return fileList;
        }
    }



