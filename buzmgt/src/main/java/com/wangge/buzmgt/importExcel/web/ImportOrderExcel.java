package com.wangge.buzmgt.importExcel.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.util.ExcelUtil;
import com.wangge.buzmgt.util.FileUtil;
import com.wangge.buzmgt.util.UploadUtil;
import com.wangge.buzmgt.util.WdwUtil;


@Controller
@RequestMapping(value = "/import")
public class ImportOrderExcel {
  
  private static final Logger logger = Logger.getLogger(ImportOrderExcel.class);
  
  @Value("${buzmgt.file.fileUploadPath}")
  private String fileUploadDir;
  
  //private static String url = "http://image.3j1688.com/uploadfile/"; // 外网正式环境
  
  @Resource
  private OrderSignforService orderSignforService;
  
  @RequestMapping(value = "/toImportLogisticsNo")
  public String toImportLogisticsNo(){
    return "excel/toImport";
  }
  
  
  @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
  public String upload(@RequestParam("file") MultipartFile file,
      HttpServletRequest request) {
    Json json = new Json();
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH/");
   // String pathdir = "/var/sanji/excel/uploadfile/" + dateformat.format(new Date());// 构件服务器文件保存目录
    String pathdir = fileUploadDir + dateformat.format(new Date());// 构件本地文件保存目录 // 得到本地图片保存目录的真实路径 http://localhost:80/aaa.jpg
    String filename = UUID.randomUUID().toString() + FileUtil.getExt(file);// 构建文件名称
   // String fileUploadPath = pathdir+filename;
    try {
      //** 验证文件是否合法 *//*  
      if (ExcelUtil.validateExcel(pathdir+filename)){  
        String urlPath = FileUtil.saveFile(pathdir, filename, file,dateformat);
        if (urlPath != null && !"".equals(urlPath)) {
          json.setMsg("导入成功！");
          saveExcelData(pathdir+filename);
          FileUtil.deleteFile(pathdir+filename);
        } else {
          json.setMsg("导入失败！");
         // return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
        }
      }  else{
        json.setMsg("格式不正确！");
      }
      request.setAttribute("message", json);
      return "excel/result";
     
    } catch (Exception e) {
      logger.error("login() occur error. ", e);
      e.printStackTrace();
      json.setMsg("excel导入异常！");
      request.setAttribute("message", json);
      return "excel/result";
    }

  }
  
   private  void saveExcelData(String path) throws IOException {
          OrderSignfor xlsOrder = null;
          List<OrderSignfor> list = readXls(path);
          //从数据库导出表
         /* try {
              XlsDto2Excel.xlsDto2Excel(list);
          } catch (Exception e) {
              e.printStackTrace();
          }*/
           for (OrderSignfor os : list) {
                OrderSignfor orderSf = orderSignforService.findByOrderNo(os.getOrderNo());
                if(orderSf != null){
                  orderSf.setFastmailNo(os.getFastmailNo());
                  orderSf.setFastmailTime(os.getFastmailTime());
                  orderSignforService.updateOrderSignfor(orderSf);
                }
          }
      }
    
   
      /**
       * 读取xls文件内容
       * 
       * @return List<XlsDto>对象
       * @throws IOException
       *             输入/输出(i/o)异常
       */
      private List<OrderSignfor> readXls(String filePath) {
        // filePath = "E:\\excel\\高新：三际平台和51蜂云平台新增渠道.xlsx";
       
        Workbook wb = ExcelUtil.getWorkbook(filePath);
          OrderSignfor orderSignfor = null;
          List<OrderSignfor> list = new ArrayList<OrderSignfor>();
          // 循环工作表Sheet
          for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
            Sheet sheet = wb.getSheetAt(numSheet);
              if (sheet == null) {
                  continue;
              }
              // 循环行Row
              for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row hssfRow = sheet.getRow(rowNum);
                  if (hssfRow == null) {
                      continue;
                  }
                  orderSignfor = new OrderSignfor();
                  // 循环列Cell
                  // 0学号 1姓名 2学院 3课程名 4 成绩
                  // for (int cellNum = 0; cellNum <=4; cellNum++) {
                 /* Cell xy = hssfRow.getCell(0);
                  if (xy == null) {
                      continue;
                  }
                  System.out.println("==================="+getValue(xy));*/
                 
                 Cell ordernum = hssfRow.getCell(1);
                  if (ordernum == null) {
                      continue;
                  }
                  String[] orderno =  ExcelUtil.getValue(ordernum).split(",");
                  if(orderno.length >=2){
                    for(int i=0;i<orderno.length;i++){
                      orderSignfor.setOrderNo(orderno[i]);
                    }
                  }else{
                    orderSignfor.setOrderNo(orderno[0]);
                  }
                  
                  Cell fastmailNo = hssfRow.getCell(2);
                  if (fastmailNo == null) {
                      continue;
                  }
                  orderSignfor.setFastmailNo(ExcelUtil.getValue(fastmailNo));
                 // member.setUsername(getFormatName(getValue(username)));
                  Cell fastmailTime = hssfRow.getCell(5);
                  if (fastmailTime == null) {
                      continue;
                  }
                  SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                  try {
                    orderSignfor.setFastmailTime(date.parse(ExcelUtil.getValue(fastmailTime)) );
                  } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                  }
                  
                   
                  
                  list.add(orderSignfor);
              }
          }
          return list;
      }
   

}
