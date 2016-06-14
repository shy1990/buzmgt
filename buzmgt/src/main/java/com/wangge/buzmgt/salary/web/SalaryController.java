package com.wangge.buzmgt.salary.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.salary.entity.Salary;
import com.wangge.buzmgt.salary.service.SalaryService;
import com.wangge.buzmgt.util.excel.ExcelImport;
import com.wangge.buzmgt.util.file.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by 神盾局 on 2016/6/13.
 */
@Controller
@RequestMapping(value = "salary")
public class SalaryController {
    @Value("${lean.file.fileUploadPath}")
    private String fileUploadPath;
    @Autowired
    private SalaryService salaryService;
    private static final Logger logger = Logger.getLogger(SalaryController.class);
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String provideUploadInfo() {
        return "salary/salary";
    }

    @RequestMapping(value = "/salarys",method = RequestMethod.GET)
    @ResponseBody
    public Page<Salary> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size,
                                @RequestParam(value = "startTime", defaultValue = "2000-01-26")String startTime,
                                @RequestParam(value = "endTime", defaultValue = "2200-07-26") String endTime){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        return salaryService.findByPage(pageable,startTime,endTime);
    }


    /**
     * 文件上传
     * @param name
     * @param file
     * @return
     */

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody
    JSONObject excelUpload(@RequestParam("name") String name,
                           @RequestParam("file") MultipartFile file){
        logger.info("======fileUploadPath:"+fileUploadPath);
        String fileRealPath = "";
        JSONObject jsonObject = new JSONObject();
        InputStream is;
        if (!file.isEmpty()) {
            try {
                //获得名字
                String fileName = file.getOriginalFilename();
                logger.info("fileName:"+fileName);
                //获得名字后缀
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                //判断格式是否正确
                if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
                    jsonObject.put("message","格式不对");
                    return jsonObject;
                }
                //缓存到服务器
//            ===============================================
                if (StringUtils.isEmpty(fileName)) {
                    jsonObject.put("message","名字为空");
                    return jsonObject;
                }
                is = file.getInputStream();
                //重新命名该文件
                fileName = FileUtils.reName(fileName);
                FileUtils.saveFile(fileName, is, fileUploadPath);
//           ===============================================
                fileRealPath = fileUploadPath+fileName;
                //读取文件中的内容
                Map<Integer, String> excelContent = ExcelImport.readExcelContent(fileRealPath);
                logger.info(excelContent.size());
                salaryService.save(excelContent);
                //操作完成之后删除临时文件
                FileUtils.deleteFile(fileRealPath);
                jsonObject.put("message","上传成功");
                return jsonObject;
            } catch (Exception e) {
                jsonObject.put("message",e.getMessage());
                FileUtils.deleteFile(fileRealPath);
                return jsonObject;
            }
        } else {
            jsonObject.put("message","空文件");
            return jsonObject;
        }
    }
}
