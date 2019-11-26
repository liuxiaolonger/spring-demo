package com.etoc.base.impl;


import java.util.List;

import com.etoc.base.SysQueryService;

public interface SysQueryListService<T> extends SysQueryService<List<T>>
{
	
    List<T> resultObj() throws Exception;
    
}
