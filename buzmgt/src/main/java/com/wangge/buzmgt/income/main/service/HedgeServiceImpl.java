package com.wangge.buzmgt.income.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.Hedge;
import com.wangge.buzmgt.income.main.repository.HedgeRepository;
import com.wangge.buzmgt.income.main.vo.HedgeVo;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.income.main.vo.repository.HedgeVoRepository;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class HedgeServiceImpl implements HedgeService {
  @Autowired
  private HedgeRepository hedgeRep;
  @Autowired
  private HedgeVoRepository hedgeVOrep;
  
  @Override
  public void saveHedgeFromExcle(Map<Integer, String> excelContent) throws Exception {
    List<Hedge> uList = new ArrayList<>();
    try {
      excelContent.forEach((key, val) -> {
        String[] vals = val.split("-->");
        Hedge hedge = new Hedge(vals[0], vals[1], vals[2], Integer.valueOf(vals[3]), DateUtil.string2Date(vals[4]),
            vals[5]);
        uList.add(hedge);
      });
      if (uList.size() > 0) {
        hedgeRep.save(uList);
      }
    } catch (Exception e) {
      LogUtil.error("导入售后冲减表出错", e);
      throw new Exception("导入售后冲减表出错");
    }
  }
  
  @Override
  public Page<HedgeVo> getVopage(Pageable pageReq, Map<String, Object> searchParams) {
    Page<HedgeVo> page = hedgeVOrep.findAll((Specification<HedgeVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    }, pageReq);
    return page;
  }
  
}
