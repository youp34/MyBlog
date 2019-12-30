package com.blog.myblog.dao;

import com.blog.myblog.pojo.Comment;
import com.blog.myblog.pojo.Timeline;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from blog_comments")
    List<Comment> queryAll();
    @Select("select * from blog_comments where article_id=#{article_id}")
    List<Comment> findComment(@Param("article_id")int article_id);
    @Delete("delete from blog_comments where id=#{id}")
    void deleteComment(@Param("id")int id);
    @Insert("insert into blog_comments(article_id,comment_user,comment_content,comment_time) values (#{article_id},#{comment_user},#{comment_content},#{comment_time})")
    void setComment(@Param("article_id")int article_id,@Param("comment_user")String comment_user,@Param("comment_content")String comment_content,@Param("comment_time")String comment_time);
}
