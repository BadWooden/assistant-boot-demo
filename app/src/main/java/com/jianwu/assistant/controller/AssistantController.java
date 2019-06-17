package com.jianwu.assistant.controller;


import com.jianwu.assistant.domain.*;
import com.jianwu.assistant.manager.AssistantBedManager;
import com.jianwu.assistant.manager.AssistantDailyManager;
import com.jianwu.assistant.manager.AssistantDoctorManager;
import com.jianwu.assistant.manager.AssistantHospitalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/assistant")
public class AssistantController extends BaseController {
    @Autowired
    AssistantHospitalManager assistantHospitalManager;
    @Autowired
    AssistantDoctorManager assistantDoctorManager;
    @Autowired
    AssistantDailyManager assistantDailyManager;
    @Autowired
    AssistantBedManager assistantBedManager;

    @RequestMapping(value = "/addDoctor", method = RequestMethod.POST, produces = "application/json")
    public ResultResponse addDoctor(AssistantDoctor doctor) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            doctor.setUserId((Long) resultResponse.getData());
            assistantDoctorManager.save(doctor);
        }
        return resultResponse;
    }


    @RequestMapping(value = "/addDaily", method = RequestMethod.POST, produces = "application/json")
    public ResultResponse addDaily(AssistantDaily daily) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            daily.setUserId((Long) resultResponse.getData());
            assistantDailyManager.save(daily);
        }
        return resultResponse;
    }

    @RequestMapping(value = "/addBed", method = RequestMethod.POST, produces = "application/json")
    public ResultResponse addBed(AssistantBed bed) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            bed.setUserId((Long) resultResponse.getData());
            assistantBedManager.save(bed);
        }
        return resultResponse;
    }

    @RequestMapping(value = "/getHospital", method = RequestMethod.GET, produces = "application/json")
    public ResultResponse getHospital(String key) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            List<AssistantHospital> list = assistantHospitalManager.getHospital(key);
            resultResponse.setData(list);
        }

        return resultResponse;
    }

    @RequestMapping(value = "/getDoctor", method = RequestMethod.GET, produces = "application/json")
    public ResultResponse getDoctor(Long hospitalId) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            List<AssistantDoctor> list = assistantDoctorManager.getDoctor(hospitalId, (Long) resultResponse.getData());
            resultResponse.setData(list);
        }
        return resultResponse;
    }


    @RequestMapping(value = "/getDailyByDoctor", method = RequestMethod.GET, produces = "application/json")
    public ResultResponse getDailyByDoctor(Long hospitalId) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            List<Map<String, Object>> list = assistantDailyManager.getDailyByDoctor(hospitalId, (Long) resultResponse.getData());
            resultResponse.setData(list);
        }
        return resultResponse;
    }

    @RequestMapping(value = "/getDailyType", method = RequestMethod.GET, produces = "application/json")
    public ResultResponse getDailyType(Long hospitalId) {
        ResultResponse resultResponse = checkLogin();
        if ("200".equals(resultResponse.getCode())) {
            List<Map<String, Object>> list = assistantDailyManager.getDailyType(hospitalId, (Long) resultResponse.getData());
            resultResponse.setData(list);
        }
        return resultResponse;
    }
}
