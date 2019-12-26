package com.blog.myblog.dao;

import com.blog.myblog.pojo.Timeline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TimelineMapper {
    @Select("select * from blog_timeline")
    List<Timeline> queryAll();
    @Delete("delete from blog_timeline where id=#{id}")
    void deleteTimeline(@Param("id")int id);
    @Insert("insert into blog_timeline(time,content) values (#{time},#{content})")
    void setTimeline(@Param("time")String time,@Param("content")String content);
}
