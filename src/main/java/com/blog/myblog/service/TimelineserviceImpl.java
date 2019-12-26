package com.blog.myblog.service;

import com.blog.myblog.dao.TimelineMapper;
import com.blog.myblog.pojo.Timeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineserviceImpl implements Timelineservice {
    @Autowired
    private TimelineMapper timelineMapper;
    @Override
    public void deleteTimeline(int id) {
        timelineMapper.deleteTimeline(id);
    }

    @Override
    public List<Timeline> queryAll() {
        return timelineMapper.queryAll();
    }

    @Override
    public void setTimeline(String time, String content) {
        timelineMapper.setTimeline(time,content);
    }
}
