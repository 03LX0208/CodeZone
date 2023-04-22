package com.zone.backend.service.impl.record;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zone.backend.mapper.RecordMapper;
import com.zone.backend.pojo.Record;
import com.zone.backend.service.record.GetAllRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GetAllRecordServiceImpl implements GetAllRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> getAll() {
        List<Record> res = null;
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("id", 0);
        res = recordMapper.selectList(queryWrapper);
        Collections.reverse(res);
        return res;
    }
}
