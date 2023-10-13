package com.linmour.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.system.convert.DictDataDtoConvert;
import com.linmour.system.mapper.DictDataMapper;
import com.linmour.system.mapper.DictTypeMapper;
import com.linmour.system.pojo.Do.DictData;
import com.linmour.system.pojo.Do.DictType;
import com.linmour.system.service.DictTypeService;
import com.linmour.common.dtos.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author linmour
* @description 针对表【system_dict_type】的数据库操作Service实现
* @createDate 2023-09-20 13:12:06
*/
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType>
    implements DictTypeService {

    @Resource
    private DictDataMapper dictDataMapper;
    @Override
    public Result getDictList() {
        List<DictData> dictData = dictDataMapper.selectList(null);
        return Result.success(DictDataDtoConvert.IN.convert(dictData));
    }

    @Override
    public Result getDict(String dictType) {
        return null;
    }
}




