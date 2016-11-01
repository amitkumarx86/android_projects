package com.example.amitkumarx86.videobookmark;

public class VideoData {

    private int id;
    private String timeStamp;
    private String topicName;
    private String videoName;

    public VideoData(){

    }
    public VideoData(String videoName, String topicName, String timeStamp) {
        this.videoName = videoName;
        this.timeStamp = timeStamp;
        this.topicName = topicName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getId() {
        return id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getVideoName() {
        return videoName;
    }
}
