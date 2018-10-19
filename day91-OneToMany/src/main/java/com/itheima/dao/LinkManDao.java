package com.itheima.dao;

import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 定义好了一个符合 Spring Data JPA 规范的 Dao 层接口
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 */
public interface LinkManDao extends JpaRepository<LinkMan,Long>,JpaSpecificationExecutor<LinkMan> {

}
