package com.zone.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zone.backend.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
