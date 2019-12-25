package com.blog.myblog.dao;

import com.blog.myblog.pojo.AdminUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from blog_admin_user where username=#{username}")
    AdminUser findAdminuser(@Param("username")String username);
    @Select("select * from blog_admin_user where id=#{id}")
    AdminUser finduserbyId(@Param("id")int id);
    @Select("select * from blog_admin_user")
    List<AdminUser> queryAll();
    @Select("select count(*) from blog_admin_user")
    Integer countUser();
    @Delete("delete from blog_admin_user where id=#{id}")
    void deleteUser(@Param("id")int id);
    @Update("update blog_admin_user set permission=#{permission} where id=#{id}")
    void setPower(@Param("permission")String permission, @Param("id")int id);
    @Insert("insert into blog_admin_user(username,password,description,address,qq,email,github,permission) values (#{username},#{password},#{description},#{address},#{qq},#{email},#{github},#{permission})")
    void registerUser(@Param("username")String username,@Param("password")String password,@Param("description")String description,@Param("address")String address,@Param("qq")String qq,@Param("email")String email,@Param("github")String github,@Param("permission")String permission);
    @Update("update blog_admin_user set address=#{address}, qq=#{qq}, email=#{email}, github=#{github},description=#{description} where username=#{username}")
    void setInformation(@Param("address")String address,@Param("qq")String qq,@Param("email")String email,@Param("github")String github,@Param("description")String description,@Param("username")String username);
    @Update("update blog_admin_user set password=#{password} where username=#{username}")
    void setPassword(@Param("password")String password, @Param("username")String username);
}
