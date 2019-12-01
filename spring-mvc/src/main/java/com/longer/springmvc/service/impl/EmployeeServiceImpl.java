package com.longer.springmvc.service.impl;


import com.longer.springmvc.mapper.EmployeeMapper;
import com.longer.springmvc.model.Employee;
import com.longer.springmvc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService  {
    @Autowired
    private EmployeeMapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Employee record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Employee record) {
        return mapper.insertSelective(record);
    }

    @Override
    public Employee selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Employee record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Employee record) {
        return mapper.updateByPrimaryKey(record);
    }
}
