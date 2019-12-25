package com.blog.myblog.service;

import com.blog.myblog.dao.LinkMappper;
import com.blog.myblog.pojo.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkserviceImpl implements Linkservice {

    @Autowired
    private LinkMappper linkMappper;

    @Override
    public void deleteLink(int id) {
        linkMappper.deleteLink(id);
    }

    @Override
    public List<Link> queryAll() {
        return linkMappper.queryAll();
    }

    @Override
    public void setLink(String name, String url) {
        linkMappper.setLink(name,url);
    }
}
