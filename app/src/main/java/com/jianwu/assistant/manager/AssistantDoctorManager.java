package com.jianwu.assistant.manager;

import com.jianwu.assistant.domain.AssistantDoctor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yoush
 * @since 2019-04-11
 */
public interface AssistantDoctorManager extends IService<AssistantDoctor> {

    List<AssistantDoctor> getDoctor(Long hospitalId,Long userId);
}
