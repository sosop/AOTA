package com.tcl.aota.manage.app;

import com.tcl.aota.manage.AppManage;
import com.tcl.aota.persistent.model.App;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-manage.xml")
public class AppManageTest {
	@Resource(name = "appManage")
	private AppManage appManage;
	
	@Test
	public void testAppManageIsNotNull() {
		assertThat(appManage, Matchers.notNullValue());
	}

    @Test
    public void testselectAppList(){
        List<App> appList=appManage.selectAppListByLatestPkg();
    }
}
