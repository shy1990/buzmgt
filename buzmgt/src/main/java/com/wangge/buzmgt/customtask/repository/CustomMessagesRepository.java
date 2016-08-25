package com.wangge.buzmgt.customtask.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.customtask.entity.CustomMessages;

public interface CustomMessagesRepository extends JpaRepository<CustomMessages, Long> {
  /**
   * 统计某一事件的业务员回复消息的总数(sum2)和已查看的数量(sum1)
   * 
   * @param customtaskId
   * @return
   */
  @Query(value = "select t1.sum1, t2.sum2   from (select count(1) sum2 \n" + "          from sys_custom_message t\n"
      + "         where t.customtask_id = ?1\n" + "           and t.roletype = 1) t2\n"
      + "  left join (select count(1) sum1\n" + "               from sys_custom_message t \n"
      + "              where t.customtask_id = ?1\n" + "                and t.status = 0\n"
      + "                and t.roletype = 1) t1 on 1 = 1", nativeQuery = true)
  Object countByRoleType(long customtaskId);
  
  @Query("select distinct m.salesmanId from CustomMessages m where m.customtaskId=?1 and m.roletype=1")
  Set<String> findByCustomtaskId(long customtaskId);
  
  /**
   * 查询有某个事件下有消息记录的业务员并按状态排序
   * 
   * @param customtaskId
   * @return
   */
  @Query(value = "select t.*, 0   from (select distinct m.salesman_id          from sys_custom_message m\n"
      + "         where m.customtask_id = ?1      and m.roletype = 1       and m.status = 0) t\n"
      + "union\n" + "select t.*, 1  from (select distinct m.salesman_id\n"
      + "          from sys_custom_message m\n" + "         where m.customtask_id = ?1 \n"
      + "           and m.roletype = 1\n" + "           and m.status = 1) t", nativeQuery = true)
  Set<Object> findbyRoleType(Long customtaskId);
  
  /**
   * 通过任务id和业务id来查找message
   * 
   * @param CustomtaskId
   * @param salesmanId
   * @return
   */
  List<CustomMessages> findByCustomtaskIdAndSalesmanIdInOrderByTimeAsc(Long CustomtaskId, String[] salesmanId);
  
  @Transactional
  @Modifying
  @Query("update CustomMessages m set m.status=1 where m.id in ?1")
  int updateStatusById(List<Long> ids);
  
  /**
   * 查找某个事件一组消息最新的id;
   * 
   * @param id
   * @return
   */
  @Query("select max(m.id) from CustomMessages m where m.customtaskId=?1")
  Object CountbyCustomtaskId(long id);
}
