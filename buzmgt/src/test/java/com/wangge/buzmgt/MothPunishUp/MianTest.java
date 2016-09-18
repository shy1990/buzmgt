package com.wangge.buzmgt.MothPunishUp;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;
import com.wangge.buzmgt.salesman.service.MonthPunishUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 神盾局 on 2016/5/21.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
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
   // @Test//分页测试
    public void test1(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 2, sort);
        Page<MonthPunishUp> page = mothPunishUpRepository.findAll(pageable);
        List<MonthPunishUp> list = page.getContent();
        for(MonthPunishUp p : list){
            System.out.println(p.getId());
        }

    }

    //@Test//条件查询
    public void test2() throws ParseException {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        String endTime = "2016-05-26";
        String startTime = "2016-04-29";

        Pageable pageable = new PageRequest(0, 2, sort);
        System.out.println(startTime+"    "+endTime);
        Page<MonthPunishUp> p = monthPunishUpService.findByPage(startTime,endTime,pageable);
        List<MonthPunishUp> list =  p.getContent();
        for(MonthPunishUp o:list){

            System.out.println("**:  "+o.getId());
        }
    }
    //@Test//条件查询
    public void test3(){
        Float a = mothPunishUpRepository.amerceSum();
        System.out.println(a);
    }
   // @Test//条件查询
    public void test4(){
        MonthPunishUp p = new MonthPunishUp();
        p.setCreateDate(new Date());
        p.setDebt(Float.parseFloat("777"));
        mothPunishUpRepository.save(p);
    }
   // @Test
    public void test5() throws ParseException {
       System.out.println("*****:  "+getString());
    }
    public static Date getDate(String time) throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        Date date = sf.parse(time);
        return date;
    }
    public static String getString() throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        String a = sf.format(new Date());
        return a;
    }
    
//    @Test
//    public   void pushTest(){
//  	  Map<String, Object> talMap = new HashMap<String, Object>();
//        talMap.put("orderNum", "15105314911");
//        talMap.put("accNum", "11");
//        talMap.put("username", "zhang1");
//        talMap.put("skuNum", "222");
//        talMap.put("amount", "333");
//        talMap.put("orderNum", "123456985");
//        talMap.put("memberMobile", "18700000001");
//        HttpUtil.sendPostJson("http://192.168.2.151:8082/v1/"+ "push/pushNewOrder", talMap);
//    }
}
