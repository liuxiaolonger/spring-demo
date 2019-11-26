package com.longer.service.user.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.etoc.vo.StateInstance;
import com.longer.base.impl.SysMoveListService;
import com.longer.dao.model.SysUser;
import com.longer.service.user.AbsUserService;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MoveUserState extends AbsUserService implements SysMoveListService<StateInstance> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<StateInstance> stateIns;

	@Override
	public MoveUserState addObject(List<StateInstance> stateIns) {
		this.stateIns = stateIns;
		return this;
	}
	
	@Override
	public void execute() throws Exception {
		stateIns.forEach(user -> {
			SysUser Suser = new SysUser();
			Suser.setId(user.getId());
			Suser.setStatus(user.getState().name());

			userMapper.updateByPrimaryKeySelective(Suser);
		});

		logger.info("批量修改用户状态成功！");
	}


}
