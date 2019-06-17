package com.jianwu.assistant.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jianwu.assistant.domain.AssistantHospital;
import com.jianwu.assistant.dao.AssistantHospitalMapper;
import com.jianwu.assistant.manager.AssistantHospitalManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yoush
 * @since 2019-04-03
 */
@Service
public class AssistantHospitalManagerImpl extends ServiceImpl<AssistantHospitalMapper, AssistantHospital> implements AssistantHospitalManager {

    @Override
    public List<AssistantHospital> getHospital(String key) {
        QueryWrapper<AssistantHospital> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().eq("status", 1).like("name",key);
        return list(queryWrapper);
    }
}
