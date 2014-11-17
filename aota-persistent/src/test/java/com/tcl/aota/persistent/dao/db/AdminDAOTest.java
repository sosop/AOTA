package com.tcl.aota.persistent.dao.db;

import com.tcl.aota.common.utils.PasswordEncrypt;
import com.tcl.aota.persistent.model.Admin;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-persistent.xml")
public class AdminDAOTest {
	
	@Resource(name = "adminDAO")
	private AdminDAO adminDAO;
	
	private Admin admin;
	
	@Before
	public void init() {
		admin = new Admin();
		admin.setAdminName("ke.long@tcl.com");
		admin.setAdminPass(PasswordEncrypt.encrypt("123456", 1));
		admin.setVersion("1");
        admin.setCreateTime(new Date());
        admin.setUpdateTime(new Date());
	}
	
	
	@Test
	public void testAdminDAOIsNotNull() {
		assertThat(adminDAO, Matchers.notNullValue());
	}
	
	@Test
	public void testInsert() {
		assertThat(adminDAO.insert(admin), Matchers.greaterThan(0));
	}
}
