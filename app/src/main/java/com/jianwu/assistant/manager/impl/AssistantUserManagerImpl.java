package com.jianwu.assistant.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jianwu.assistant.domain.AssistantUser;
import com.jianwu.assistant.dao.AssistantUserMapper;
import com.jianwu.assistant.manager.AssistantUserManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianwu.assistant.utils.MD5Util;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yoush
 * @since 2019-04-03
 */
@Service
public class AssistantUserManagerImpl extends ServiceImpl<AssistantUserMapper, AssistantUser> implements AssistantUserManager {

    @Override
    public AssistantUser login(AssistantUser request) {
        QueryWrapper<AssistantUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().eq("phone", request.getPhone()).eq("pwd", request.getPwd()).eq("status", 1);
        return getOne(queryWrapper);
    }
}
