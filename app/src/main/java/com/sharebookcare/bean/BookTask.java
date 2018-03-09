package com.sharebookcare.bean;

import cn.bmob.v3.BmobObject;


public class BookTask extends BmobObject {

    private String name;
    private String publish;
    private String author;
    private Book book;
    private MyUser sender; //发布任务的人
    private MyUser receiver; //接收任务的人
    private Integer exchange;//任务奖励
    private Integer received; //任务状态，0，未接任务，1，已有人接的任务，2 已完成任务

    @Override
    public String toString() {
        return "BookTask{" +
                "name='" + name + '\'' +
                ", publish='" + publish + '\'' +
                ", author='" + author + '\'' +
                ", book=" + book +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", exchange=" + exchange +
                ", received=" + received +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public MyUser getSender() {
        return sender;
    }

    public void setSender(MyUser sender) {
        this.sender = sender;
    }

    public MyUser getReceiver() {
        return receiver;
    }

    public void setReceiver(MyUser receiver) {
        this.receiver = receiver;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }
}
