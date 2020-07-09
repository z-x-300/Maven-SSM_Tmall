package com.zhangxin.tmall.service;

import com.zhangxin.tmall.dao.UserDao;
import com.zhangxin.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    //获取所有用户列表
    public List<User> getAllUserForAdmin(){
        List<User> userList =userDao.getAllUserForAdmin();
        return userList;
    }

    //修改用户名
    public void updateUserNameForAdmin(int id,String name){
        userDao.updateUserNameForAdmin(id,name);
    }

    //根据用户名查询用户
    public User getUserByUserName(String name){
        User user =userDao.getUserByUserName(name);
        return user;
    }
    //修改密码
    public void updatePasswordForAdmin(int id,String password){
        userDao.updatePasswordForAdmin(id,password);
    }
    //删除用户
    public void deleteUserForAdmin(int id){
        userDao.deleteUserForAdmin(id);
    }
    //获取记录在数据库第几条，删除时需要使用
    public int getUserLocation(int id){
        return userDao.getUserLocation(id);
    }
}
