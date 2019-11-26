package com.longer.service.guser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.exception.ChannelException;
import com.longer.base.SysSaveService;
import com.longer.dao.model.SysUserGroupUser;
import com.longer.service.guser.AbsUGroupUserService;
import com.longer.service.guser.vo.UserGroupUser;

/**
 * 
 * 添加用户组关联表信息
 * 
 * @author longlong
 * @version [版本号, 2019年1月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveListUGUs extends AbsUGroupUserService implements SysSaveService<List<UserGroupUser>> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// 用户、组ID集
	private List<SysUserGroupUser> lists = new ArrayList<>();


	protected SaveListUGUs addUserGroupUser(List<SysUserGroupUser> lists) {
		this.lists = lists;
		return this;
	}


	@Override
	public void execute() throws Exception {
		if(CollectionUtils.isNotEmpty(lists)) {
			lists.forEach(UGroup -> {
				sysUGroupUserMapper.insertUserAndGroup(UGroup);
			});
			logger.info("保存用户和用户组关联表成功。");
		}else {
			logger.warn("保存的用户和用户组关联表为空@!!!");
		}

	}

	public List<UserGroupUser> resultObj() throws Exception{
		List<UserGroupUser> lists = new ArrayList<>();
		this.lists.forEach(UGroup -> {
			try {
				UserGroupUser userGroups = transferObjectFields(UGroup, UserGroupUser.class, null);
				lists.add(userGroups);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败！" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return lists;
	}

}
