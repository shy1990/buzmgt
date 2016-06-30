package com.wangge.buzmgt.monthTarget.web;

import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import com.wangge.buzmgt.monthTarget.service.MothTargetDataService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.ExcelExport;
import com.wangge.json.JSONFormat;
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
    @RequestMapping(value = "/{regionId}/{time}",method = RequestMethod.GET)
    public String toJsp(@PathVariable String regionId,@PathVariable String time,Model model){
        logger.info(regionId+" ======= "+time);
        model.addAttribute("regionId",regionId);
        model.addAttribute("time",time);
        return "monthTarget/single_month";
    }


    @RequestMapping(value = "/mothTargetDatas",method = RequestMethod.GET)
    @ResponseBody
//    @JSONFormat(filterField = {"SalesMan.user","region.children"})
    public Page<MothTargetData> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam (value = "time", defaultValue = "")String time,
                                     @RequestParam (value = "name", defaultValue = "")String name,
                                     @RequestParam (value = "regionId",defaultValue = "") String regionId//限定为当前业务员
                                     ){
        logger.info("============== ");
        Page pageResult = mothTargetDataService.getMothTargetDatas(regionId,name,time,page,size);


        return pageResult;
    }


    /**
     * 导出表
     * @return
     */
    @RequestMapping(value = "export/{time}")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,@PathVariable String time){
        List<MothTargetData> list = mothTargetDataService.findAll(time);

        System.out.println(list.size());
//        list.forEach(MonthPunishUp ->{
//            MonthPunishUp.setRegionName(MonthPunishUp.getSalesMan().getRegion().getName());
//        });
        String[] title_ = new String[]{ "商铺名","电话","区域","提货量", "提货次数", "时间"};
        String[] coloumsKey_ = new String[]{"shopName","phoneNmu","regionName","numsOne","count","time"};
        ExcelExport.doExcelExport("月指标.xls",list,title_,coloumsKey_,request,response);
    }

}
