package com.linmour.system.controller.admin.dict;

import com.linmour.system.service.DictTypeService;
import com.linmour.common.dtos.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/dict")
public class DictController {

    @Resource
    private DictTypeService dictTypeService;

    @GetMapping("/getDictList")
    public Result getDictList(){
        return dictTypeService.getDictList();
    }
    @GetMapping("/getDict/{dictType}")
    public Result getDict(@PathVariable String dictType){
        return dictTypeService.getDict(dictType);
    }

}
