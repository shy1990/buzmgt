package com.wangge.buzmgt.salesman.service;

import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

/**
 * Created by 神盾局 on 2016/5/21.
 */
@Service
public class MonthPunishUpServiceImpl implements MonthPunishUpService{

    @Autowired
    private MothPunishUpRepository mothPunishUpRepository;
    @Override
    public Page<MonthPunishUp> findByPage( Pageable pageable) {

       Page<MonthPunishUp> page =  mothPunishUpRepository.findAll(pageable);

        System.out.println("########:  "+page.getContent().toString());

        return page;
    }

    public Page<MonthPunishUp> findByPage(String timeStart,String timeEnd, Pageable pageable) {
        if(timeStart != null &&"".equals(timeStart) && timeEnd != null && "".equals(timeEnd)){
            Specification<MonthPunishUp> specification1 = new Specification<MonthPunishUp>() {
                @Override
                public Predicate toPredicate(Root<MonthPunishUp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    Path<String> namePath = root.get("createDate");
                    Path<String> namePath1 = root.get("createDate");
//                    Predicate predicate =  cb.lessThan(root.get("createDate").as(String.class),"%"+timeStart+"%");
//                    query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%"));
                    query.where(cb.greaterThanOrEqualTo(namePath,"%"+timeStart+"%"),cb.lessThanOrEqualTo(namePath1,"%"+timeEnd+"%"));
                    return null;
                }
            };
            return  mothPunishUpRepository.findAll(specification1,pageable);
        }
        return mothPunishUpRepository.findAll(pageable);
    }
}
