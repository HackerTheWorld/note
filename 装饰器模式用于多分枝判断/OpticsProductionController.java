import com.aacoptics.base.Constants;
import com.aacoptics.base.ResponseResult;
import com.aacoptics.base.ResponseResultGenerator;
import com.aacoptics.service.OpticsProductivityService;
import com.aacoptics.service.sfc.AssemblyTrendDataService;
import com.aacoptics.service.sfc.MtfDetailGroupDataService;
import com.aacoptics.service.sfc.MtfGroupedDataService;
import com.aacoptics.service.sfc.SfcService;
import com.aacoptics.targetflag.AssemblyTrendDataMethod;
import com.aacoptics.targetflag.MtfDetailGroupDataMethod;
import com.aacoptics.targetflag.MtfGroupedDataMethod;
import com.aacoptics.targetflag.SfcMethod;
import com.aacoptics.vo.AssemblyDetailedDataVo;
import com.aacoptics.vo.AssemblyTrendDataVo;
import com.aacoptics.vo.ComponentDetailVo;
import com.aacoptics.vo.ParetoDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.aacoptics.config.StringCodeStr.*;


public class OpticsProductionController {
	
	//将接口注入
    private final Map<String, SfcService> sfcInterMap;
	
	//注入的为所有实现此接口的类
    @Autowired
    public OpticsProductionController(List<SfcService> sfcInterListAuto){
        sfcInterMap = sfcInterListAuto.stream().collect(
                Collectors.toMap(sfcInter ->
                        Objects.requireNonNull(AnnotationUtils.findAnnotation(sfcInter.getClass(), SfcMethod.class)).sfcMethodName(),
                v -> v, (v1, v2) -> v1)
        );
    }


    @GetMapping(value = "/test")
    public ResponseResult getMouldPackageData (@RequestParam(name = "type")String type)                                      ) {

		//已自定义标签作为key
        List<Map<String,Object>> result = sfcInterMap.get(type).sfcFunction(projectMain,project,mouldNoSys, startTime, endTime, process,siteId);

    }

}

