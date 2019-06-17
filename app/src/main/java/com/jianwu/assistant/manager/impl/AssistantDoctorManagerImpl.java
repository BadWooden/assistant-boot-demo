package com.jianwu.assistant.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jianwu.assistant.domain.AssistantDoctor;
import com.jianwu.assistant.dao.AssistantDoctorMapper;
import com.jianwu.assistant.domain.AssistantHospital;
import com.jianwu.assistant.manager.AssistantDoctorManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yoush
 * @since 2019-04-11
 */
@Service
public class AssistantDoctorManagerImpl extends ServiceImpl<AssistantDoctorMapper, AssistantDoctor> implements AssistantDoctorManager {

    @Override
    public List<AssistantDoctor> getDoctor(Long hospitalId, Long userId) {
        QueryWrapper<AssistantDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().eq("hospital_id", hospitalId).eq("user_id", userId).eq("status", 1);
        return list(queryWrapper);
    }
}
