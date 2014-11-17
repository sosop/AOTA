package com.tcl.aota.persistent.dao.db;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author kelong
 * @date 10/30/14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-persistent.xml")
public class AppDAOTest {

	@Resource
	private AppDAO appDAO;


}
