package com.wangge.buzmgt.monthTarget.web;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import com.wangge.buzmgt.monthTarget.service.MothTargetDataService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.excel.ExcelExport;
import com.wangge.json.JSONFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by joe on 16-6-27.
 *
 */
@Controller
@RequestMapping("/mothTargetData")
public class MothTargetDataController {

    private static final Logger logger = Logger.getLogger(MothTargetDataController.class);
    @Autowired
    private MothTargetDataService mothTargetDataService;

    //    @RequestMapping(value = "/{regionId}/{time}/{order}/{orderNum}/{active}/{activeNum}",method = RequestMethod.GET)
    @RequestMapping(method = RequestMethod.GET)
    public String toJsp(String regionId,
                        String time,
                        String order,//实际提货量
                        String orderNum,//指标提货量
                        String merchant,//实际提货商家
                        String merchantNum,//指标提货商家
                        String active,//实际活跃商家
                        String activeNum,//指标活跃商家
                        String mature,//实际成熟商家
                        String matureNum,//指标成熟商家
                        Model model) {
        logger.info(regionId + " ======= " + time + "=====" + order + "====" + orderNum + "===" + merchant + "===" + merchantNum + "===" + active + "===" + activeNum + "===" + mature + "===" + matureNum);
        model.addAttribute("regionId", regionId);
        model.addAttribute("time", time);
        model.addAttribute("order", order);
        model.addAttribute("orderNum", orderNum);
        model.addAttribute("merchant", merchant);
        model.addAttribute("merchantNum", merchantNum);
        model.addAttribute("active", active);
        model.addAttribute("activeNum", activeNum);
        model.addAttribute("mature", mature);
        model.addAttribute("matureNum", matureNum);

        return "monthTarget/single_month";
    }

    @RequestMapping(value = "/mothTargetDatas", method = RequestMethod.GET)
    @ResponseBody
//    @JSONFormat(filterField = {"SalesMan.user","region.children"})
    public Page<MothTargetData> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam(value = "time", defaultValue = "") String time,
                                     @RequestParam(value = "name", defaultValue = "") String name,
                                     @RequestParam(value = "regionId", defaultValue = "") String regionId//限定为当前业务员
    ) {
        logger.info("regionId======= " + regionId + "  name==== " + name);
//        regionId = "37";
        Page pageResult = mothTargetDataService.getMothTargetDatas(regionId, name, time, page, size);
        return pageResult;
    }


    /**
     * 导出表
     *
     * @return
     */
    @RequestMapping(value = "export/{time}")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable String time) {
        List<MothTargetData> list = mothTargetDataService.findAll(time);

        System.out.println(list.size());
//        list.forEach(MonthPunishUp ->{
//            MonthPunishUp.setRegionName(MonthPunishUp.getSalesMan().getRegion().getName());
//        });
        String[] title_ = new String[]{"商铺名", "电话", "区域", "提货量", "提货次数", "时间"};
        String[] coloumsKey_ = new String[]{"shopName", "phoneNmu", "regionName", "numsOne", "count", "time"};
        ExcelExport.doExcelExport("月指标.xls", list, title_, coloumsKey_, request, response);
    }
}