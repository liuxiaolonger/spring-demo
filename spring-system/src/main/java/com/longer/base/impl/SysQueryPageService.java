package com.longer.base.impl;


import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;
import com.longer.base.SysQueryService;

public interface SysQueryPageService<T extends BaseResource> extends SysQueryService<PageInfo<T>>
{
    
	PageInfo<T> resultObj() throws Exception;
    
}
