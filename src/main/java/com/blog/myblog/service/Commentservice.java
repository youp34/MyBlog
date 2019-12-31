package com.blog.myblog.service;

import com.blog.myblog.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Commentservice {
    List<Comment> queryAll();
    List<Comment> findComment(int article_id);
    void deleteComment(int id);
    void setComment(int article_id,String comment_user,String comment_content,String comment_time);
    Comment findComment_id(int id);
}
