package com.blog.myblog.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //创建session
        HttpSession session=request.getSession();
        //无需登录可以访问的地址
        String [] allowUrls=new String[]{"/","/login","/register"};
        //获取当前请求地址
        Object user = session.getAttribute("user");
        if (user == null) {
            // 获取request返回页面到登录页
            request.getRequestDispatcher("/login").forward(request, response);
            // 如果获取的request的session中的loginUser参数为空（未登录），就返回登录页，否则放行访问
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}