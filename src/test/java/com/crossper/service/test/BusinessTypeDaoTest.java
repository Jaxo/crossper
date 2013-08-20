/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.service.test;

import com.crossper.repository.dao.impl.BusinessCategoryDaoImpl;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration 
public class BusinessTypeDaoTest extends AbstractJUnit4SpringContextTests {
    
    @Resource
    BusinessCategoryDaoImpl bizTypeDao;
    
    public BusinessTypeDaoTest() {
        
    }
    
    public void setBizTypeDao( BusinessCategoryDaoImpl dao) {
        this.bizTypeDao = dao;
    }
    //@Before
    public void setUp() {
        //setBizTypeDao();
        System.out.println("setUp");
    }

    //@After
    public void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    public void testBusinessCategoryList() {
        bizTypeDao.getCategories();
        
    }
    @Test
    public void testCategoryList() {
        bizTypeDao.getCategoriesForDisplay();
        
    }
    
    
}
