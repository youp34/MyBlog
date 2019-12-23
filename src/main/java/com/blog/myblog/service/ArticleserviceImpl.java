package com.blog.myblog.service;

import com.blog.myblog.dao.ArticleMapper;
import com.blog.myblog.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleserviceImpl implements Articleservice {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void addarticle(String title, String picture, String username, int page_view, String tag, String time, String comment, String content_md, String content_html,String describe) {
        articleMapper.addarticle(title,picture,username,page_view,tag,time,comment,content_md,content_html,describe);
    }

    @Override
    public List<Article> queryAll() {
        return articleMapper.queryAll();
    }

    @Override
    public void deleteArticle(int id) {
        articleMapper.deleteArticle(id);
    }

    @Override
    public void updataPraise(int page_view, int id) {
        articleMapper.updataPraise(page_view,id);
    }

    @Override
    public Article searchAticle(int id) {
        return articleMapper.searchAticle(id);
    }

    @Override
    public List<Article> sortByTag(String tag) {
        return articleMapper.sortByTag(tag);
    }

    @Override
    public Integer countArticle() {
        return articleMapper.countArticle();
    }
}
