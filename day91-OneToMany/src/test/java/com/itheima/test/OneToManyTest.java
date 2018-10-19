package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional
    @Rollback(false)
    public void test1(){
        Customer c = new Customer();
        c.setCustName("TBD云集中心");
        c.setCustLevel("VIP客户");
        c.setCustSource("网络");
        c.setCustIndustry("商业办公");
        c.setCustAddress("昌平区北七家镇");
        c.setCustPhone("010-84389340");
        LinkMan l = new LinkMan();
        l.setLkmName("TBD联系人");
        l.setLkmGender("男");
        l.setLkmMobile("13811111111");
        l.setLkmPhone("010-34785348");
        l.setLkmEmail("98354834@qq.com");
        l.setLkmPosition("老师");
        l.setLkmMemo("还行吧");
        c.getLinkManList().add(l);
        l.setCustomer(c);
        customerDao.save(c);
        linkManDao.save(l);
    }

    /**
     * 级联删除
     */
    @Test
    @Transactional
    @Rollback(false)
    public void test2(){
        customerDao.delete(14l);
    }

    /**
     * 对象导航查询
     */
    @Test
    //由于是在java代码中测试，为了解决no session问题，将操作配置到同一个事务中
    @Transactional
    @Rollback(false)
    public void test4(){
        Customer customer = customerDao.findOne(13l);
        System.out.println("aaa");
        List<LinkMan> linkManList = customer.getLinkManList();
        for(LinkMan linkMan : linkManList) {
            System.out.println(linkMan);
        }
    }

    @Test
    //由于是在java代码中测试，为了解决no session问题，将操作配置到同一个事务中
    @Transactional
    @Rollback(false)
    public void test5(){
        LinkMan linkMan1 = linkManDao.findOne(5l);
        System.out.println("aaa");
        Customer customer = linkMan1.getCustomer();
        System.out.println(customer);
    }

}
