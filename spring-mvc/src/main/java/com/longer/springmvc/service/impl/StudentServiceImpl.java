package com.longer.springmvc.service.impl;


import com.longer.springmvc.mapper.StudentMapper;
import com.longer.springmvc.model.Student;
import com.longer.springmvc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper mapper;
    @Override
    public int deleteByPrimaryKey(Integer stuNo) {
        return mapper.deleteByPrimaryKey(stuNo);
    }

    @Override
    public int insert(Student record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Student record) {
        return mapper.insertSelective(record);
    }

    @Override
    public Student selectByPrimaryKey(Integer stuNo) {
        return mapper.selectByPrimaryKey(stuNo);
    }

    @Override
    public int updateByPrimaryKeySelective(Student record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Student record) {
        return mapper.updateByPrimaryKey(record);
    }
}