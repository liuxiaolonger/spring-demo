package com.etoc.service.group.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.base.impl.SysMoveListService;
import com.etoc.exception.ChannelException;
import com.etoc.model.SysUserGroup;
import com.etoc.service.group.AbsUserGroupService;
import com.etoc.vo.StateInstance;

/**
 * 
 * 修改用户状态
 * 
 * @author chenzhi
 * @version [版本号, 2018年12月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUserGoupsState extends AbsUserGroupService implements SysMoveListService<StateInstance> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<StateInstance> stateIns;

	@Override
	public MoveUserGoupsState addObject(List<StateInstance> stateIns) {
		this.stateIns = stateIns;
		return this;
	}

	@Override
	public void execute() throws Exception {
		stateIns.forEach(state -> {
			try {
				SysUserGroup userGroup = transferObjectFields(state, SysUserGroup.class, null);
				userGroupMapper.updateGroup(userGroup);
			} catch (Exception e) {
				logger.error("数据库对象转化为VO对象失败！" + e );
				throw new ChannelException("数据库对象转化为VO对象失败！", e);
			}
		});

	}

}
