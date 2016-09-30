package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.entity.SectionRecord;
import com.wangge.buzmgt.section.repository.SectionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by joe on 16-9-28.
 */
@Service
public class SectionRecordServiceImpl implements SectionRecordService  {

    @Autowired
    private SectionRecordRepository sectionRecordRepository;
    @Override
    public SectionRecord save(SectionRecord sectionRecord) {

        SectionRecord s = sectionRecordRepository.save(sectionRecord);
        return s;
    }
}
