package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecificationTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * interface Path<X> extends Expression<X>
     * equal 精确查询
     */
    @Test
    public void test2(){
        Specification<Customer> spec=new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Predicate predicate = cb.equal(custName,"达内");
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * like模糊查询
     */
    @Test
    public void test1(){
        Specification<Customer> spec=new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Predicate predicate = cb.like(custName.as(String.class), "传智%");
                return predicate;
            }
        };
        List<Customer> list = customerDao.findAll(spec);
        for (Customer c:list){
            System.out.println(c);
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void test3(){
        Specification<Customer> spec=new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               return cb.like(root.<String>get("custName"),"传智%");
            }
        };
        /**
         * 构造分页参数
         * Pageable : 接口
         * PageRequest实现了Pageable接口，调用构造方法的形式构造
         * 第一个参数：页码（从0开始）
         * 第二个参数：每页查询条数
         */
        Pageable pageable=new PageRequest(1,2);
        /**
         * 分页查询，封装为Spring Data Jpa 内部的page bean
         * 此重载的findAll方法为分页方法需要两个参数
         * 第一个参数：查询条件Specification
         * 第二个参数：分页参数
         */
        Page<Customer> page = customerDao.findAll(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Customer c:page.getContent()){
            System.out.println(c);
        }
    }

}
