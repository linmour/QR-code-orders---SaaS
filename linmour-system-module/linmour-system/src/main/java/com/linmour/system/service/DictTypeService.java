package com.linmour.system.service;

import com.linmour.system.pojo.Do.DictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmour.common.dtos.Result;

/**
* @author linmour
* @description 针对表【system_dict_type】的数据库操作Service
* @createDate 2023-09-20 13:12:06
*/
public interface DictTypeService extends IService<DictType> {

    Result getDictList();

    Result getDict(String dictType);
}
