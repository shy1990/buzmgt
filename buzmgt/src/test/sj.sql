select  count(1) from SJZAIXIAN.SJ_TB_ORDER t;

select o3.member_id, m.town,o3.days,m.username from
(select count(1) days,o2.member_id from 
(select  o1.member_id,o1.days from
(select  t.member_id,t.CREATETIME,to_char(t.CREATETIME,'yyyy-mm-dd') days,t.ship_Name from SJZAIXIAN.SJ_TB_ORDER t
 where to_char(t.createTime,'yyyy-mm')=to_char(sysdate - interval '1' month,'yyyy-mm') and t.pay_status='1') o1
group by o1.member_id,o1.days)o2 group by  o2.member_id order by days desc) o3 
left join SJZAIXIAN.SJ_TB_members m
 on o3.member_id=m.id
 where m.town='20001'

