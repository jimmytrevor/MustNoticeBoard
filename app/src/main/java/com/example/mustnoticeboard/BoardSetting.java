package com.example.mustnoticeboard;

public class BoardSetting {

   private String title;
   private String desc;
   private String image;


    public BoardSetting(){

    }


    public BoardSetting(String title,String desc,String image){
        this.desc=desc;
        this.title=title;
        this.image=image;

    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
