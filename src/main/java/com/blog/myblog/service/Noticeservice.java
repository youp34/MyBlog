package com.blog.myblog.service;

import com.blog.myblog.pojo.Notice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Noticeservice {
    List<Notice> queryAll();
    void deleteNotice(int id);
    void setNotice(String notice);
}
