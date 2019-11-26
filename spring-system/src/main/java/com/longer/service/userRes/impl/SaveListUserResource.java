package com.longer.service.userRes.impl;

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
import com.longer.dao.model.SysUserResource;
import com.longer.service.userRes.AbsUserResourceService;
import com.longer.service.userRes.vo.UserResource;

/**
 * 
 * 批量添加用户资源关联表 <功能详细描述>
 * 
 * @author liuxiaolong
 * @version [版本号, 2019年1月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("saveListUserResource")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SaveListUserResource extends AbsUserResourceService implements SysSaveService<List<UserResource>> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<SysUserResource> lists = new ArrayList<>();

	protected SaveListUserResource addUserResourceList(List<SysUserResource> lists) {
		this.lists = lists;
		return this;
	}

	@Override
	public void execute() throws Exception {
		if (CollectionUtils.isNotEmpty(lists)) {
			this.lists.forEach(UResource -> {
				sysUserResourceMapper.insertSelective(UResource);
			});
			logger.info("保存资源和用户的关联表成功。");
		}else {
			logger.warn("保存的资源和用户关联表为空@!!!");
		}
			
	}

	public List<UserResource> resultObj() throws Exception {
		List<UserResource> lists = new ArrayList<>();
		this.lists.forEach(UResource -> {
			try {
				UserResource userReso = transferObjectFields(UResource, UserResource.class, null);
				lists.add(userReso);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败！" + e);
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});

		return lists;
	}

}
