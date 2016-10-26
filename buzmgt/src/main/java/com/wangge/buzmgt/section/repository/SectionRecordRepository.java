package com.wangge.buzmgt.section.repository;

import com.wangge.buzmgt.section.entity.SectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by joe on 16-9-28.
 */
public interface SectionRecordRepository extends JpaRepository<SectionRecord,Long> {

    public SectionRecord save(SectionRecord sectionRecord);
}
