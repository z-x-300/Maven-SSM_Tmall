package com.zhangxin.tmall.service;

import com.zhangxin.tmall.pojo.User;


import java.util.List;

public interface UserService {

    //获取所有用户列表
    public List<User> getAllUserForAdmin();
    //修改用户名
    public void updateUserNameForAdmin(int id,String name);
    //根据用户名查询用户
    public User getUserByUserName(String name);
    //修改密码
    public void updatePasswordForAdmin(int id,String password);
    //删除用户
    public void deleteUserForAdmin(int id);
    //获取记录在数据库第几条，删除时需要使用
    public int getUserLocation(int id);
    //根据用户id获取用户名
    public User getUserById(Integer id);
}
