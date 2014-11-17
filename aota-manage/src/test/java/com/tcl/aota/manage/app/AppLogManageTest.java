package com.tcl.aota.manage.app;

import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.DateUtil;
import com.tcl.aota.manage.imp.AppLogManageImp;
import com.tcl.aota.persistent.dao.db.AppLogDAO;

public class AppLogManageTest {

	private Mockery context = new Mockery();

	private final AppLogDAO appLogDAO = context.mock(AppLogDAO.class);

	private AppLogManageImp manage = new AppLogManageImp();

	@Before
	public void init() {
		manage.setAppLogDAO(appLogDAO);

		final Map<String, Object> conditions = new HashMap<>();
		context.checking(new Expectations() {
			{
				conditions.put("start",
						DateUtil.getBeforeDays(Constants.Common.DAY));
				conditions.put("end", DateUtil.getBeforeDays(0));
				conditions.put("status", new int[] { 1, 2 });
				allowing(appLogDAO).selectBetweenDate(conditions);
				will(returnValue(10));

				conditions.put("start",
						DateUtil.getBeforeDays(Constants.Common.WEEK));
				conditions.put("end", DateUtil.getBeforeDays(0));
				conditions.put("status", new int[] { 1, 2 });
				allowing(appLogDAO).selectBetweenDate(conditions);
				will(returnValue(20));

				conditions.put("start",
						DateUtil.getBeforeDays(Constants.Common.DAY));
				conditions.put("end", DateUtil.getBeforeDays(0));
				conditions.put("status", new int[] { 2 });
				allowing(appLogDAO).selectBetweenDate(conditions);
				will(returnValue(6));

				conditions.put("start",
						DateUtil.getBeforeDays(Constants.Common.WEEK));
				conditions.put("end", DateUtil.getBeforeDays(0));
				conditions.put("status", new int[] { 2 });
				allowing(appLogDAO).selectBetweenDate(conditions);
				will(returnValue(8));
			}
		});
	}

	@Test
	public void testAppLogDAOisNotNull() {
		assertThat(appLogDAO, Matchers.notNullValue());
	}

	@Test
	public void testGetDownloadByDay() {
		assertThat(10, Matchers.is(10));
	}

	@Test
	public void testGetDownloadByWeek() {
		assertThat(20, Matchers.is(20));
	}

	@Test
	public void testGetUpgradeByDay() {
		assertThat(6, Matchers.is(6));
	}

	@Test
	public void testGetUpgradeByWeek() {
		assertThat(8, Matchers.is(8));
	}

	@Test
	public void testGetAll() {
		Map<String, Object> map = new HashMap<>();
		assertThat(map, Matchers.is(map));
	}

	@Test
	public void testStatistics() {
		
	}

	@Test
	public void testBatchInsert() throws Exception {

	}
}
