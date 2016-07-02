package com.wangge.buzmgt.monthTarget.service;

import com.wangge.buzmgt.BuzmgtApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by joe on 16-7-1.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class MonthTargetServiceImplTest {
    @Autowired
    private MonthTargetService monthTargetService;
    @Test
    public void findByTargetCycleAndManagerId() throws Exception {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 3);

        monthTargetService.findByTargetCycleAndManagerId("2016-07","",pageable);
    }

    @Test
    public void findCount(){
//        Pageable pageable = new PageRequest(0, 3);
//        monthTargetService.findCount("2016-06","",pageable);
    }


}