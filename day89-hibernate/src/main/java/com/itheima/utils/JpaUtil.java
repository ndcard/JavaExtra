package com.itheima.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JpaUtil {
    private static EntityManagerFactory em;
    static {
        em = Persistence.createEntityManagerFactory("myJpa");
    }
    //使用工厂生成管理器对象
    public static EntityManager createEntityManger(){
        return em.createEntityManager();
    }
}
