package com.itheima.test;

import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.SysRole;
import com.itheima.domain.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 需求：
     * 保存用户和角色
     * 要求：
     * 创建2个用户和3个角色
     * 让1号用户具有1号和2号角色(双向的)
     * 让2号用户具有2号和3号角色(双向的)
     * 保存用户和角色
     * 问题：
     * 在保存时，会出现主键重复的错误，因为都是要往中间表中保存数据造成的。
     * 解决办法：
     * 让任意一方放弃维护关联关系的权利
     */

    @Test
    public void test1(){
        SysUser u1=new SysUser();
        //u1.setUserId(8l);不能设置
        u1.setUserName("张三3");
        SysRole r1=new SysRole();
        //r1.setRoleId(9l);
        r1.setRoleName("李四3");
        //建立关系
        u1.getRoles().add(r1);
        r1.getUsers().add(u1);
        userDao.save(u1);
        roleDao.save(r1);
    }

    /**
     * 删除操作
     * 在多对多的删除时，双向级联删除根本不能配置
     * 禁用
     * 如果配了的话，如果数据之间有相互引用关系，可能会清空所有数据
     */
    @Test
    @Transactional
    @Rollback(false)//设置为不回滚
    public void testDelete() {
        userDao.delete(1l);
    }

}
