package com.jianwu.assistant.manager;

import com.jianwu.assistant.domain.AssistantUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yoush
 * @since 2019-04-03
 */
public interface AssistantUserManager extends IService<AssistantUser> {

    AssistantUser login(AssistantUser request);
}
