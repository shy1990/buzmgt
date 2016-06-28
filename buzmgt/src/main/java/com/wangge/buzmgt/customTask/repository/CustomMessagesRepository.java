package com.wangge.buzmgt.customTask.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.customTask.entity.CustomMessages;

public interface CustomMessagesRepository extends JpaRepository<CustomMessages, Long> {
	/**统计某一事件的业务员回复消息的总数(sum2)和已查看的数量(sum1)
	 * @param customtaskId
	 * @return
	 */
	@Query(value = "select t1.sum1, t2.sum2   from (select count(1) sum2 \n" + "          from sys_custom_message t\n"
			+ "         where t.customtask_id = ?1\n" + "           and t.roletype = 1) t2\n"
			+ "  left join (select count(1) sum1\n" + "               from sys_custom_message t \n"
			+ "              where t.customtask_id = ?1\n" + "                and t.status = 0\n"
			+ "                and t.roletype = 1) t1 on 1 = 1", nativeQuery = true)
	Object countByRoleType(Long customtaskId);

	@Query("select distinct m.salesmanId from CustomMessages m where m.customtaskId=?1 and m.roletype=1")
	Set<String> findByCustomtaskId(Long customtaskId);

	/**查询有某个事件下有消息记录的业务员并按状态排序
	 * @param customtaskId
	 * @return
	 */
	@Query(value = "select distinct m.salesman_id    from (select m.* \n"
			+ "            from sys_custom_message m           where m.customtask_id = ?1 \n"
			+ "      order by m.status desc) m ", nativeQuery = true)
	Set<String> findbyRoleType(Long customtaskId);

	/**通过任务id和业务id来查找message
	 * @param CustomtaskId
	 * @param salesmanId
	 * @return
	 */
	List<CustomMessages> findByCustomtaskIdAndSalesmanIdInOrderByTimeAsc(Long CustomtaskId, String[] salesmanId);
	@Transactional
	@Modifying
	@Query("update CustomMessages m set m.status=1 where m.id in ?1")
	int updateStatusById(List<Long> ids);
	/**查找某个事件一组消息最新的id;
	 * @param id
	 * @return
	 */
	@Query("select max(m.id) from CustomMessages m where m.customtaskId=?1")
	Object CountbyCustomtaskId(long id);
}
