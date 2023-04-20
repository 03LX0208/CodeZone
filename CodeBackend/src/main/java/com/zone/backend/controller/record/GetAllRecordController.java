package com.zone.backend.controller.record;

import com.zone.backend.pojo.Record;
import com.zone.backend.service.record.GetAllRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllRecordController {
    @Autowired
    private GetAllRecordService getAllRecordService;

    @GetMapping("/api/record/all/")
    public List<Record> getAllRecord() {
        return getAllRecordService.getAll();
    }
}
