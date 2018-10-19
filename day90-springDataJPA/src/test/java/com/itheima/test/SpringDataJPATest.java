package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataJPATest {
    @Autowired
    private CustomerDao customerDao;

    //==============================JPA中的简单CRUD
    //保存记录:调用:save()
    @Test
    public void testSave(){
        Customer c=new Customer();
        c.setCustName("张三");
        customerDao.save(c);
    }

    //修改记录:save() 区别于增加:看有没有主键id
    @Test
    public void testUpdate(){
        /*Customer c=new Customer();
        c.setCustId(5l);
        c.setCustName("李四");*/
        Customer c = customerDao.findOne(5l);
        c.setCustName("李四");
        customerDao.save(c);
    }

    //根据id删除一条记录 delete()
    @Test
    public void testDelete(){
        customerDao.delete(6l);
    }

    //根据Id查找一个 findone()
    @Test
    public void testFindone(){
        Customer customer = customerDao.findOne(2l);
        System.out.println(customer);
    }

    //查询全部
    @Test
    public void testFindAll(){
        List<Customer> list = customerDao.findAll();
        for (Customer c:list){
            System.out.println(c);
        }
    }

    //==============================JPA中的复杂查询:在dao层添加方法,类似创建query对象
    //查询全部
    @Test
    public void findAllCustomer(){
        List<Customer> allCustomer = customerDao.findAllCustomer();
        for (Customer c:allCustomer){
            System.out.println(c);
        }
    }
    //条件查询
    @Test
    public void findCustomer(){
        Customer customer = customerDao.findCustomer("李%");
        System.out.println(customer);
    }

    //实现修改:必须加入事务
    @Test
    @Transactional
    @Rollback(value = false)
    public void updateCustomer(){
        customerDao.updateCustomer("李莉",5l);
    }


    //==============================sql语句查询
    @Test
    public void testFindSql(){
        List<Customer> customerList = customerDao.findSql();
        for (Customer c:customerList){
            System.out.println(c);
        }
    }

    //================方法命名规则查询
    @Test
    public void testFindByCustName(){
        Customer customer = customerDao.findByCustName("达内");
        System.out.println(customer);
    }

    //=================
    /**
     * 查询一个对象:
     *      findOne  ： 立即加载：底层调用的是EntityManager中的find方法
     *      getOne : 延迟加载：底层调用的是EntityManager中的getReference方法
     *      could not initialize proxy - no Session
     */
    @Test
    @Transactional
    public void testGetone(){
        Customer customer = customerDao.getOne(5l);
        System.out.println(customer);
    }

    /**
     * 按照排序
     */
    @Test
    public void testSort(){
        Sort s=new Sort(Direction.DESC,"custId");
        List<Customer> list = customerDao.findAll(s);
        for (Customer c:list){
            System.out.println(c);
        }
    }

    /**
     * 分页查询:先排序,然后分页,page:0 为第一页,size页面大小.
     */
    @Test
    public void testFindByPage(){
        Sort s=new Sort(Direction.DESC,"custId");
        Pageable p=new PageRequest(0,2,s);
        Page<Customer> page = customerDao.findAll(p);
        /*System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        List<Customer> list = page.getContent();*/
        for (Customer c:page){
            System.out.println(c);
        }
    }

    /**
     * 统计查询:
     */
    @Test
    public void testCount(){
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 判断一个对象是否存在：
     *      1）select * from cst_customer where id = ?
     *      2）select count(*) from cst_customer where id = ?
     *
     * springDataJpa用的是哪一种？:第二种
     */
    @Test
    public void test6(){
        boolean exists = customerDao.exists(8L);
        System.out.println(exists);
    }
}
