package com.wangge.buzmgt.MothPunishUp;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;
import com.wangge.buzmgt.salesman.service.MonthPunishUpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by 神盾局 on 2016/5/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class MianTest {
    private static final Logger logger = LoggerFactory
            .getLogger(MianTest.class);
    @Autowired
    private MothPunishUpRepository mothPunishUpRepository;
    @Autowired
    private MonthPunishUpService monthPunishUpService;







//
//    @Test//查询全部
//    public void testFindAll(){
//        List<MonthPunishUp> list = mothPunishUpRepository.findAll();
////        for(MonthPunishUp o : list){
////            System.out.println("*****: "+o.getUser().getSalseMan().getRegion().getType());
////        }
//        MonthPunishUp monthPunishUp = list.get(1);
//       Region region =  monthPunishUp.getUser().getSalseMan().getRegion();
//       Region.RegionType type= region.getType();
//        String name = "";
//        List<String> listName = new ArrayList<String>();
//        System.out.println(type);
//        switch (type){
//            case COUNTRY:
//                System.out.println(1);
//                break;
//            case PARGANA:
//                System.out.println(2);
//                break;
//            case PROVINCE:
//                System.out.println(3);
//                break;
//            case AREA:
//                System.out.println(4);
//                Region regionTest = region;
//
//                for(int i =1;i<=4;i++){
//                    name += regionTest.getName();
//                    regionTest = regionTest.getParent();
////                    listName.a
//                }
//                System.out.println(name);
//                break;
//            case CITY:
//                System.out.println(5);
//                break;
//            case COUNTY:
//                System.out.println(6);
//                break;
//            case TOWN:
//                System.out.println(7);
//                break;
//            case OTHER:
//                System.out.println(8);
//                break;
//        }
//
// }
    @Test//分页测试
    public void test1(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 2, sort);
        Page<MonthPunishUp> page = mothPunishUpRepository.findAll(pageable);
        List<MonthPunishUp> list = page.getContent();
        for(MonthPunishUp p : list){
            System.out.println(p.getId());
        }

    }

    @Test//条件查询
    public void test2(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        String startTime = "01-5月 -16 07.24.00.996000000 上午";
        String endTime = "02-5月 -16 07.24.00.996000000 上午";
        Pageable pageable = new PageRequest(0, 2, sort);
        Page<MonthPunishUp> p = monthPunishUpService.findByPage(startTime,endTime,pageable);
        List<MonthPunishUp> list =  p.getContent();
        for(MonthPunishUp o:list){

            System.out.println(o.getId());
        }
    }
    @Test//条件查询
    public void test3(){
        Float a = mothPunishUpRepository.amerceSum();
        System.out.println(a);
    }

}
