package com.wangge.buzmgt.chartlist.web;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.chartlist.service.ChartListService;
import com.wangge.buzmgt.dto.ChartDto;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.JsonResponse;


@Controller
@RequestMapping("/chart")
public class ChartListController {
  
  @Autowired
  private ChartListService chartService;
  
  @Autowired
  private RegionService rs;
  
  @Autowired
  private ManagerService ms;
  
  @RequestMapping(value="/toChartList")
  public String toChart(@RequestParam(value="regionId",defaultValue="",required=false) String regionId,Model model,HttpServletRequest req){
    String rName = "";
    String rId = "";
    /*if(!StringUtils.isEmpty(regionId)){
       model.addAttribute("regionId", regionId);
    }else{}
    */
    if (null != regionId && !"".equals(regionId)) {
      Region r = rs.getRegionById(regionId);
      rName = r.getName();
      rId = r.getId();

      req.getSession().setAttribute("rName", rName);
      req.getSession().setAttribute("rId", rId);

      model.addAttribute("regionName", rName);
      model.addAttribute("regionId", rId);
    } else {
      Subject subject = SecurityUtils.getSubject();
      User user = (User) subject.getPrincipal();
      Manager manager = ms.getById(user.getId());
      if (null != req.getSession().getAttribute("rName")) {
        rName = req.getSession().getAttribute("rName") + "";
        rId = req.getSession().getAttribute("rId") + "";
      } else {
        rName = manager.getRegion().getName();
       // rId = manager.getRegion().getId();
      }
      model.addAttribute("regionName", rName);
      model.addAttribute("regionId", rId);
    }
    
    return "chart/chartList";
  }
  
  
  
  
  
  /**
   * 
    * getOutboundChart:(订单出库图表). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param date
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value="/outboundChart",method=RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getOutboundChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    
    try {
      ChartDto chartDto = chartService.getOutboundChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getOutboundChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getOutboundChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }
  /**
   * 
    * getCashChart:(收现金图表). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param regionId
    * @param date
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/cashChart", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getCashChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    try {
      ChartDto chartDto = chartService.getCashChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getCashChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getCashChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }
  
  
  /**
   * 
    * getRefusedChart:(拒收数据图表). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param regionId
    * @param date
    * @return 
    * @since JDK 1.8
   */
  
  @RequestMapping(value = "/refusedChart", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getRefusedChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    try {
      ChartDto chartDto = chartService.getRefusedChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getRefusedChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getRefusedChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }
  /**
   * 
    * getUnReportChart:(未报备未付款). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param regionId
    * @param date
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/unReportChart", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getUnReportChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    try {
      ChartDto chartDto = chartService.getunReportChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getUnReportChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getUnReportChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }
  
  /**
   * 
    * getUnReportChart:(未报备未付款). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param regionId
    * @param date
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/reportChart", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getReportChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    try {
      ChartDto chartDto = chartService.getReportChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getReportChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getReportChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }
  
  @RequestMapping(value = "/statementChart", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getStatementChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    try {
      ChartDto chartDto = chartService.getStatementChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getStatementChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getStatementChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }
  
  @RequestMapping(value = "/paidStatementChart", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse<ChartDto> getStatementAndPaidChart(
      @RequestParam(required = false, defaultValue = "", value = "regionId") String regionId,
      @RequestParam(required = false, defaultValue = "", value = "date") String date) {
    JsonResponse<ChartDto> result;
    try {
      ChartDto chartDto = chartService.getStatementAndPaidChart(regionId,date);
      result = new JsonResponse<ChartDto>(chartDto,true);
    } catch (ParseException e) {
      LogUtil.info("getStatementAndPaidChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("日期类型转换错误 : " + e.getMessage(), false);
    }catch (Exception e) {
      LogUtil.info("getStatementAndPaidChart : " + e.getMessage());
      result = new JsonResponse<ChartDto>("获取数据异常 : " + e.getMessage(), false);
    }
    
    return result;
  }

}
