package com.blog.myblog.dao;

import com.blog.myblog.pojo.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select * from blog_tag")
    List<Tag> queryAll();
    @Select("select * from blog_tag where tag=#{tag}")
    Tag findTag(@Param("tag")String tag);
    @Select("select * from blog_tag where id=#{id}")
    Tag findTagbyId(@Param("id")int id);
    @Update("update blog_tag set number=#{number} where tag=#{tag}")
    void updataNumber(@Param("number")int number,@Param("tag")String tag);
    @Insert("insert into blog_tag(tag,number) values (#{tag},#{number})")
    void addTag(@Param("tag")String tag,@Param("number")int number);
    @Select("select count(*) from blog_tag")
    Integer countTag();
    @Delete("delete from blog_tag where id=#{id}")
    void deleteTag(@Param("id")int id);
}
