package com.etoc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.etoc.model.Employee;
@Mapper
public interface EmployeeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    int insert(Employee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    int insertSelective(Employee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    Employee selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    int updateByPrimaryKey(Employee record);
}