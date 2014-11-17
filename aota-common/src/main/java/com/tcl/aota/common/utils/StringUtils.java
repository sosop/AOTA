package com.tcl.aota.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolong.hou
 *         便于字符串拼接
 */
public class StringUtils {

    public static String append(Object... args) {
        StringBuffer buf = new StringBuffer();
        if (args != null && args.length > 0) {
            for (Object obj : args) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static boolean isNull(String... str) {
        boolean nil = true;
        if (null != str && str.length > 0) {
            for (String s : str) {
                // 只要有一个元素不为空，那么数组就不为空
                if (null != s && !"".equals(s.trim())) {
                    nil = false;
                    break;
                }
            }
        }
        return nil;
    }

    public static List<Long> parseList(String ids) {
        String[] idArr = ids.split(",");
        List<Long> idList = new ArrayList<Long>();
        if (idArr != null) {
            for (String id : idArr) {
                idList.add(new Long(id));
            }
        }
        return idList;
    }

    public static boolean isNullList(List list) {
        boolean flag = false;
        if (list == null || list.size() == 0) {
            flag = true;
        }
        return flag;
    }
}