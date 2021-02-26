package com.aacoptics.service.sfc.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.service.sfc.SfcService;
import com.test.targetflag.SfcMethod;
import com.test.util.SfcQueryUtil;

//自定义标签类
@Component
@SfcMethod(sfcMethodName = "anneal")
//定义接口实现方法
public class MouldAnnealDataImpl implements SfcService {

    @Autowired
    private SfcQueryUtil sfcQueryUtil;

    @Override
    public List<Map<String, Object>> sfcFunction(String projectMain, String project, String mouldNoSys, String startTime, String endTime, String component, Integer siteId) {
        component = component.replace("退火", "");
        return sfcQueryUtil.getProductivityComAnnealData(projectMain, project, mouldNoSys, startTime, endTime, component, siteId);
    }
}

