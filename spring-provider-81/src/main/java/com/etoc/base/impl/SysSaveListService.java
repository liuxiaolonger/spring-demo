package com.etoc.base.impl;

import java.util.List;

import com.etoc.base.SysSaveService;

public interface SysSaveListService<T> extends SysSaveService<List<T>>
{
    List<T> resultObj() throws Exception;
}
