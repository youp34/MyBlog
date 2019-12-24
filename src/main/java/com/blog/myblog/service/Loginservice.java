package com.blog.myblog.service;

import com.blog.myblog.pojo.AdminUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Loginservice {
    AdminUser findloginuser(String username);
    List<AdminUser> queryAll();
    Integer countUser();
    void deleteUser(int id);
    AdminUser finduserbyId(int id);
    void setPower(String permission,int id);
    void registerUser(String username,String password,String description,String address,String qq,String email,String github,String permission);
    void setInformation(String address,String qq,String email,String github,String description,String username);
}
