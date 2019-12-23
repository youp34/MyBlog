package com.blog.myblog.service;

import com.blog.myblog.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Articleservice {
    void addarticle(String title,String picture,String username,int page_view,String tag,String time,String comment,String content_md,String content_html,String describe);
    List<Article> queryAll();
    void deleteArticle(int id);
    Article searchAticle(int id);
    List<Article> sortByTag(String tag);
    void updataPraise(int page_view,int id);
    Integer countArticle();
}
