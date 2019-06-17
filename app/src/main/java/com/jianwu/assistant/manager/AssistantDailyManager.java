package com.jianwu.assistant.manager;

import com.jianwu.assistant.domain.AssistantDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yoush
 * @since 2019-04-11
 */
public interface AssistantDailyManager extends IService<AssistantDaily> {

    List<Map<String, Object>> getDailyByDoctor(Long hospitalId,Long userId);

    List<Map<String, Object>> getDailyType(Long hospitalId,Long userId);
}
