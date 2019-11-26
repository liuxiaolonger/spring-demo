package com.etoc.service.userRes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysQueryListService;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysUserResource;
import com.etoc.service.userRes.AbsUserResourceService;
import com.etoc.service.userRes.vo.UserResource;

/**
 * 
 * 查找用户资源关联表 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("queryListUserResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QueryListUserResource extends AbsUserResourceService implements SysQueryListService<UserResource> {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String resourceId;

	private String userId;
	
	private Integer isLeaf;

	private List<SysUserResource> uResource = new ArrayList<>();

	protected QueryListUserResource addResourceId(String resourceId) {
		this.resourceId = resourceId;
		return this;
	}

	protected QueryListUserResource addUserId(String userId) {
		this.userId = userId;
		return this;
	}
	
	protected QueryListUserResource addIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
		return this;
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(userId)) {
			// 根据用户ID 查询关联表数据
			map.put(MODEL_USERID, userId);
		}
		if (StringUtils.isNotEmpty(resourceId)) {
			// 根据资源ID 查询关联表数据
			map.put(MODEL_RESOURCEID, resourceId);
		}
		if(isLeaf != null) {
			// 过滤资源是否是叶子节点
			map.put(MODEL_ISLEAF, isLeaf);
		}

		// 当参数不为空时执行sql语句
		if(map.size() != 0) {
			uResource = sysUserResourceMapper.selectBySysResourceModel(map);
		}
		if (CollectionUtils.isEmpty(uResource)) {
			logger.warn("关联表为空");
		}

	}

	/*
	 * 返回结果集
	 */
	@Override
	public List<UserResource> resultObj() throws Exception {
		List<UserResource> userResources = new ArrayList<>();
		uResource.forEach(sysUserResource -> {
			try {
				UserResource UResource = transferObjectFields(sysUserResource, UserResource.class, null);
				userResources.add(UResource);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败e:" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});
		return userResources;
	}

}
