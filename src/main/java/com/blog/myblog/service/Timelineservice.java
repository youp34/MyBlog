package com.blog.myblog.service;

import com.blog.myblog.pojo.Timeline;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Timelineservice {
    List<Timeline> queryAll();
    void deleteTimeline(int id);
    void setTimeline(String time,String content);
}
