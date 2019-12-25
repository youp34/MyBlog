package com.blog.myblog.dao;

import com.blog.myblog.pojo.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LinkMappper {
    @Select("select * from blog_friendship_link")
    List<Link> queryAll();
    @Delete("delete from blog_friendship_link where id=#{id}")
    void deleteLink(@Param("id")int id);
    @Insert("insert into blog_friendship_link(name,url) values (#{name},#{url})")
    void setLink(@Param("name")String name,@Param("url")String url);
}
