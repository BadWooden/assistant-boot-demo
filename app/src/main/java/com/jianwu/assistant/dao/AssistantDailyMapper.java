package com.jianwu.assistant.dao;

import com.jianwu.assistant.domain.AssistantDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yoush
 * @since 2019-04-11
 */
public interface AssistantDailyMapper extends BaseMapper<AssistantDaily> {
    List<Map<String, Object>> getDailyByDoctor(@Param("hospitalId") Long hospitalId, @Param("userId") Long userId);

    List<Map<String, Object>> getDailyType(@Param("hospitalId") Long hospitalId, @Param("userId") Long userId);
}
