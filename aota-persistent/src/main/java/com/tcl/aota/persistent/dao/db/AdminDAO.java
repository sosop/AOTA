package com.tcl.aota.persistent.dao.db;

import com.tcl.aota.persistent.model.Admin;

import java.util.List;

public interface AdminDAO {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table aota_admin
     *
     * @mbggenerated Tue Oct 28 09:29:17 CST 2014
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table aota_admin
     *
     * @mbggenerated Tue Oct 28 09:29:17 CST 2014
     */
    int insert(Admin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table aota_admin
     *
     * @mbggenerated Tue Oct 28 09:29:17 CST 2014
     */
    Admin selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table aota_admin
     *
     * @mbggenerated Tue Oct 28 09:29:17 CST 2014
     */
    int updateByPrimaryKeySelective(Admin record);


    /**
     * get admin obj by userName
     * @param userName
     * @return
     */
    List<Admin> selectByName(String userName);
}