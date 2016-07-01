package com.wangge.buzmgt.monthTarget.service;

import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by joe on 16-6-27.
 * 查询月任务个人区域商家总数据service
 */

@Service
public class MothTargetDataServiceImpl implements MothTargetDataService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RegionService regionService;

    @Override
    /*
     * pageNum:当前页
     * limit：显示几条
     * */
    public Page<MothTargetData> getMothTargetDatas(String time,Integer page,Integer size) {
        //根据日期查询
        Query query = entityManager.createNativeQuery("select t.orderId,t.memberId,t.phoneNum,t.shopName,t.regionId,sum(NUMS),count(*) count from mothtargetdata t where to_char(createtime,'yyyy-mm-dd') LIKE ? group by t.memberid,t.orderId,t.memberId,t.phoneNum,t.shopName,t.regionId  ");
        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        Page<MothTargetData> pageResult = null;
        int l = 0;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
            Date d = simpleDateFormat.parse("2016-06-16");
            sqlQuery.setParameter(l, "%2016-06%");//日期参数
            sqlQuery.setFirstResult(page * size);
            sqlQuery.setMaxResults(size);
            List<MothTargetData> mtdList = new ArrayList<>();
            List<Object[]> ret = sqlQuery.list();
            int count = sqlQuery.list().size();//分页查询出总条数
            System.out.println(count+"-------");
            if (CollectionUtils.isNotEmpty(ret)) {
                ret.forEach(o -> {
                    MothTargetData mtd = new MothTargetData();
                    mtd.setOrderId((String)o[0]);
                    mtd.setMemberId((String)o[1]);
                    mtd.setRegionId((String)o[4]);
                    mtd.setPhoneNmu((String)o[2]);
                    mtd.setShopName((String) o[3]);
                    Region region = regionService.findListRegionbyid((String)o[4]);
                    mtd.setRegion(region);
                    mtd.setNumsOne((BigDecimal) o[5]);
                    mtd.setCount((BigDecimal)o[6]);
                    mtdList.add(mtd);
                });
            }
            pageResult = new PageImpl<MothTargetData>(mtdList,new PageRequest(page,size),count);
            System.out.println(pageResult+"******");
//            mtdList.forEach(mtd -> {
//                System.out.println(mtd);
//            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return pageResult;
    }
}
