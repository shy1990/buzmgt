package com.wangge.buzmgt.customtask.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.wangge.buzmgt.customtask.entity.CustomMessages;
import com.wangge.buzmgt.customtask.entity.CustomTask;
import com.wangge.buzmgt.customtask.repository.CustomMessagesRepository;
import com.wangge.buzmgt.customtask.repository.CustomTaskRepository;
import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.monthtask.entity.AppServer;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.HttpUtil;

@Service
public class CustomTaskServeImpl implements CustomTaskServer {
  @Autowired
  CustomTaskRepository customRep;
  @Autowired
  CustomMessagesRepository messageRep;
  @Autowired
  SalesManRepository salesmanRep;
  @Autowired
  MainIncomeService mainIncomeService;
  @Autowired
  MainIncomeRepository incomeRep;
  
  private Log log = LogFactory.getLog(this.getClass());
  
  public static final String[] TASKTYPEARR = new String[] { "注册", "售后", "扣罚", "拜访", "小米" };
  public static final String[] TASKTYPEDETAILARR = new String[] { "店铺注册", "售后处理", "扣罚通知", "客户拜访", "小米分销" };
  
  @Override
  public Map<String, Object> getList(String salesmanId, Pageable page) {
    return null;
  }
  
  /*
   * 保存自定义事件的同时将该事件推送到每个相关业务员手机上
   * 
   * @see com.wangge.buzmgt.customTask.server.CustomTaskServer#save(com.wangge.
   * buzmgt.customTask.entity.CustomTask)
   */
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public void save(CustomTask customTask) throws Exception {
    try {
      
      Collection<SalesMan> oldSet = customTask.getSalesmanSet();
      List<String> idList = new ArrayList<String>();
      for (SalesMan old : oldSet) {
        idList.add(old.getId());
      }
      List<SalesMan> newlist = salesmanRep.findAll(idList);
      customTask.setSalesmanSet(new HashSet<SalesMan>(newlist));
      customRep.save(customTask);
      
      // 计算扣罚金额,每次有就叠加
      if (customTask.getType() == 2) {
        for (String salesManId : idList) {
          MainIncome main = mainIncomeService.findIncomeMain(salesManId);
          main.setPunish(main.getPunish() + customTask.getPunishCount());
          incomeRep.save(main);
        }
        
      }
      
      // 开始推送操作
      String phone = "";
      for (SalesMan salesman : newlist) {
        phone += salesman.getMobile() + ",";
      }
      if (phone.length() > 3) {
        phone = phone.substring(0, phone.length() - 1);
      }
      Map<String, Object> talMap = new HashMap<String, Object>();
      talMap.put("mobiles", phone);
      talMap.put("msg", customTask.getTitle());
      talMap.put("Id", customTask.getId());
      HttpUtil.sendPostJson(AppServer.URL + "push/customTask", talMap);
    } catch (Exception e) {
      log.debug(e);
      e.printStackTrace();
      throw e;
    }
  }
  
  /*
   * SetJoin<CustomTask, SalesMan> setJion = root
   * .join(root.getModel().getDeclaredSet("salesmanSet", SalesMan.class),
   * JoinType.LEFT); 聚合属性查询,JoinType(inner,left,right);可以看成单个属性的操作
   * 
   * @see com.wangge.buzmgt.customTask.server.CustomTaskServer#findAll(org.
   * springframework.data.domain.Pageable, java.util.Map)
   */
  @Override
  public Map<String, Object> findAll(Pageable page, Map<String, Object> searchParams) {
    String salesName = searchParams.get("salesName") == null ? "" : searchParams.get("salesName").toString();
    searchParams.remove("salesName");
    Page<CustomTask> cpage = customRep.findAll((Specification<CustomTask>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      if (!salesName.isEmpty()) {
        SetJoin<CustomTask, SalesMan> setJion = root
            .join(root.getModel().getDeclaredSet("salesmanSet", SalesMan.class), JoinType.LEFT);
        predicates.add(cb.like(setJion.get("truename"), "%" + salesName + "%"));
      }
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      
      return cb.and(predicates.toArray(new Predicate[] {}));
    }, page);
    List<CustomTask> cList = cpage.getContent();
    Map<String, Object> allMap = new HashMap<String, Object>();
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    
    // 查完组装数据
    for (CustomTask task : cList) {
      Map<String, Object> datamap = new HashMap<String, Object>();
      datamap.put("id", task.getId());
      datamap.put("title", task.getTitle());
      datamap.put("type", task.getType());
      datamap.put("typeName", TASKTYPEARR[task.getType()]);
      datamap.put("content", task.getContent());
      datamap.put("time", DateUtil.date2String(task.getCreateTime(), "yyyy-MM-dd HH:mm"));
      Set<SalesMan> saleList = task.getSalesmanSet();
      String saleNames = "";
      for (SalesMan man : saleList) {
        saleNames += man.getTruename() + " ";
      }
      if (saleNames.length() > 1) {
        saleNames = saleNames.substring(0, saleNames.length() - 1);
      }
      datamap.put("salesMan", saleNames);
      getCustomRecieve(task, datamap, saleList);
      mapList.add(datamap);
    }
    allMap.put("content", mapList);
    allMap.put("size", cpage.getSize());
    allMap.put("totalElements", cpage.getTotalElements());
    return allMap;
  }
  
  /**
   * 获取单个customtask的recieve数据
   * 
   * @param task
   * @param datamap
   * @param saleList
   *          业务员列表
   * @throws NumberFormatException
   */
  private Object getCustomRecieve(CustomTask task, Map<String, Object> datamap, Set<SalesMan> saleList)
      throws NumberFormatException {
    int status = task.getStatus();
    if (status == 0) {
      datamap.put("recieve", "业务员未读");
    } else {
      datamap.put("recieve", "业务员已读");
    }
    if (saleList.size() > 0 && status == 1) {
      Object[] sum = (Object[]) messageRep.countByRoleType(task.getId());
      int allsum = Integer.parseInt(sum[1].toString());
      int unsum = Integer.parseInt(sum[0].toString());
      if (allsum == 1 && saleList.size() == 1 && unsum == 1) {
        datamap.put("recieve", "有回执");
      } else if (allsum == 1 && saleList.size() == 1 && unsum == 0) {
        datamap.put("recieve", "回执已读");
      } else if (saleList.size() > 1 || allsum > 1) {
        datamap.put("recieve", unsum + "/" + allsum);
      }
    }
    return datamap.get("recieve");
  }
  
  /*
   * 1.先查出有回执的业务员reset 2.查出所有业务员的allset 3.allset去掉rest里的元素得到无回执的业务员ids
   */
  @Override
  public void getSaleSet(CustomTask customTask, Model model) {
    Set<String> reSet = messageRep.findByCustomtaskId(customTask.getId());
    /*
     * 此处原为Set<SalesMan> salesmanSet = customTask.getSalesmanSet();
     * 结果操作salesmanSet结果自动保持久化了. 此bug;
     */
    Set<SalesMan> salesmanSet = new HashSet<SalesMan>(customTask.getSalesmanSet());
    List<SalesMan> reList = salesmanRep.findAll(reSet);
    salesmanSet.removeAll(reList);
    Set<SalesMan> unreSet = salesmanSet;
    
    model.addAttribute("reSet", reList);
    model.addAttribute("unreSet", unreSet);
    if (reSet.size() > 0) {
      String ids = "";
      for (String s : reSet) {
        ids += s + ",";
      }
      model.addAttribute("resalSet", ids.substring(0, ids.length() - 1));
    } else {
      model.addAttribute("resalSet", "null");
    }
    if (salesmanSet.size() > 0) {
      String ids = "";
      for (SalesMan s : salesmanSet) {
        ids += s.getId() + ",";
      }
      model.addAttribute("unresalSet", ids.substring(0, ids.length() - 1));
    } else {
      model.addAttribute("unresalSet", "null");
    }
  }
  
  /*
   * 功能:实现分页查询自定义事件中的业务员消息对话列表 1.查找分页中的业务员信息 2.根据业务员信息查相关消息 3.消息根据业务员分组
   * 4.组装处理分组的消息
   */
  @Override
  public Map<String, Object> getMessage(CustomTask customTask, Pageable pageReq) {
    
    Map<String, Object> returnMap = new HashMap<String, Object>();
    getCustomRecieve(customTask, returnMap, customTask.getSalesmanSet());
    
    // 1.得到所有有消息记录的业务员id(动态获取),2.自动根据size和page来获取分页的业务员
    Long customId = customTask.getId();
    Set<Object> saleArrSet1 = messageRep.findbyRoleType(customId);
    List<Object> reSet = new ArrayList<>(saleArrSet1);
    Collections.sort(reSet, (c1, c2) -> {
      Object[] man1 = (Object[]) c1;
      Object[] man2 = (Object[]) c2;
      return Integer.valueOf(man1[1].toString()) - Integer.valueOf(man2[1].toString());
    });
    // List<String> reSet = new ArrayList<>();
    // saleArrSet.forEach((c) -> {
    // reSet.add(((Object[]) c)[0].toString());
    // });
    int size = pageReq.getPageSize();
    int page = pageReq.getPageNumber();
    int startNum = size * page;
    int ArrSize = reSet.size() > size * (page + 1) ? size : reSet.size() - startNum;
    Object[] subSet = reSet.toArray(new Object[] {});
    String[] idArr = new String[ArrSize];
    for (int i = 0; i < ArrSize; i++) {
      idArr[i] = ((Object[]) subSet[startNum + i])[0].toString();
    }
    // 通过业务员来查找信息
    List<CustomMessages> msList = messageRep.findByCustomtaskIdAndSalesmanIdInOrderByTimeAsc(customId, idArr);
    List<Map<String, Object>> contList = new ArrayList<Map<String, Object>>();
    Map<String, List<CustomMessages>> remap = new HashMap<String, List<CustomMessages>>();
    // 用salesmanId(业务员Id)来聚合分组消息到remap
    for (CustomMessages message : msList) {
      message.setCtime();
      String saleid = message.getSalesmanId();
      List<CustomMessages> mList = remap.get(saleid);
      if (null == mList) {
        mList = new ArrayList<CustomMessages>();
      }
      mList.add(message);
      remap.put(saleid, mList);
    }
    
    // 处理分组,
    for (Map.Entry<String, List<CustomMessages>> entry : remap.entrySet()) {
      Map<String, Object> singleSaleMap = new HashMap<String, Object>();
      SalesMan man = salesmanRep.findById(entry.getKey());
      if (man == null) {
        continue;
      }
      List<CustomMessages> mesageList = entry.getValue();
      singleSaleMap.put("name", man.getTruename());
      singleSaleMap.put("salesId", entry.getKey());
      singleSaleMap.put("size", mesageList.size());
      singleSaleMap.put("unsize", countUnreadSize(mesageList));
      singleSaleMap.put("mesList", mesageList);
      contList.add(singleSaleMap);
    }
    // 拼接返回数据
    returnMap.put("content", contList);
    returnMap.put("totalElements", reSet.size());
    // 轮询用
    returnMap.put("newId", findlastId(customId));
    returnMap.put("size", size);
    returnMap.put("appUrl", AppServer.URL);
    return returnMap;
  }
  
  /**
   * 统计每个list中有多少条没有阅读的信息
   * 
   * @param mesageList
   * @return
   */
  private Object countUnreadSize(List<CustomMessages> mesageList) {
    List<Long> idList = new ArrayList<Long>();
    int i = 0;
    for (CustomMessages c : mesageList) {
      if (c.getRoletype() == 1 && c.getStatus() == 0) {
        i++;
        idList.add(c.getId());
      }
    }
    if (idList.size() > 0) {
      messageRep.updateStatusById(idList);
    }
    return i;
  }
  
  /*
   * 功能:1.支持多条聊天消息保存.2.支持消息推送到手机
   * 
   */
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public void saveMessage(Map<String, Object> messages) throws Exception {
    Long customtaskId = Long.parseLong(messages.get("customtaskId").toString());
    String content = messages.get("content").toString();
    @SuppressWarnings("unchecked")
    List<String> salesids = (List<String>) messages.get("salesmanId");
    List<CustomMessages> mlist = new ArrayList<CustomMessages>();
    for (String saleid : salesids) {
      CustomMessages message = new CustomMessages(customtaskId, saleid, content);
      mlist.add(message);
    }
    messageRep.save(mlist);
    
    // 推送到手机中
    Map<String, Object> talMap = new HashMap<String, Object>();
    List<SalesMan> salesList = salesmanRep.findAll(salesids);
    String phone = "";
    for (SalesMan man : salesList) {
      phone += man.getMobile() + ",";
    }
    talMap.put("mobiles", phone.substring(0, phone.length() - 1));
    talMap.put("msg", "您有新的自定义回复消息");
    talMap.put("Id", customtaskId);
    
    try {
      HttpUtil.sendPostJson(AppServer.URL + "push/customTask", talMap);
    } catch (Exception e) {
      e.printStackTrace();
      log.debug(e);
      throw e;
    }
  }
  
  /*
   * 通过前端不断轮询,得打最新的id,与前端js的对比,如果值大的话就重新加载前端的消息列表
   * 
   */
  @Override
  public Object findlastId(Long taskId) {
    Object id = messageRep.CountbyCustomtaskId(taskId);
    return null == id ? 0 : id;
  }
  
}
