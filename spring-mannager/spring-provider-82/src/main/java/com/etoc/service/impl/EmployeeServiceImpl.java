package com.etoc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoc.mapper.EmployeeMapper;
import com.etoc.model.Employee;
import com.etoc.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	 private   EmployeeMapper mapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Employee selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Employee record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employee> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

}
