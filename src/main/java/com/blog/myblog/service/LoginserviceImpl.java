package com.blog.myblog.service;

import com.blog.myblog.dao.UserMapper;
import com.blog.myblog.pojo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginserviceImpl implements Loginservice {
    @Autowired
    private UserMapper userMapper;
    @Override
    public AdminUser findloginuser(String username) {
        return userMapper.findAdminuser(username);
    }

    @Override
    public List<AdminUser> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public Integer countUser() {
        return userMapper.countUser();
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    public AdminUser finduserbyId(int id) {
        return userMapper.finduserbyId(id);
    }

    @Override
    public void setPower(String permission, int id) {
        userMapper.setPower(permission,id);
    }

    @Override
    public void registerUser(String username, String password, String description, String address, String qq, String email, String github, String permission) {
        userMapper.registerUser(username, password, description, address, qq, email, github, permission);
    }

    @Override
    public void setInformation(String address, String qq, String email, String github, String description, String username) {
        userMapper.setInformation(address,qq,email,github,description,username);
    }
}
