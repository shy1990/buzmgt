package com.wangge.buzmgt.monthtarget.service;

import com.wangge.buzmgt.monthtarget.entity.MothTargetData;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by joe on 16-6-27.
 * 查询月任务个人区域商家总数据service
 */

@Service
public class MothTargetDataServiceImpl implements MothTargetDataService {

    private static final Logger logger = Logger.getLogger(MothTargetDataServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RegionService regionService;

    @Override
    /*
     * pageNum:当前页
     * limit：显示几条
     * */
    public Page<MothTargetData> getMothTargetDatas(String regionId, String name, String time, Integer page, Integer size) {

        String sql =
                "select t.phoneNum,t.shopName,t.regionId,nvl(sum(NUMS),0),nvl(count(1),0) count " + "from mothtargetdata t " +
                        "where " +
                        " to_char(createtime,'yyyy-mm') LIKE ? " +
                        " and t.parentid = ? " +
                        "group by " +
                        "t.phoneNum,t.shopName,t.regionId " +
                        "  order by nvl(sum(NUMS),0) desc,nvl(count(1),0) desc ";
        Query query = null;
        SQLQuery sqlQuery = null;
        int l = 0;
        int a = 1;
        int b = 2;
        if (name != null && !"".equals(name)) {
            sql = "select t.phoneNum,t.shopName,t.regionId,nvl(sum(NUMS),0),nvl(count(1),0) count from mothtargetdata t " +
                    " where to_char(createtime,'yyyy-mm') LIKE ? " +
                    " and t.shopName like ? " +
                    " and t.parentid = ? " +
                    " group by t.phoneNum,t.shopName,t.regionId " +
                    " order by nvl(sum(NUMS),0) desc,nvl(count(1),0) desc ";
            query = entityManager.createNativeQuery(sql);
            sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
            sqlQuery.setParameter(l, "%" + time + "%");//日期参数,必须存在
            sqlQuery.setParameter(a, "%" + name + "%");//商家名字参数
            sqlQuery.setParameter(b, regionId);//业务员id
        } else {
            query = entityManager.createNativeQuery(sql);
            sqlQuery = query.unwrap(SQLQuery.class);
            sqlQuery.setParameter(l, "%" + time + "%");//日期参数,必须存在
            sqlQuery.setParameter(a, regionId);
        }
        //根据日期查询
//        query = entityManager.createNativeQuery("select t.orderId,t.memberId,t.phoneNum,t.shopName,t.regionId,sum(NUMS),count(*) count from mothtargetdata t where to_char(createtime,'yyyy-mm-dd') LIKE ? group by t.memberid,t.orderId,t.memberId,t.phoneNum,t.shopName,t.regionId  ");
//        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        Page<MothTargetData> pageResult = null;
//        int l = 0;
//            sqlQuery.setParameter(l, "%2016-06%");//日期参数

        int count = sqlQuery.list().size();//分页查询出总条数(不是分页之后的)
        sqlQuery.setFirstResult(page * size);//设置开始位置
        sqlQuery.setMaxResults(size);//每页显示条数
        List<MothTargetData> mtdList = new ArrayList<>();
        List<Object[]> ret = sqlQuery.list();
        logger.info(count + "-------");
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(o -> {
                MothTargetData mtd = new MothTargetData();
//                mtd.setOrderId((String) o[0]);
//                mtd.setMemberId((String) o[1]);
                mtd.setRegionId((String) o[2]);
                mtd.setPhoneNmu((String) o[0]);
                mtd.setShopName((String) o[1]);
                Region region = regionService.getRegionById((String) o[2]);
//                logger.info(region);
//                mtd.setRegionName(regionName(region));
                mtd.setRegion(region);
                mtd.setNumsOne(((BigDecimal) o[3]).intValue());
                mtd.setCount(((BigDecimal) o[4]).intValue());
                mtd.setTime(time);
                mtdList.add(mtd);
            });
        }
        pageResult = new PageImpl<MothTargetData>(mtdList, new PageRequest(page, size), count);
        return pageResult;
    }

    @Override
    public List<MothTargetData> findAll(String regionid, String time) {
        String sql =
                "select t.phoneNum,t.shopName,t.regionId,nvl(sum(NUMS),0),nvl(count(1),0) count " + "from mothtargetdata t " +
                        "where " +
                        " to_char(createtime,'yyyy-mm') LIKE ? " +
                        " and t.parentid = ? " +
                        "group by " +
                        "t.phoneNum,t.shopName,t.regionId " +
                        "  order by nvl(sum(NUMS),0) desc,nvl(count(1),0) desc ";
        Query query = null;
        SQLQuery sqlQuery = null;
        int l = 0;
        int a = 1;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        sqlQuery.setParameter(l, "%" + time + "%");//日期参数,必须存在
        sqlQuery.setParameter(a, regionid);//业务员id
        List<MothTargetData> mtdList = new ArrayList<>();
        List<Object[]> ret = sqlQuery.list();
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(o -> {
                MothTargetData mtd = new MothTargetData();
                mtd.setRegionId((String) o[2]);
                mtd.setPhoneNmu((String) o[0]);
                mtd.setShopName((String) o[1]);
                Region region = regionService.getRegionById((String) o[2]);
                mtd.setRegion(region);
                mtd.setNumsOne(((BigDecimal) o[3]).intValue());
                mtd.setCount(((BigDecimal) o[4]).intValue());
                mtd.setTime(time);
                mtdList.add(mtd);
            });
        }
        return mtdList;
    }


    public static String regionName(Region region) {
//        COUNTRY("国"), PARGANA("大区"), PROVINCE("省"), AREA("区"), CITY("市"), COUNTY("县"), TOWN("镇"), OTHER("其他")
        String name = "";

        switch (region.getType()) {
            case COUNTRY:
                name = region.getName();
                break;

            case PARGANA:
                name = region.getParent().getName();
                name += region.getName();
                break;
            case PROVINCE:
                name = region.getParent().getParent().getName();
                name += region.getParent().getName();
                name += region.getName();
                break;
            case AREA:
                name = region.getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getName();
                name += region.getParent().getName();
                name += region.getName();
                break;
            case CITY:
                name = region.getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getName();
                name += region.getParent().getName();
                name += region.getName();
                break;
            case COUNTY:
                logger.info(region.getType());
                name = region.getParent().getParent().getParent().getParent().getParent().getName();
                logger.info(region.getId());
                name += region.getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getName();
                name += region.getParent().getName();
                name += region.getName();
                break;
            case TOWN:
                name = region.getParent().getParent().getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getName();
                name += region.getParent().getName();
                name += region.getName();
                break;
            case OTHER:
                name = region.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getParent().getName();
                name += region.getParent().getParent().getName();
                name += region.getParent().getName();
                name += region.getName();
                break;
        }


        return name;

    }
}
