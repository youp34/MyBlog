package com.blog.myblog.service;

import com.blog.myblog.pojo.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface Tagservice {
    List<Tag>queryAll();
    Tag findTag(String tag);
    void updataNumber(int number,String tag);
    void addTag(String tag,int number);
    Integer countTag();
    void deleteTag(int id);
    Tag findTagbyId(int id);
}
