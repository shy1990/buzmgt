package com.wangge.buzmgt.salesman.service;

import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 神盾局 on 2016/5/21.
 */
@Service
public class MonthPunishUpServiceImpl implements MonthPunishUpService{
    private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

    private final static String TIME_MIN = " 00:00:00 000";

    private final static String TIME_MAX = " 23:59:59 999";

    @Autowired
    private MothPunishUpRepository mothPunishUpRepository;
    @Override
    public Page<MonthPunishUp> findByPage( Pageable pageable) {

       Page<MonthPunishUp> page =  mothPunishUpRepository.findAll(pageable);

        System.out.println("########:  "+page.getContent().toString());

        return page;
    }

    public Page<MonthPunishUp> findByPage(String timeStart,String timeEnd, Pageable pageable) {

        if(timeStart != null && !"".equals(timeStart) && timeEnd != null && !"".equals(timeEnd)){

            Specification<MonthPunishUp> specification1 = new Specification<MonthPunishUp>() {
                @Override
                public Predicate toPredicate(Root<MonthPunishUp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    String timeStartUp =   timeStart+TIME_MIN;
                    String timeEndUp =  timeEnd+TIME_MAX;
                    System.out.println("go if.....");
                    Date time = null;
                    Date time1 = null;
                    try {
                        time =  getDate(timeStartUp);
                        time1 = getDate(timeEndUp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Predicate predicate = cb.greaterThan(root.get("createDate").as(Date.class),time);//查询全部有"ce"
                    Predicate predicate1 = cb.lessThan(root.get("createDate").as(Date.class),time1);//查询全部有"ce"
                    Predicate p = cb.and(predicate,predicate1);
                    return p;
                }
            };
            return  mothPunishUpRepository.findAll(specification1,pageable);
        }
        return mothPunishUpRepository.findAll(pageable);
    }

    public static Date getDate(String time) throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        Date date = sf.parse(time);
        return date;
    }
}
