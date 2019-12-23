package com.blog.myblog.controller;

import com.blog.myblog.component.FastDFSClientWrapper;
import com.blog.myblog.pojo.AdminUser;
import com.blog.myblog.pojo.Article;
import com.blog.myblog.pojo.Tag;
import com.blog.myblog.service.ArticleserviceImpl;
import com.blog.myblog.service.LoginserviceImpl;
import com.blog.myblog.service.Tagservice;
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
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    LoginserviceImpl loginservice;
    @Autowired
    Tagservice tagservice;
    @Autowired
    FastDFSClientWrapper fastDFSClientWrapper;
    @Autowired
    ArticleserviceImpl articleservice;
    @Value("${fdfs.ip}")
    private String ip;
    /**
     * 登录
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get(){
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login_get(){ return "login"; }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login_post(@RequestParam("username")String username, @RequestParam("password")String password, HttpSession session, Model model){
        AdminUser login = loginservice.findloginuser(username);
        if(login == null || "".equals(login)){
            model.addAttribute("error","系统无此用户！");
            return "login";
        }else if(password.equals(login.getPassword()) && "1".equals(login.getPermission())){
            session.setAttribute("user",username);
            return "redirect:/index";
        } else if (password.equals(login.getPassword()) && "0".equals(login.getPermission())){
            session.setAttribute("user",username);
            return "redirect:/home";
        }else {
            model.addAttribute("error","密码输入错误！");
            return "login";
        }
    }
    /**
     * 注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register_get(){
        return "register";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("email")String email,Model model){
        if ("".equals(username) || "".equals(password) || "".equals(email)){
            model.addAttribute("status","输入不能为空！");
            return "register";
        }else {
            AdminUser user = loginservice.findloginuser(username);
            if ("".equals(user) || user == null){
                loginservice.registerUser(username,password,"未知","未知","未知",email,"未知","0");
                model.addAttribute("status","注册成功！点击左上角返回登录页面进行登录！");
                return "register";
            }else {
                model.addAttribute("status","该用户名已经存在！");
                return "register";
            }
        }
    }
    /**
     * 注销
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout_get(HttpSession session){
        session.removeAttribute("user");
        return "login";
    }
    /**
     * 标签管理
     */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String category_get(HttpSession session,Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            List<Tag> list = tagservice.queryAll();
            model.addAttribute("list",list);
            model.addAttribute("identity",username);
            return "ad-category";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 添加标签
     */
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String category_post(@RequestParam("tag")String tag, HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if (tag.equals("")){
            return "redirect:/category";
        } else {
            if("1".equals(login.getPermission())){
                tagservice.addTag(tag,0);
                return "redirect:/category";
            }else{
                model.addAttribute("error","没有权限访问此页面！");
                return "error";
            }
        }
    }
    /**
     * 删除标签
     */
    @RequestMapping(value = "/delete_category/{id}", method = RequestMethod.GET)
    public String delete_category(HttpSession session,Model model,@PathVariable int id){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if("1".equals(login.getPermission())){
            Tag tag = tagservice.findTagbyId(id);
            if(tag.getNumber() > 0){
                model.addAttribute("warning","该标签下存在文章，无法进行此操作！");
                model.addAttribute("url","/category");
                return "ad-wrong";
            }else {
                tagservice.deleteTag(id);
                return "redirect:/category";
            }
        }else{
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 评论管理
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public String comment_get(HttpSession session,Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            return "ad-comment";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 文章管理
     */
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String article_get(HttpSession session,Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            List<Article> list = articleservice.queryAll();
            model.addAttribute("list",list);
            model.addAttribute("identity",username);
            return "ad-article";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 编写文章
     */
    @RequestMapping(value = "/editearticle", method = RequestMethod.GET)
    public String editearticle_get(HttpSession session,Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            List<Tag> tag = tagservice.queryAll();
            model.addAttribute("tag",tag);
            model.addAttribute("identity",username);
            return "ad-editearticle";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 提交文章
     */
    @RequestMapping(value = "/editearticle", method = RequestMethod.POST)
    public String editearticle_post(Model model, HttpSession session,@RequestParam("describe")String describe, @RequestParam("category")String category, @RequestParam("title")String title, @RequestParam("test-editormd-markdown-doc")String content_md, @RequestParam("content")String content_html) throws IOException {
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            String  url_html = fastDFSClientWrapper.uploadFile(content_html.getBytes(StandardCharsets.UTF_8),"txt");
            String  url_md = fastDFSClientWrapper.uploadFile(content_md.getBytes(StandardCharsets.UTF_8),"txt");
            GetTime data = new GetTime();
            articleservice.addarticle(title,"1",username,0,category,data.getData(),"1",url_md,url_html,describe);
            Tag a = tagservice.findTag(category);
            int num = a.getNumber() + 1;
            tagservice.updataNumber(num,category);
            return "redirect:/editearticle";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 删除文章
     */
    @RequestMapping(value = "/delete_article/{id}", method = RequestMethod.GET)
    public String delete_article(HttpSession session,@PathVariable int id, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("".equals(username) && "1".equals(login.getPermission())){
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
        Article list = articleservice.searchAticle(id);
        String url1 = ip + "/" + list.getContent_html();
        String url2 = ip + "/" + list.getContent_md();
        fastDFSClientWrapper.deleteFile(url1);
        fastDFSClientWrapper.deleteFile(url2);
        articleservice.deleteArticle(id);
        Article b = articleservice.searchAticle(id);
        Tag a = tagservice.findTag(b.getTag());
        int num = a.getNumber() - 1;
        tagservice.updataNumber(num,b.getTag());
        return "redirect:/articles";
    }
    /**
     * 后台首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index_get(Model model, HttpSession session){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            model.addAttribute("count_article",articleservice.countArticle());
            model.addAttribute("count_tag",tagservice.countTag());
            model.addAttribute("count_user",loginservice.countUser());
            model.addAttribute("identity",username);
            return "ad-index";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 上传照片
     */
    @RequestMapping(value = "/uploadimages")
    public void upload_images(HttpSession session){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            //上传图片

        } else {
            System.out.println("没有得到权限上传照片");
        }
    }
    /**
     * 上传文件
     */
    @RequestMapping(value = "/uploadfile")
    public void upload_file(HttpSession session){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            //上传文件

        } else {
            System.out.println("没有得到权限上传文件");
        }
    }
    /**
     * 用户列表
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user_get(HttpSession session,Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            List<AdminUser> list = loginservice.queryAll();
            model.addAttribute("list",list);
            model.addAttribute("identity",username);
            return "ad-user";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete_user/{id}", method = RequestMethod.GET)
    public String user_delete(HttpSession session, @PathVariable int id, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            // 删除用户
            loginservice.deleteUser(id);
            return "redirect:/user";
        } else {
            model.addAttribute("error","没有权限删除用户");
            return "error";
        }
    }
    /**
     * 更改用户权限
     */
    @RequestMapping(value = "/set_power/{id}", method = RequestMethod.GET)
    public String set_power(HttpSession session, @PathVariable int id, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            // 更改权限
            AdminUser user = loginservice.finduserbyId(id);
            if ("1".equals(user.getPermission())){
                loginservice.setPower("0",id);
            }else {
                loginservice.setPower("1",id);
            }
            return "redirect:/user";
        } else {
            model.addAttribute("error","没有权限修改用户权限！");
            return "error";
        }
    }
    /**
     * 时光轴
     */
    @RequestMapping(value = "/line", method = RequestMethod.GET)
    public String line_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            model.addAttribute("identity",username);
            return "ad-timeline";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 用户管理
     */
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            model.addAttribute("identity",username);
            return "ad-setting";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
    /**
     * 个人信息
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String information_get(HttpSession session, Model model){
        String username = (String) session.getAttribute("user");
        AdminUser login = loginservice.findloginuser(username);
        if ("1".equals(login.getPermission())){
            model.addAttribute("identity",username);
            return "ad-information";
        } else {
            model.addAttribute("error","没有权限访问此页面！");
            return "error";
        }
    }
}