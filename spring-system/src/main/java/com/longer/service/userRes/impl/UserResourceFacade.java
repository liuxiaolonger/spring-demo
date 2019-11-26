package com.longer.service.userRes.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etoc.util.UUIDUtil;
import com.longer.base.BaseService;
import com.longer.base.impl.SysQueryListService;
import com.longer.dao.model.SysUserResource;
import com.longer.service.userRes.AbsUserResourceService;
import com.longer.service.userRes.UserResourceService;
import com.longer.service.userRes.vo.UserResource;

/**
 * 
 * 用户资源信息调度器 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("userResourceFacade")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserResourceFacade extends AbsUserResourceService implements UserResourceService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String id;

	private String type;
	
	private Integer isLeaf;

	// 用户、资源ID集
	private List<String> ids;
	
	
	@Override
	public UserResourceFacade addIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
		return this;
	}

	@Override
	public UserResourceFacade addQueryId(String type, String id) {
		this.type = type;
		this.id = id;
		return this;
	}

	@Override
	public UserResourceFacade setObject(String type, String id, List<String> ids) {
		this.type = type;
		this.id = id;
		this.ids = ids;
		return this;
	}

	@Override
	public SysQueryListService<UserResource> queryUserResource() throws Exception {
		QueryListUserResource queryListUserResource = context.getBean(QueryListUserResource.class)
				.addIsLeaf(isLeaf);
		if (USER_ID.equals(type)) {
			queryListUserResource.addUserId(id);
		} else if (RESOURCE_ID.equals(type)) {
			queryListUserResource.addResourceId(id);
		} else {
			logger.warn("type参数错误！");
		}

		queryListUserResource.execute();
		return queryListUserResource;
	}

	@Override
	public BaseService saveUserResource() throws Exception {
		// 保存资源和用户关联信息
		SaveListUserResource saveListUserResource = context.getBean(SaveListUserResource.class);
		
		List<SysUserResource> lists = new ArrayList<>();// 存放要保存的关联表对象信息
		// 当ids不为空集合时给保存动作传参
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(URId -> {
				SysUserResource UResource = new SysUserResource();
				UResource.setId(UUIDUtil.getUUID());
				if (USER_ID.equals(type)) {
					// 根据用户ID保存关联的资源信息
					UResource.setResourceId(URId);
					UResource.setUserId(id);
				} else if (RESOURCE_ID.equals(type)) {
					// 根据资源ID保存关联的用户信息
					UResource.setResourceId(URId);
					UResource.setUserId(id);
				} else {
					logger.warn("保存时type参数错误！");
					return;
				}
				lists.add(UResource);
			});
			saveListUserResource.addUserResourceList(lists);
		}

		saveListUserResource.execute();
		return saveListUserResource;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService moveUserResource() throws Exception {
		RemoveListUserResource removeListUserResource = context.getBean(RemoveListUserResource.class);// 删除资源和用户关联信息
		SaveListUserResource saveUserResource = context.getBean(SaveListUserResource.class);// 保存资源和用户关联信息
		MoveListUserResource moveListUserResource = context.getBean(MoveListUserResource.class);// 修改关联表bean
		
		// 当ids为空时不做删除操作
		String id = this.ids == null ? null : this.id;
		if (USER_ID.equals(type)) {
			removeListUserResource.addUserId(id);
		} else if (RESOURCE_ID.equals(type)) {
			removeListUserResource.addResourceId(id);
		} else {
			logger.warn("删除时type参数错误！");
		}
		
		List<SysUserResource> lists = new ArrayList<>();// 存放要保存的关联表对象信息
		// 当ids不为空集合时给保存动作传参
		if (CollectionUtils.isNotEmpty(ids)) {
			ids.forEach(URId -> {
				SysUserResource UResource = new SysUserResource();
				UResource.setId(UUIDUtil.getUUID());
				if (USER_ID.equals(type)) {
					// 根据用户ID保存关联的资源信息
					UResource.setResourceId(URId);
					UResource.setUserId(id);
				} else if (RESOURCE_ID.equals(type)) {
					// 根据资源ID保存关联的用户信息
					UResource.setResourceId(URId);
					UResource.setUserId(id);
				} else {
					logger.warn("保存时type参数错误！");
					return;
				}
				lists.add(UResource);
			});
			saveUserResource.addUserResourceList(lists);
		}

		moveListUserResource.setSysRemoveService(removeListUserResource);
		moveListUserResource.setSysSaveService(saveUserResource);

		moveListUserResource.execute();
		return moveListUserResource;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseService removeUserResource() throws Exception {
		RemoveListUserResource removeListUserResource = context.getBean(RemoveListUserResource.class);
		if (USER_ID.equals(type)) {
			removeListUserResource.addUserId(id);
		} else if (RESOURCE_ID.equals(type)) {
			removeListUserResource.addResourceId(id);
		} else {
			logger.warn("type参数错误！");
		}

		removeListUserResource.execute();
		return removeListUserResource;
	}

}
