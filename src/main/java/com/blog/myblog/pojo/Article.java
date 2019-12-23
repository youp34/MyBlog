package com.blog.myblog.pojo;

public class Article {
     private int id;
     private String title;
     private String picture;
     private String username;
     private int page_view;
     private String tag;
     private String time;
     private String comment;
     private String content_md;
     private String content_html;
     private String describe_article;

    public String getDescribe() {
        return describe_article;
    }

    public void setDescribe(String describe) {
        this.describe_article = describe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent_md() {
        return content_md;
    }

    public void setContent_md(String content_md) {
        this.content_md = content_md;
    }

    public String getContent_html() {
        return content_html;
    }

    public void setContent_html(String content_html) {
        this.content_html = content_html;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage_view() {
        return page_view;
    }

    public void setPage_view(int page_view) {
        this.page_view = page_view;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
