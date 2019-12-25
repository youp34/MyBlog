package com.blog.myblog.service;

import com.blog.myblog.pojo.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Linkservice {
    List<Link> queryAll();
    void deleteLink(int id);
    void setLink(String name,String url);
}
