package com.wangge.buzmgt.income.main.web;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.main.vo.HedgeVo;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.excel.ExcelImport;
import com.wangge.buzmgt.util.file.FileUtils;


@Controller
@RequestMapping("/hedge")
public class HedgeController {
  @Value("${lean.file.fileUploadPath}")
  private String fileUploadPath;
  @Autowired
  private HedgeService hedgeService;
  private static final String SEARCH_OPERTOR = "SC_";
  @RequestMapping("/index")
  public String goIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
    return "/income/main/hedge";
  }
  @RequestMapping("/getData")
  public @ResponseBody Page<HedgeVo> getPageVo(HttpServletRequest request, HttpServletResponse response,
      Pageable pageReq){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<HedgeVo> page = hedgeService.getVopage(pageReq, searchParams);
    return page;
    
  }
  /**
   * 文件上传
   * @param file
   * @return
   */

  @RequestMapping(value="/upload", method= RequestMethod.POST)
  public @ResponseBody
  JSONObject excelUpload(@RequestParam("file") MultipartFile file){
      LogUtil.info("======fileUploadPath:"+fileUploadPath);
      String fileRealPath = "";
      JSONObject jsonObject = new JSONObject();
      InputStream is;
      if (!file.isEmpty()) {
          try {
              //获得名字
              String fileName = file.getOriginalFilename();
              LogUtil.info("fileName:"+fileName);
              //获得名字后缀
              String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
              //判断格式是否正确
              if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
                  jsonObject.put("message","格式不对");
                  return jsonObject;
              }
              //缓存到服务器
//          ===============================================
              if (StringUtils.isEmpty(fileName)) {
                  jsonObject.put("message","名字为空");
                  return jsonObject;
              }
              is = file.getInputStream();
              //重新命名该文件
              fileName = FileUtils.reName(fileName);
              FileUtils.saveFile(fileName, is, fileUploadPath);
//         ===============================================
              fileRealPath = fileUploadPath+fileName;
              //读取文件中的内容
              Map<Integer, String> excelContent = ExcelImport.readExcelContent(fileRealPath);
              hedgeService.saveHedgeFromExcle(excelContent);
              LogUtil.info(excelContent.size()+"");
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
