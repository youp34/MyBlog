package com.blog.myblog.service;

import com.blog.myblog.dao.CommentMapper;
import com.blog.myblog.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentserviceImpl implements Commentservice {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> queryAll() {
        return commentMapper.queryAll();
    }

    @Override
    public List<Comment> findComment(int article_id) {
        return commentMapper.findComment(article_id);
    }

    @Override
    public void deleteComment(int id) {
        commentMapper.deleteComment(id);
    }

    @Override
    public void setComment(int article_id, String comment_user, String comment_content,String comment_time) {
        commentMapper.setComment(article_id,comment_user,comment_content,comment_time);
    }

    @Override
    public Comment findComment_id(int id) {
        return commentMapper.findComment_id(id);
    }
}
