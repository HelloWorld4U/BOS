package com.itheima.bos.dao.base.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardDAO;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardDAO_test <br/>  
 * Function:  <br/>  
 * Date:     Jan 14, 2018 9:24:41 PM <br/>       
 */
@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StandardDAO_test {

    @Autowired
    private StandardDAO standardDAO;
    
    @Test
    public void test01() {

        List<Standard> list = standardDAO.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void test02() {

        for(int i = 0;i<10;i++){
            Standard s1 = new Standard();
            s1.setName("张三"+i);
            s1.setMaxLength(i);
            s1.setMaxWeight(i);
            standardDAO.save(s1);
        }
      
    }
    
    @Test
    public void test03() {
       /*Standard s = standardDAO.getOne(1L);
       System.out.println(s);*/
        
        List<Standard> list = standardDAO.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void test04() {
       Standard s = standardDAO.findOne(1L);
       System.out.println(s);
    }
    

    @Test
    public void test05() {
       Standard s = standardDAO.getOne(1l);
       System.out.println(s);
    }
    
  /*  @Test
    public void test06() {
        standardDAO.deletbbbbb(111,11111,11111);
    }*/
}
  
