package com.wangge.buzmgt.log.repository;

import com.wangge.buzmgt.log.entity.Log;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by barton on 16-6-7.
 */
public interface LogRepository extends CrudRepository<Log, Long> {
}
