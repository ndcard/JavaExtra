package com.itheima.dao;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 定义好了一个符合 Spring Data JPA 规范的 Dao 层接口
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 */
public interface CustomerDao extends JpaRepository<Customer,Long>,JpaSpecificationExecutor<Customer> {

    //============query使用JPQL方式查询
    //查询全部
    @Query(value = "from Customer")
    public List<Customer> findAllCustomer();
    //查询条件
    @Query("from Customer where custName like ?")
    public Customer findCustomer(String s);
    //修改
    @Query("update Customer set custName=? where custId=?")
    @Modifying
    public void updateCustomer(String s,Long l);

    //============sql语句查询
    //nativeQuery : 使用本地sql的方式查询
    @Query(value = "select*from cst_customer",nativeQuery = true)
    public List<Customer> findSql();

    //===============方法命名规则查询
    public Customer findByCustName(String s);


}
