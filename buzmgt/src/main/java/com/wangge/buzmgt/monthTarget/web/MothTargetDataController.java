package com.wangge.buzmgt.monthTarget.web;

import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import com.wangge.buzmgt.monthTarget.service.MothTargetDataService;
import com.wangge.buzmgt.util.ExcelExport;
import com.wangge.json.JSONFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private MothTargetDataService mothTargetDataService;

    @RequestMapping
    public String toJsp(){
        return "monthTarget/single_month";
    }


    @RequestMapping(value = "/mothTargetDatas",method = RequestMethod.GET)
    @ResponseBody
//    @JSONFormat(filterField = {"SalesMan.user","region.children"})
    public Page<MothTargetData> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam (value = "time", defaultValue = "")String time,
                                     @RequestParam (value = "name", defaultValue = "")String name
                                     ){

        Page pageResult = mothTargetDataService.getMothTargetDatas(name,time,page,size);


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
