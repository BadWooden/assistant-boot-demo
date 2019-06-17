package com.jianwu.assistant.manager;

import com.jianwu.assistant.domain.AssistantHospital;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yoush
 * @since 2019-04-03
 */
public interface AssistantHospitalManager extends IService<AssistantHospital> {

    List<AssistantHospital> getHospital(String key);
}
