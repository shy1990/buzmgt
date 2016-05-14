package com.wangge.buzmgt.monthTask.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.repository.MonthOrdersDataRepository;
import com.wangge.buzmgt.util.DateUtil;

@Service

public class MonthTaskServiceImpl implements MonthTaskService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private MonthOrdersDataRepository monthRep;
	// 订单支付状态为1,计算上个月的订单情况
	public static final String lsdatasql = "select  r.registdata_id, r.region_id , o3.days, r.shop_name, '1' month \n"
			+ "  from (select count(1) days, o2.member_id\n" + "          from (select o1.member_id, o1.day\n"
			+ "                  from (select t.member_id,\n" + "                               t.CREATETIME,\n"
			+ "                               to_char(t.CREATETIME, 'yyyy-mm-dd') day,\n"
			+ "                               t.ship_Name\n"
			+ "                          from SJZAIXIAN.SJ_TB_ORDER t\n"
			+ "                         where to_char(t.createTime, 'yyyy-mm') =\n"
			+ "                               to_char(sysdate - interval '1' month, 'yyyy-mm')\n"
			+ "                           and t.pay_status = '1') o1\n"
			+ "                 group by o1.member_id, o1.day) o2\n" + "         group by o2.member_id\n"
			+ "         order by days desc) o3\n" + "  left join SJZAIXIAN.SJ_TB_members m on o3.member_id = m.id \n"
			+ "   inner join  sys_registdata r on r.member_id=m.id \n" + " where  r.region_id like '$town%' ";
	// 上月拜访访情况
	public static final String lsVisitSql = 
			"select count(1), tmp.month, tmp.registdata_id, tmp.region_id \n" +
					"  from (select substr(g.finish_time, 0, 7) month,\n" + 
					"               g.registdata_id,\n" + 
					"               g.region_id\n" + 
					"          from (select to_char(v.finish_time, 'yyyy-mm-dd') finish_time,\n" + 
					"                       r.registdata_id,\n" + 
					"                       r.region_id\n" + 
					"                  from sys_visit v\n" + 
					"                  left join sys_registdata r on v.registdata_id =\n" + 
					"                                                r.registdata_id\n" + 
					"                 where v.finish_time is not null\n" + 
					"                   and r.region_id like '$town%'\n" + 
					"                   and (to_char(v.finish_time, 'yyyy-mm') =\n" + 
					"                       to_char(sysdate - interval '1' month, 'yyyy-mm'))) g\n" + 
					"         group by g.finish_time, g.registdata_id, g.region_id) tmp\n" + 
					" group by tmp.month, tmp.registdata_id, tmp.region_id";


}
