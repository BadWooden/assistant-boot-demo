package com.jianwu.assistant.manager.impl;

import com.google.common.collect.Lists;
import com.jianwu.assistant.domain.AssistantDaily;
import com.jianwu.assistant.dao.AssistantDailyMapper;
import com.jianwu.assistant.manager.AssistantDailyManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yoush
 * @since 2019-04-03
 */
@Service
public class AssistantDailyManagerImpl extends ServiceImpl<AssistantDailyMapper, AssistantDaily> implements AssistantDailyManager {

    @Override
    public List<Map<String, Object>> getDailyByDoctor(Long hospitalId, Long userId) {
        List<Map<String, Object>> result = getBaseMapper().getDailyByDoctor(hospitalId, userId);
        return buildData(result);
    }

    @Override
    public List<Map<String, Object>> getDailyType(Long hospitalId, Long userId) {
        List<Map<String, Object>> result = getBaseMapper().getDailyType(hospitalId, userId);
        return buildData(result);
    }


    public List<Map<String, Object>> buildData(List<Map<String, Object>> result) {
        result= fullData(result);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        for (Map<String, Object> temp : result) {
            String time = (String) temp.get("time");
            List<Map<String, Object>> l = map.get(time);
            if (l == null) {
                l = new ArrayList<>();
                map.put(time, l);
            }
            l.add(temp);
        }
        List<String> times = getBeforeSevenDay();
        for (String time : times) {
            List<Map<String, Object>> target = map.get(time);
            if (target == null) target = new ArrayList<>();
            Map<String, Object> value = new HashMap<>();
            value.put("time", time);
            value.put("list", target);
            list.add(value);
        }
        return list;
    }

    public List<Map<String, Object>> fullData(List<Map<String, Object>> result) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> name = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> temp : result) {
            map.put(temp.get("time").toString().concat("-").concat(temp.get("name").toString()), temp);
            name.put(temp.get("name").toString(), temp);
        }
        List<String> times = getBeforeSevenDay();
        for (String time : times) {
            for (Map.Entry<String, Object> entry : name.entrySet()) {
                String n = entry.getKey();
                Object object = map.get(time.concat("-").concat(n));
                if (object != null) {
                    list.add((Map<String, Object>) object);
                } else {
                    Map<String, Object> t = new HashMap<>();
                    t.put("time", time);
                    t.put("name", n);
                    t.put("number", 0);
                    list.add(t);
                }
            }
        }
        return list;
    }


    public List<String> getBeforeSevenDay() {
        List<String> days = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        days.add(sdf.format(c.getTime()));
        for (int i = 0; i < 6; i++) {
            c.add(Calendar.DAY_OF_MONTH, -1);
            days.add(sdf.format(c.getTime()));
        }
        return Lists.reverse(days);
    }


}
