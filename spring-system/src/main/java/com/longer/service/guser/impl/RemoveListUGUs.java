package com.longer.service.guser.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.longer.base.SysRemoveService;
import com.longer.service.guser.AbsUGroupUserService;

/**
 * 
 * 批量删除用户组关联表
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveListUGUs extends AbsUGroupUserService implements SysRemoveService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;

	private String groupId;

	protected RemoveListUGUs addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	protected RemoveListUGUs addGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			// 根据用户ID删除关联表数据
			map.put(MODEL_USERID, userId);
		}
		if (StringUtils.isNotEmpty(groupId)) {
			// 根据用户组ID 删除关联表数据
			map.put(MODEL_GROUPID, groupId);
		}

		logger.info("执行删除关联表的参数userId= {}, groupId= {}", userId, groupId);

		// 当参数不为空时执行sql语句
		if (map.size() != 0) {
			sysUGroupUserMapper.deleteByPrimaryKey(map);
		}
	}

}
