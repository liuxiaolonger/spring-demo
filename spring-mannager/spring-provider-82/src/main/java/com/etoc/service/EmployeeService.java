package com.etoc.service;

import java.util.List;

import com.etoc.model.Employee;

public interface EmployeeService {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table employee
	 *
	 * @mbg.generated Sat Mar 23 13:00:11 CST 2019
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table employee
	 *
	 * @mbg.generated Sat Mar 23 13:00:11 CST 2019
	 */
	int insert(Employee record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table employee
	 *
	 * @mbg.generated Sat Mar 23 13:00:11 CST 2019
	 */
	int insertSelective(Employee record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table employee
	 *
	 * @mbg.generated Sat Mar 23 13:00:11 CST 2019
	 */
	Employee selectByPrimaryKey(Integer id);

	List<Employee> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table employee
	 *
	 * @mbg.generated Sat Mar 23 13:00:11 CST 2019
	 */
	int updateByPrimaryKeySelective(Employee record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table employee
	 *
	 * @mbg.generated Sat Mar 23 13:00:11 CST 2019
	 */
	int updateByPrimaryKey(Employee record);
}