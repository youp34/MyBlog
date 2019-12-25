package com.blog.myblog.service;

import com.blog.myblog.dao.NoticeMapper;
import com.blog.myblog.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeserviceImpl implements Noticeservice{

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void deleteNotice(int id) {
        noticeMapper.deleteNotice(id);
    }

    @Override
    public List<Notice> queryAll() {
        return noticeMapper.queryAll();
    }

    @Override
    public void setNotice(String notice) {
        noticeMapper.setNotice(notice);
    }
}
