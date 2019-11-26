package com.longer.base.impl;


import java.util.List;

import com.longer.base.SysQueryService;

public interface SysQueryListService<T> extends SysQueryService<List<T>>
{
	
    List<T> resultObj() throws Exception;
    
}
