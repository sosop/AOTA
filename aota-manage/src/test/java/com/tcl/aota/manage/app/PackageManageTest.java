package com.tcl.aota.manage.app;

import com.tcl.aota.manage.PackageManage;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-manage.xml")
public class PackageManageTest {
	@Resource(name = "packageManage")
	private PackageManage packageManage;
	
	@Test
    public void putAppToPackageTest()
    {
        List<Long> ids=new ArrayList<Long>();
        ids.add(new Long(161));
        ids.add(new Long(162));
        int count=packageManage.putAppToPackage(ids);
        assertThat(count,Matchers.greaterThanOrEqualTo(0));
    }
    @Test
    public void releasePackateTest(){
        packageManage.release(5);
    }
}
