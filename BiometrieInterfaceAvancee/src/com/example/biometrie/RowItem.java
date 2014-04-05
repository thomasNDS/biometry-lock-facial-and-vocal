package com.example.biometrie;

public class RowItem {
	private int imageId;
    private String title;
    private String desc;
    private int image2;
 
    public RowItem(int imageId, String title, String desc) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }
    
    public RowItem(int imageId, String title, String desc,int image2) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.image2=image2;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
