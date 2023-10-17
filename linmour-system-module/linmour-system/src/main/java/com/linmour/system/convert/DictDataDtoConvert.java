package com.linmour.system.convert;

import com.linmour.system.pojo.Do.DictData;
import com.linmour.system.pojo.Dto.DictDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictDataDtoConvert {
    DictDataDtoConvert IN = Mappers.getMapper(DictDataDtoConvert.class);
    List<DictDataDto> convert(List<DictData> list);
}
