package com.blog.myblog.dao;

import com.blog.myblog.pojo.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select * from blog_notice")
    List<Notice> queryAll();
    @Delete("delete from blog_notice where id=#{id}")
    void deleteNotice(@Param("id")int id);
    @Insert("insert into blog_notice(notice) values (#{notice})")
    void setNotice(@Param("notice")String notice);
}
