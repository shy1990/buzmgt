package com.wangge.buzmgt.cash.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderCash.WaterPayStatusEnum;
import com.wangge.buzmgt.cash.service.BankTradeService;
import com.wangge.buzmgt.cash.service.WaterOrderCashService;
import com.wangge.buzmgt.util.excel.ExcelImport;
import com.wangge.buzmgt.util.file.FileUtils;

@Controller
@RequestMapping("/bankTrade")
public class BankTradeController {

  @Value("${buzmgt.file.fileUploadPath}")
  private String fileUploadPath;

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(BankTradeController.class);

  @Autowired
  private BankTradeService bankTradeService;
  @Autowired
  private WaterOrderCashService waterOrderCashService;

  @RequestMapping(value = "/toBankTrades")
  public String toBankTrades() {
    return "cash/bank_import";
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public Page<BankTrade> getCashList(HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = {
      "createDate", "id" }, direction = Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<BankTrade> bankTrades = bankTradeService.findAll(searchParams, pageable);
    
    return bankTrades;
  }

  /**
   * excel文件上传
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject upload(HttpServletRequest request, @RequestParam String importDate) {

    JSONObject jsonObject = new JSONObject();
    MultipartHttpServletRequest mReq;
    MultipartFile file;
    InputStream is;

    // 原始文件名称
    String fileName;
    
    String fileRealPath = "";
    try {

      // ============查询是否归档+是否已经审核账单。
      //重构查询条数
      long count=bankTradeService.countByIsArchiveAndImportDate(1 , importDate);
      // 已归档
      if (count > 0) {
        jsonObject.put("result", "failure");
        jsonObject.put("message", "已归档不能导入");
        return jsonObject;
      }

      // 已审核 
      //重构查询条数
      long cashCounts=waterOrderCashService.countByPayStatusAndCreateDate(WaterPayStatusEnum.OverPay, importDate);
      
      if (cashCounts > 0) {
        jsonObject.put("result", "failure");
        jsonObject.put("message", "已审核不能导入");
        return jsonObject;
      }

      mReq = (MultipartHttpServletRequest) request;

      // 获取文件
      file = mReq.getFile("file-input");

      // 取得文件的原始文件名称
      fileName = file.getOriginalFilename();

      logger.info("取得原始文件名:" + fileName);

      String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

      logger.info("原始文件的后缀名:" + suffix);

      if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
        jsonObject.put("result", "failure");
        jsonObject.put("message", "文件类型错误，请选择xlsx类型的文件");

        FileUtils.deleteFile(fileUploadPath + fileName);

        return jsonObject;
      }

      if (!StringUtils.isEmpty(fileName)) {
        is = file.getInputStream();

        fileName = FileUtils.reName(fileName);

        logger.info("重新命名后的文件名:" + fileName);

        FileUtils.saveFile(fileName, is, fileUploadPath);

        fileRealPath = fileUploadPath + fileName;

        // 文件上传完成后，读取文件
        Map<Integer, String> excelContent = ExcelImport.readExcelContent(fileRealPath);

        // ============读取文件完成后，导入到数据库=============
        Map<String,Object> searchParams=new HashMap<>();
        searchParams.put("EQ_importDate", importDate);
        List<BankTrade> bankTrades = bankTradeService.findAll(searchParams);
        searchParams.remove("EQ_importDate");
        if (bankTrades.size() > 0) {
          bankTradeService.delete(bankTrades);
        }
        // 保存。
        bankTradeService.save(excelContent, importDate);

        FileUtils.deleteFile(fileRealPath);

        jsonObject.put("result", "success");
      } else {
        throw new IOException("文件名为空!");
      }

    } catch (Exception e) {
      FileUtils.deleteFile(fileRealPath);
      jsonObject.put("result", "failure");
      jsonObject.put("message", "系统异常，稍后重试！");
      logger.info(e.getMessage());
      return jsonObject;

    }
    return jsonObject;

  }

}
