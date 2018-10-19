package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDao {

    @Select("select*from user")
    public List<User> findAll();

    @Select("select * from user where id = #{id}")
    public User findById(Integer id);

    @Update("update user set username=#{username},password=#{password},name=#{name} where id=#{id}")
    public void update(User user);
}
