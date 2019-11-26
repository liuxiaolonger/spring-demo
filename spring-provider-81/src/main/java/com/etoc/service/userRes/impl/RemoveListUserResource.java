package com.etoc.service.userRes.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.SysRemoveService;
import com.etoc.service.userRes.AbsUserResourceService;

/**
 * 
 * 批量删除用户资源关联表 <功能详细描述> <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("removeListUserResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RemoveListUserResource extends AbsUserResourceService implements SysRemoveService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String userId;

	private String resourceId;

	protected RemoveListUserResource addUserId(String userId) {
		this.userId = userId;
		return this;
	}

	protected RemoveListUserResource addResourceId(String resourceId) {
		this.resourceId = resourceId;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			// 根据用户id删除关联的资源信息
			map.put(MODEL_USERID, userId);
		}
		if (StringUtils.isNotEmpty(resourceId)) {
			// 根据资源id删除关联的用户信息
			map.put(MODEL_RESOURCEID, resourceId);
		}

		logger.info("执行删除关联表的参数userId= {}, resourceId= {}", userId, resourceId);

		// 当参数不为空时执行sql语句
		if (map.size() != 0) {
			sysUserResourceMapper.deleteSysUserResourceModel(map);
		}

	}

}
