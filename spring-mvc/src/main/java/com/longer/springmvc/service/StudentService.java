package com.longer.springmvc.service;

import com.longer.springmvc.model.Student;

public interface StudentService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Sun Dec 01 11:21:31 CST 2019
     */
    int deleteByPrimaryKey(Integer stuNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Sun Dec 01 11:21:31 CST 2019
     */
    int insert(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Sun Dec 01 11:21:31 CST 2019
     */
    int insertSelective(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Sun Dec 01 11:21:31 CST 2019
     */
    Student selectByPrimaryKey(Integer stuNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Sun Dec 01 11:21:31 CST 2019
     */
    int updateByPrimaryKeySelective(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table student
     *
     * @mbggenerated Sun Dec 01 11:21:31 CST 2019
     */
    int updateByPrimaryKey(Student record);
}