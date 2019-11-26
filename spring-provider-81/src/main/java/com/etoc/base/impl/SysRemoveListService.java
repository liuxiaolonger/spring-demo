package com.etoc.base.impl;

import java.util.List;

import com.etoc.base.SysRemoveService;

public interface SysRemoveListService extends SysRemoveService
{
	public SysRemoveListService addObject(List<String> ids);
}
