package com.wangge.buzmgt.monthTarget.web;

import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import com.wangge.buzmgt.monthTarget.service.MothTargetDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by joe on 16-6-27.
 *
 */
@Controller
@RequestMapping("/mothTargetData")
public class MothTargetDataController {
    @Autowired
    private MothTargetDataService mothTargetDataService;
    @RequestMapping(value = "/mothTargetDatas",method = RequestMethod.GET)
    @ResponseBody
    public Page<MothTargetData> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size,
                                     @RequestParam (value = "time", defaultValue = "2016-06")String time
                                     ){

        Page pageResult = mothTargetDataService.getMothTargetDatas(time,page,size);


        return pageResult;
    }

}
