package com.tcl.aota.manage.app;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tcl.aota.cache.redis.cluster.Cluster;
import com.tcl.aota.cache.redis.cluster.ClusterInfo;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.SerializeUtil;
import com.tcl.aota.manage.AppManage;
import com.tcl.aota.persistent.model.App;

/**
 * @author kelong
 * @date 11/3/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-manage.xml")
public class RedisManageTest {
    @Resource
    private AppManage appManage;

    @Resource(name = "clusterInfo")
    private ClusterInfo clusterInfo;
    @Test
    public void testisnertAppCache(){
        List<App> appList=appManage.selectAppListByLatestPkg();
        Cluster cluser=clusterInfo.cluster(Constants.REDIS.CLUSTER_1);
        for(App app:appList){
            cluser.hSet(Constants.REDIS.APP.getBytes(),app.getId().toString().getBytes(), SerializeUtil.serialize(app));
        }
    }

    @Test
    public void testGetAppCache(){
        Cluster cluser=clusterInfo.cluster(Constants.REDIS.CLUSTER_1);
        Map<byte[],byte[]> appCaches=cluser.hGetAll(Constants.REDIS.APP.getBytes());
    }

    @Test
    public void testGetAppDetail(){
        Cluster cluser=clusterInfo.cluster(Constants.REDIS.CLUSTER_1);
        byte[] obj=cluser.hGet("app".getBytes(), "161".getBytes());
        App app=(App)SerializeUtil.unserialize(obj);
        System.out.println(app.getId());
    }

}
