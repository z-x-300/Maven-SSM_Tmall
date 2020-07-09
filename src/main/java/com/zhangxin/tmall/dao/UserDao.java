package com.zhangxin.tmall.dao;

import com.zhangxin.tmall.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    //获取所有用户列表
    public List<User> getAllUserForAdmin();
    //修改用户名
    public void updateUserNameForAdmin(@Param("id") int id,@Param("name") String name);
    //根据用户名查询用户
    public User getUserByUserName(String name);
    //修改密码
    public void updatePasswordForAdmin(@Param("id") int id,@Param("password") String password);
    //删除用户
    public void deleteUserForAdmin(int id);
    //获取记录在数据库第几条，删除时需要使用
    public int getUserLocation(int id);

}
