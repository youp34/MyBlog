package com.blog.myblog.controller;


import com.blog.myblog.component.FastDFSClientWrapper;
import com.blog.myblog.pojo.*;
import com.blog.myblog.service.*;
import com.blog.myblog.tool.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    LoginserviceImpl loginservice;
    @Autowired
    Tagservice tagservice;
    @Autowired
    FastDFSClientWrapper fastDFSClientWrapper;
    @Autowired
    ArticleserviceImpl articleservice;
    @Autowired
    private Linkservice linkservice;
    @Autowired
    private Timelineservice timelineservice;
    @Autowired
    private Noticeservice noticeservice;
    @Autowired
    private Commentservice commentservice;
    @Value("${blog.user}")
    private String own;
    @Value("${fdfs.ip}")
    private String ip;
    /**
     * 博客首页
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String login_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        model.addAttribute("address",list1.getAddress());
        List<Tag> tag = tagservice.queryAll();
        model.addAttribute("tag",tag);
        List<Article> list = articleservice.queryAll();
        model.addAttribute("list",list);
        List<Notice> notices = noticeservice.queryAll();
        model.addAttribute("notices",notices);
        return "home";
    }
    /**
     * 根据标签显示文章
     */
    @RequestMapping(value = "/category/{tag}", method = RequestMethod.GET)
    public String tag(@PathVariable String tag, HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        List<Tag> tag1 = tagservice.queryAll();
        model.addAttribute("tag",tag1);
        List<Article> list = articleservice.sortByTag(tag);
        if ("".equals(list) || list == null){
            String a ="<div class=\"shadow\" style=\"text-align:center;font-size:16px;padding:40px 15px;background:#fff;margin-bottom:15px;\">\n" + "未搜索到与【<span style=\"color: #FF5722;\">keywords</span>】有关的文章，随便看看吧！\n" + "</div>";
            model.addAttribute("no1",a);
            return "article";
        }
        model.addAttribute("list",list);
        return "article";
    }
    /**
     * 博客文章专栏
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public String article_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        List<Tag> tag = tagservice.queryAll();
        model.addAttribute("tag",tag);
        List<Article> list = articleservice.queryAll();
        model.addAttribute("list",list);
        return "article";
    }
    /**
     * 博客文章模糊搜索
     */
    @RequestMapping(value = "/fuzzySearch", method = RequestMethod.POST)
    public String articleSeaech(HttpSession session, Model model,@RequestParam("keywords")String keywords){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        List<Tag> tag = tagservice.queryAll();
        model.addAttribute("tag",tag);
        // 模糊搜索
        if ("".equals(keywords)){
            model.addAttribute("error","您的操作有误");
            return "error";
        }
        List<Article> list = articleservice.fuzzySearch(keywords);
        model.addAttribute("list",list);
        return "article";
    }
    /**
     * 博客文章内容
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable int id,HttpSession session, Model model) throws IOException {
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        // 用户信息
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        // 遍历标签
        List<Tag> tag = tagservice.queryAll();
        model.addAttribute("tag",tag);
        // 显示文章内容
        Article list = articleservice.searchAticle(id);
        model.addAttribute("list",list);
        String url = ip + "/" + list.getContent_html();
        String content_html = new String(fastDFSClientWrapper.downFile(url), "utf-8");
        model.addAttribute("content_html",content_html);
        // 显示评论内容
        List<Comment> comments = commentservice.findComment(id);
        model.addAttribute("comments",comments);
        return "detail";
    }
    /**
     * 博客文章点赞
     */
    @RequestMapping(value = "/praise/{id}", method = RequestMethod.GET)
    public String praise(@PathVariable int id,HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        Article list = articleservice.searchAticle(id);
        int number = list.getPage_view() + 1;
        articleservice.updataPraise(number,id);
        return "redirect:/detail/" + id;
    }
    /**
     * 博客文章评论
     */
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
    public String comments(@PathVariable int id,HttpSession session, Model model,@RequestParam("content")String editorContent){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        if (!"".equals(editorContent)){
            GetTime time =new GetTime();
            commentservice.setComment(id,username,editorContent,time.getTime());
            return "redirect:/detail/" + id;
        }else {
            model.addAttribute("error","您的操作有误");
            return "error";
        }

    }
    /**
     * 关于本站
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        AdminUser list = loginservice.findloginuser(own);
        model.addAttribute("list",list);
        List<Link> links = linkservice.queryAll();
        model.addAttribute("links",links);
        return "about";
    }
    /**
     * 博客资源分享
     */
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public String resource_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        return "resource";
    }
    /**
     * 时间轴
     */
    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    public String timeline_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        if ("".equals(username)){
            model.addAttribute("error","您的用户Session已过期请重新登录");
            return "error";
        }
        AdminUser list1 = loginservice.findloginuser(own);
        model.addAttribute("own",list1.getUsername());
        List<Timeline> timelines = timelineservice.queryAll();
        model.addAttribute("timelines",timelines);
        return "timeline";
    }
}
