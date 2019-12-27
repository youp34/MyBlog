package com.blog.myblog.dao;

import com.blog.myblog.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into blog_article( title,picture,username,page_view,tag,time,comment,content_md,content_html,describe_article ) values ( #{title},#{picture},#{username},#{page_view},#{tag},#{time},#{comment},#{content_md},#{content_html},#{describe_article} )")
    void addarticle(@Param("title")String title, @Param("picture")String picture, @Param("username")String username, @Param("page_view")int page_view, @Param("tag")String tag, @Param("time")String time, @Param("comment")String comment, @Param("content_md")String content_md, @Param("content_html")String content_html, @Param("describe_article")String describe);
    @Select("select * from blog_article")
    List<Article> queryAll();
    @Delete("delete from blog_article where id=#{id}")
    void deleteArticle(@Param("id")int id);
    @Select("select * from blog_article where id=#{id}")
    Article searchAticle(@Param("id")int id);
    @Select("select * from blog_article where tag=#{tag}")
    List<Article> sortByTag(@Param("tag")String tag);
    @Update("update blog_article set page_view=#{page_view} where id=#{id}")
    void updataPraise(@Param("page_view")int page_view,@Param("id")int id);
    @Select("select count(*) from blog_article")
    Integer countArticle();
    @Select("select * from blog_article where title like #{title}")
    List<Article> fuzzySearch(@Param("title")String title);
}
