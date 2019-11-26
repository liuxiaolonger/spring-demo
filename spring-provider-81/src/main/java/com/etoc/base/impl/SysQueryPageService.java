package com.etoc.base.impl;


import com.etoc.base.SysQueryService;
import com.etoc.util.BaseResource;
import com.etoc.util.PageInfo;

public interface SysQueryPageService<T extends BaseResource> extends SysQueryService<PageInfo<T>>
{
    
	PageInfo<T> resultObj() throws Exception;
    
}
