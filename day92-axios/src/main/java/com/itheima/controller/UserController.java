package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 初始化页面查找全部
     * @return
     */
    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    /**
     * 点击修改,根据id回显一个user
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public User findById(Integer id){
        return userService.findById(id);
    }

    @RequestMapping("/update")
    public void update(@RequestBody User user){
        userService.update(user);
    }
}
