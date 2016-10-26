package com.wangge.buzmgt.monthtarget.web;

import com.wangge.buzmgt.monthtarget.entity.MonthTarget;
import com.wangge.buzmgt.monthtarget.service.MonthTargetService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.excel.ExcelExport;
import com.wangge.json.JSONFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/monthtarget")
@Controller
public class MonthTargetController {
  private static final Logger logger = Logger.getLogger(MonthTargetController.class);
  @Autowired
  private SalesManService smService;
  @Autowired
  private MonthTargetService mtService;
  @Autowired
  private RegionService regionService;

  /**
   * 跳转到月指标
   * @return
   */
  @RequestMapping("/monthtarget")
  public String toMonthTarget(){

    return "monthtarget/mouth_target";
  }

  /**
   * 跳转到月指标设置
   * @return
   */
  @RequestMapping("/monthSetting")
  public String toMonthSetting(){

    return "monthtarget/mouth_setting";
  }

  /**
   * 跳转到月指标添加和修改
   * @param flag
   * @param id
   * @param model
   * @return
   */
  @RequestMapping(value = "/toUpdate")
  public String toUpdate(String flag,Long id,Model model){
    Region region = mtService.getRegion();
    if(!"update".equals(flag)){
      Set<SalesMan> salesSet = new HashSet<SalesMan>();
      salesSet.addAll(smService.findForTargetByReginId(region.getId()));
      model.addAttribute("salesList", salesSet);
    }
    model.addAttribute("region", region);
    model.addAttribute("flag",flag);
    model.addAttribute("id",id);
    return "monthtarget/update";
  }

  /**
   * 根据regionId查询业务
   * @param regionId
   * @return
   */
  @RequestMapping("/regionName")
  @JSONFormat(filterField = {"SalesMan.user","region.children","region.coordinates"})
  public SalesMan getRegionName(String regionId){
    SalesMan sm = smService.findByRegionAndisPrimaryAccount(regionService.getRegionById(regionId));
    return sm;
  }

  /**
   * 根据regionId查询订单量数据
   * @param regionId
   * @return
   */
  @RequestMapping(value = "/orderNum",method = RequestMethod.GET)
  @ResponseBody
  public Map<String,Object> orderNum(String regionId){
    Map<String,Object> map = mtService.getOrderNum(regionId);
    return map;
  }

  /**
   * 根据regionId查询提货商家
   * @param regionId
   * @return
   */
  @RequestMapping(value = "/seller",method = RequestMethod.GET)
  @ResponseBody
  public Map<String,Object> seller(String regionId){
    Map<String,Object> map = mtService.getSeller(regionId);
    return map;
  }

  /**
   * 根据regionId查询活跃商家和成熟商家
   * @param regionId
   * @return
   */
  @RequestMapping(value = "/merchant",method = RequestMethod.GET)
  @ResponseBody
  public Map<String,Object> merchant(String regionId){
    Map<String,Object> map = mtService.getMerchant(regionId);
    return map;
  }

  /**
   * 根据regionId保存月指标
   * @param mt
   * @return
   */
  @RequestMapping(value = "/save/{regionId}",method = {RequestMethod.POST})
  @ResponseBody
  public String save(@RequestBody MonthTarget mt,@PathVariable("regionId") Region region){
    String msg = mtService.save(mt,region);
    return msg;
  }

  /**
   * 根据id修改月指标
   * @param mt
   * @param monthTarget
   * @return
   */
  @RequestMapping(value = "/update/{id}",method = {RequestMethod.POST})
  @ResponseBody
  public String update(@RequestBody MonthTarget mt,@PathVariable("id") MonthTarget monthTarget){
    monthTarget.setActiveNum(mt.getActiveNum());
    monthTarget.setOrderNum(mt.getOrderNum());
    monthTarget.setMerchantNum(mt.getMerchantNum());
    monthTarget.setMatureNum(mt.getMatureNum());
    String msg = mtService.save(monthTarget);
    return msg;
  }

  /**
   * 根据月指标id发布一条月指标
   * @param monthTarget
   * @return
   */
  @RequestMapping(value = "/publish/{id}",method = {RequestMethod.GET})
  @ResponseBody
  public String publish(@PathVariable("id") MonthTarget monthTarget){
    String msg = mtService.publish(monthTarget);
    return msg;
  }

  /**
   * 发布当前管理员保存的全部业务员指标
   * @return
   */
  @RequestMapping(value = "/publishAll",method = RequestMethod.POST)
  @ResponseBody
  public String publishAll(){
    String msg = mtService.publishAll();
    return msg;
  }

  /**
   * 按条件(业务姓名、指标周期)查询全部的月指标并分页
   * @param targetCycle
   * @param userName
   * @param pageRequest
   * @return
   */
  @RequestMapping(value = "/findMonthTarget",method = RequestMethod.GET)
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public Page<MonthTarget> findMonthTarget(String targetCycle,String userName,
                                           @PageableDefault(page = 0,size=20,sort={"orderNum"},direction= Sort.Direction.DESC) Pageable pageRequest){
    Page<MonthTarget> page = mtService.findAll(targetCycle,userName,pageRequest);
    return page;
  }




  /**
   * 根据时间与区域经理id查询 全部的业务员信息
   *
   * 不用这个
   * @return
   */
  @RequestMapping(value = "/ceshi",method = RequestMethod.GET)
//  @ResponseBody
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public  Page<MonthTarget> findByTargetCycleAndManagerId(@RequestParam String time,
                                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                          @RequestParam (value = "name", defaultValue = "")String truename
  ){
    Sort sort = new Sort(Sort.Direction.DESC,"id");
    Pageable pageable = new PageRequest(page, size,sort);
    Page<MonthTarget> requestPage = mtService.findByTargetCycleAndManagerId(truename,time,pageable);

    return requestPage;
  }

  /**
   * 根据时间与区域经理id查询 全部的业务员信息
   * **/
  @RequestMapping(value = "/monthTargets",method = RequestMethod.GET)
//  @ResponseBody
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public Page<MonthTarget> listAll(@RequestParam String time,
                                   @RequestParam (value = "name", defaultValue = "")String name,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size
  ){
    logger.info("*******************************************");
//    time = "2016-07";
//    page = 0;
//    size = 20;

    Page<MonthTarget> requestPage = mtService.findAllBySql(page,size,name,time);


    return requestPage;
  }

  @RequestMapping(value = "/export")
  public void exportExcel(HttpServletRequest request,HttpServletResponse response,String time){
//    time = "2016-08";
    List<MonthTarget> list = mtService.exportExcel(time);
    String[] title_ = new String[]{"区域","负责人","注册商家","提货量(实际)","提货量(指标)","提货商家(实际)","提货商家(指标)","活跃商家(实际)","活跃商家(指标)","成熟商家(实际)","成熟商家(指标)","指标周期"};
    String[] coloumsKey_ = new String[]{"region.name","trueName","matureAll","order","orderNum","merchant","merchantNum","active","activeNum","mature","matureNum","targetCycle"};
    ExcelExport.doExcelExport("月指标.xls",list,title_,coloumsKey_,request,response);
  }



}
