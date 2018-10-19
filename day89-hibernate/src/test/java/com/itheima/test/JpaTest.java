package com.itheima.test;

import com.itheima.domain.Customer;
import com.itheima.utils.JpaUtil;
import org.junit.Test;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class JpaTest {
    @Test
    public void testJdbc(){
        Connection conn=null;
        PreparedStatement pstm=null;
        try {
            //1,加载和注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2,获取连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jpa?useUnicode=true&amp;characterEncoding=UTF-8","root","root");
            //3,获取预编译对象
            String sql="insert into cst_customer (cust_name,cust_level) VALUES (?,?)";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,"张三");
            pstm.setString(2,"高级vip");
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if (pstm!=null){
                    pstm.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                if (conn!=null) conn.close();//第二种写法
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testJpa(){
        /**
         * 创建实体管理类工厂，借助 Persistence 的静态方法获取
         * 其中传递的参数为持久化单元名称，需要 jpa 配置文件中指定
         */
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("myJpa");
        //创建实体管理类
        EntityManager entityManager = managerFactory.createEntityManager();
        //获取事务
        EntityTransaction transaction = entityManager.getTransaction();
        //开启事务
        transaction.begin();
        Customer c=new Customer();
        c.setCustName("李四");
        c.setCustLevel("会员");
        //保存操作
        entityManager.persist(c);
        transaction.commit();
        entityManager.close();
        managerFactory.close();
    }
    //增加 persist
    @Test
    public void testPersist(){
        Customer c=new Customer();
        c.setCustName("传智学院");
        c.setCustLevel("VIP客户");
        c.setCustSource("网络");
        c.setCustIndustry("IT教育");
        c.setCustAddress("广州市天河区珠吉路58号");
        c.setCustPhone("400-618-9090");
        EntityManager entityManger = null;
        EntityTransaction transaction = null;
        try {
            entityManger=JpaUtil.createEntityManger();
            transaction=entityManger.getTransaction();
            transaction.begin();
            entityManger.persist(c);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManger.close();
        }
    }
    //修改 merge
    @Test
    public void testMerge(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //准备修改对象,必须先获取对象:根据主键获取
            Customer c=entityManager.find(Customer.class,1l);
            c.setCustAddress("爱我中华");
            entityManager.merge(c);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //删除 remove
    @Test
    public void testRemove(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //准备修改对象,必须先获取对象:根据主键获取
            Customer c=entityManager.find(Customer.class,1l);
            entityManager.remove(c);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //查询 find
    @Test
    public void testFind(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //准备修改对象,必须先获取对象:根据主键获取
            Customer c=entityManager.find(Customer.class,2l);
            transaction.commit();
            System.out.println(c);
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //查询 getReference 延迟加载
    @Test
    public void testGetReference(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //准备修改对象,必须先获取对象:根据主键获取
            Customer c=entityManager.getReference(Customer.class,2l);
            transaction.commit();
            System.out.println(c);
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

    //查询实体的缓存问题:查询结果放在缓存了,以后直接从缓存中取出
    @Test
    public void testFindTwice(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //准备修改对象,必须先获取对象:根据主键获取
            Customer c=entityManager.find(Customer.class,2l);
            Customer c2=entityManager.find(Customer.class,2l);
            System.out.println(c==c2);
            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //复杂查询:查询全部 创建query对象
    @Test
    public void testFindAll(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //创建query
            String sql="from Customer";
            Query query = entityManager.createQuery(sql);
            List<Customer> resultList = query.getResultList();
            for (Customer c:resultList){
                System.out.println(c);
            }
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //分页查询
    @Test
    public void testFindByPage(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //创建query
            String sql="from Customer";
            Query query = entityManager.createQuery(sql);
            query.setFirstResult(1);    //跳过多少个
            query.setMaxResults(2);     //每页展示数
            List<Customer> resultList = query.getResultList();
            for (Customer c:resultList){
                System.out.println(c);
            }
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //条件查询
    @Test
    public void testFindByCondition(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //创建query
            String sql="from Customer where custName = ?";
            Query query = entityManager.createQuery(sql);
            query.setParameter(1,"aa");
            List<Customer> resultList = query.getResultList();
            for (Customer c:resultList){
                System.out.println(c);
            }
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //排序查询
    @Test
    public void testFindByOrder(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //创建query
            String sql="from Customer order by custId desc";
            Query query = entityManager.createQuery(sql);
            List<Customer> resultList = query.getResultList();
            for (Customer c:resultList){
                System.out.println(c);
            }
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
    //统计查询
    @Test
    public void testFindCount(){
        //定义对象
        EntityManager entityManager=null;
        EntityTransaction transaction=null;
        try{
            entityManager=JpaUtil.createEntityManger();
            transaction=entityManager.getTransaction();
            transaction.begin();
            //创建query
            String sql="select count(custSource) from Customer";
            Query query = entityManager.createQuery(sql);
            Object singleResult = query.getSingleResult();
            System.out.println(singleResult);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }
}
