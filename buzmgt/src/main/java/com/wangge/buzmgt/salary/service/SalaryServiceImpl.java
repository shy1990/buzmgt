package com.wangge.buzmgt.salary.service;


import com.wangge.buzmgt.salary.entity.Salary;
import com.wangge.buzmgt.salary.repository.SalaryRespository;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by 神盾局 on 2016/6/13.
 */
@Service
public class SalaryServiceImpl implements SalaryService {
    private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";
    private final static String TIME_MIN = " 00:00:00 000";

    private final static String TIME_MAX = " 23:59:59 999";

    private static final Logger logger = Logger.getLogger(SalaryServiceImpl.class);
    @Autowired
    private SalaryRespository salaryRespository;
    @Override
    public Page<Salary> findByPage(Pageable pageable, String startTime, String endTime) {
        if(startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)){
            Specification<Salary> specification = new Specification<Salary>() {
                @Override
                public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Date time = null;
                    Date time1 = null;
                    try{
                        time = getDate(startTime+TIME_MIN);
                        time1 = getDate(endTime+TIME_MAX);
                        logger.info(time+"::::::::::"+time1);
                    }catch(ParseException e){
                        logger.info(e.getMessage());
                    }
                    Predicate predicate = cb.greaterThan(root.get("createTime").as(Date.class),time);//查询全部有"ce"
                    Predicate predicate1 = cb.lessThan(root.get("createTime").as(Date.class),time1);//查询全部有"ce"
                    Predicate p = cb.and(predicate,predicate1);
                    return p;
                }
            };
            return salaryRespository.findAll(specification,pageable);

        }

        return salaryRespository.findAll(pageable);
    }

    @Override
    public void save(Map<Integer, String> map) {
        map.forEach((Integer,s)->{
            String[] content = s.split("-->");
            logger.info("=====================");
            if(!"姓名".equals(content[0])){
                Salary salary= new Salary();
                logger.info(content[0]);
                logger.info(content[1]);
                logger.info(content[2]);
                logger.info(DateUtil.string2Date(content[3]));
                logger.info(content[4]);
                salary.setName(content[0]);
                salary.setSalary(Float.parseFloat(content[1]));
                salary.setTel(content[2]);
                salary.setCreateTime(DateUtil.string2Date(content[3]));
                salary.setMessage(content[4]);
                salaryRespository.save(salary);

            }
        });
        logger.info(map);
    }
    public static Date getDate(String time) throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sf.parse(time);
        return date;
    }
}
