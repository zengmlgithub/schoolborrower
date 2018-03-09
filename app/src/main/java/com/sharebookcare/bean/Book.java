package com.sharebookcare.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;


public class Book extends BmobObject {

    private String title;
    private String summary;
    private BmobUser owner;  //书本拥有者
    private Integer out;
    private String author;
    private String img;
    private String publisher;
    private String alt;
    private BmobUser outer; //当前借书人
    private String recommand ;// 1 推荐 0 不推荐

    public String getRecommand() {
        return recommand;
    }

    public void setRecommand(String recommand) {
        this.recommand = recommand;
    }

    public BmobUser getOuter() {
        return outer;
    }

    public void setOuter(BmobUser outer) {
        this.outer = outer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BmobUser getOwner() {
        return owner;
    }

    public void setOwner(BmobUser owner) {
        this.owner = owner;
    }

    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", owner=" + owner +
                ", out=" + out +
                ", author='" + author + '\'' +
                ", img='" + img + '\'' +
                ", publisher='" + publisher + '\'' +
                ", alt='" + alt + '\'' +
                ", outer=" + outer +
                ", recommand=" + recommand +
                '}';
    }
}
