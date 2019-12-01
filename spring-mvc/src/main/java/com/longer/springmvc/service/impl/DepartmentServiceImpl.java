package com.longer.springmvc.service.impl;

import com.longer.springmvc.mapper.DepartmentMapper;
import com.longer.springmvc.model.Department;
import com.longer.springmvc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Department record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Department record) {
        return mapper.insertSelective(record);
    }

    @Override
    public Department selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Department record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return mapper.updateByPrimaryKey(record);
    }
}