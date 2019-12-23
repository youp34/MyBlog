package com.blog.myblog.service;

import com.blog.myblog.dao.TagMapper;
import com.blog.myblog.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagserviceImpl implements Tagservice {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> queryAll() {
        return tagMapper.queryAll();
    }

    @Override
    public void updataNumber(int number, String tag) {
        tagMapper.updataNumber(number,tag);
    }

    @Override
    public Tag findTag(String tag) {
        return tagMapper.findTag(tag);
    }

    @Override
    public void addTag(String tag, int number) {
        tagMapper.addTag(tag,number);
    }

    @Override
    public Integer countTag() {
        return tagMapper.countTag();
    }

    @Override
    public void deleteTag(int id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public Tag findTagbyId(int id) {
        return tagMapper.findTagbyId(id);
    }
}
