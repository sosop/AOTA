package com.tcl.aota.manage;

import java.util.List;
import java.util.Map;

import com.tcl.aota.persistent.model.App;
import com.tcl.aota.persistent.model.Package;

public interface PackageManage {
    public List<Package> queryByCondition(Map<String, Object> condition);

    public String release(long id);

    public int dismiss(List<Long> ids);

    /**
     * new package and put app to it
     *
     * @param ids
     * @return
     */
    public int putAppToPackage(List<Long> ids);
    
    public List<App> getAppsByPackage(long packageId);
}
