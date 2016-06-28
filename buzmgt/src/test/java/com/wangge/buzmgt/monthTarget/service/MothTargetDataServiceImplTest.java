package com.wangge.buzmgt.monthTarget.service;

import com.wangge.buzmgt.BuzmgtApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by joe on 16-6-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class MothTargetDataServiceImplTest {
    @Autowired
    private MothTargetDataService mothTargetDataService;
    @Test
    public void getMothTargetDatas() throws Exception {
        mothTargetDataService.getMothTargetDatas("测试","2016-06",0,9);
    }

}