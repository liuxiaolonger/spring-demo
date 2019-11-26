package com.longer.base.impl;

import java.util.List;

import com.longer.base.SysRemoveService;

public interface SysRemoveListService extends SysRemoveService
{
	public SysRemoveListService addObject(List<String> ids);
}
