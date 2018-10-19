package com.itheima.dao;

import com.itheima.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<SysUser,Long>,JpaSpecificationExecutor<SysUser> {
}
