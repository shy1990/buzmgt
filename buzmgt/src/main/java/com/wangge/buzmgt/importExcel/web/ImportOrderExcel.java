package com.wangge.buzmgt.importExcel.web;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.service.RegistDataService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.RelatedStatus;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.util.ExcelUtil;
import com.wangge.buzmgt.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping(value = "/import")
public class ImportOrderExcel {
  
  private static final Logger logger = Logger.getLogger(ImportOrderExcel.class);
  
  @Value("${buzmgt.file.fileUploadPath}")
  private String fileUploadDir;
  
  //private static String url = "http://image.3j1688.com/uploadfile/"; // 外网正式环境
  
  @Resource
  private OrderSignforService orderSignforService;
  @PersistenceContext
  private EntityManager entityManager;
  @Resource
  private RegistDataService registDataService;
  
  @RequestMapping(value = "/toImportLogisticsNo")
  public String toImportLogisticsNo(){
    return "excel/toImport";
  }
  
  
  @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
  public String upload(@RequestParam("file") MultipartFile file,
      HttpServletRequest request) {
    Json json = new Json();
    String filename = null;
   
   // String pathdir = "/var/sanji/excel/uploadfile/" + dateformat.format(new Date());// 构件服务器文件保存目录
  
    if(!file.isEmpty()){
      SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH/");
      String pathdir = fileUploadDir + dateformat.format(new Date());// 构件本地文件保存目录 // 得到本地图片保存目录的真实路径 http://localhost:80/aaa.jpg
       filename  = UUID.randomUUID().toString() + FileUtil.getExt(file);// 构建文件名称
    
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
    json.setMsg("请选择要导入的excel！");
    request.setAttribute("message", json);
    return "excel/result";
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
     if(list != null && list.size() > 0){
       for (OrderSignfor os : list) {
         String[] orderno =  os.getOrderNo().split(",");
           for(int i=0;i<orderno.length;i++){
             OrderSignfor orderSf = orderSignforService.findByOrderNo(orderno[i]);
             if(orderSf != null && (os.getFastmailNo() != null && !"".equals(os.getFastmailNo()))){
                 orderSf.setFastmailTime(os.getFastmailTime());
                 orderSf.setFastmailNo(os.getFastmailNo());
                 orderSf.setFastmailTime(os.getFastmailTime());
                 orderSignforService.updateOrderSignfor(orderSf);
             }else {
               String sql = "select o.order_num,\n" +
                               "       o.total_cost,\n" +
                               "       o.actual_pay_num,\n" +
                               "       nvl(count(oi.nums),0),\n" +
                               "       m.username,\n" +
                               "       m.mobile\n" +
                               "  from SJZAIXIAN.SJ_TB_ORDER o\n" +
                               "  left join SJZAIXIAN.Sj_Tb_Order_Items oi\n" +
                               "    on o.id = oi.order_id\n" +
                               "  left join SJZAIXIAN.Sj_Tb_Members m\n" +
                               "    on o.member_id = m.id\n" +
                               " where oi.target_type = 'sku' and o.order_num=?\n" +
                               " group by o.order_num, o.total_cost, o.actual_pay_num,m.username,m.mobile";
               Query query = null;
               SQLQuery sqlQuery = null;
               query = entityManager.createNativeQuery(sql);
               sqlQuery = query.unwrap(SQLQuery.class);
               int a = 0;
               sqlQuery.setParameter(a, orderno[i]);
               List<Object[]> ret = sqlQuery.list();
               //查询配件数量
               sql = "select nvl(count(oi.nums),0)\n" +
                               "  from SJZAIXIAN.SJ_TB_ORDER o\n" +
                               "  left join SJZAIXIAN.Sj_Tb_Order_Items oi\n" +
                               "    on o.id = oi.order_id\n" +
                               "  left join SJZAIXIAN.Sj_Tb_Members m\n" +
                               "    on o.member_id = m.id\n" +
                               " where oi.target_type = 'accessories' and o.order_num=?\n" +
                               " group by o.order_num";
               Query query1 =  entityManager.createNativeQuery(sql);
               sqlQuery = query1.unwrap(SQLQuery.class);
               sqlQuery.setParameter(a, orderno[i]);
               List<Object[]>  resultList = sqlQuery.list();
               if (CollectionUtils.isNotEmpty(ret)) {
                 ret.forEach(result -> {
                   OrderSignfor o = new OrderSignfor();
                   o.setOrderNo((String) result[0]);
                   o.setCreateTime(new Date());
                   o.setOrderPrice(((BigDecimal)result[1]).floatValue());
                   o.setActualPayNum(((BigDecimal)result[2]).floatValue());//实际金额
                   o.setPhoneCount(((BigDecimal)result[3]).intValue());
                   o.setOrderStatus(OrderSignfor.OrderStatus.SUCCESS);
                   o.setShopName((String) result[4]);
                   //获取配件数量
                   if (CollectionUtils.isNotEmpty(resultList)){
                     resultList.forEach(r -> {
                       o.setPartsCount((int)r[0]);
                     });
                   }
                   //查询是否已关联
                   List<RegistData> registData = registDataService.findByLoginAccount((String) result[5]);
                   if (CollectionUtils.isNotEmpty(registData)){
                     o.setRelatedStatus(RelatedStatus.ENDRELATED);
                   }else {
                     o.setRelatedStatus(RelatedStatus.NOTRELATED);
                   }
                   o.setMemberPhone((String) result[5]);
                   o.setFastmailNo(os.getFastmailNo());
                   o.setFastmailTime(os.getFastmailTime());
                   orderSignforService.save(o);
                 });
               }
             }
           }
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
                 
                 Cell ordernum = hssfRow.getCell(0);
                  if (ordernum == null) {
                      continue;
                  }
                   orderSignfor.setOrderNo(ExcelUtil.getValue(ordernum).replace("\r\n", "").trim());
                  Cell fastmailNo = hssfRow.getCell(1);
                  if (fastmailNo == null) {
                      continue;
                  }
                  orderSignfor.setFastmailNo(ExcelUtil.getValue(fastmailNo).replace("\r\n", "").trim());
                 // member.setUsername(getFormatName(getValue(username)));
                  Cell fastmailTime = hssfRow.getCell(2);
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
