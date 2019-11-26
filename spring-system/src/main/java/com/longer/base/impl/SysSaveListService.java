package com.longer.base.impl;

import java.util.List;

import com.longer.base.SysSaveService;

public interface SysSaveListService<T> extends SysSaveService<List<T>>
{
    List<T> resultObj() throws Exception;
}
