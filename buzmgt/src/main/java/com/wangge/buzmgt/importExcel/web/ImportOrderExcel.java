package com.wangge.buzmgt.importExcel.web;

import com.wangge.buzmgt.assess.service.RegistDataService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.RelatedStatus;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.util.ExcelUtil;
import com.wangge.buzmgt.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
  public String toImportLogisticsNo() {
    return "excel/toImport";
  }


  @RequestMapping(value = "/images/upload", method = RequestMethod.POST)
  public String upload(@RequestParam("file") MultipartFile file,
                       HttpServletRequest request) {
    Json json = new Json();
    String filename = null;

    // String pathdir = "/var/sanji/excel/uploadfile/" + dateformat.format(new Date());// 构件服务器文件保存目录

    if (!file.isEmpty()) {
      SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH/");
      String pathdir = fileUploadDir + dateformat.format(new Date());// 构件本地文件保存目录 // 得到本地图片保存目录的真实路径 http://localhost:80/aaa.jpg
      filename = UUID.randomUUID().toString() + FileUtil.getExt(file);// 构建文件名称

      // String fileUploadPath = pathdir+filename;
      try {
        //** 验证文件是否合法 *//*
        if (ExcelUtil.validateExcel(pathdir + filename)) {
          String urlPath = FileUtil.saveFile(pathdir, filename, file, dateformat);
          if (urlPath != null && !"".equals(urlPath)) {
            json.setMsg("导入成功！");
            saveExcelData(pathdir + filename);
            FileUtil.deleteFile(pathdir + filename);
          } else {
            json.setMsg("导入失败！");
            // return new ResponseEntity<Json>(json, HttpStatus.UNAUTHORIZED);
          }
        } else {
          json.setMsg("格式不正确！");
        }
        request.setAttribute("message", json);
        return "excel/result";

      } catch (Exception e) {
        FileUtil.deleteFile(pathdir + filename);
        e.getStackTrace();
        logger.error(e.getMessage());
        json.setMsg("excel导入异常！  " + e.getMessage());
        request.setAttribute("message", json);
        return "excel/result";
      }
    }
    json.setMsg("请选择要导入的excel！");
    request.setAttribute("message", json);
    return "excel/result";
  }

  private void saveExcelData(String path) throws Exception {
    OrderSignfor xlsOrder = null;
    List<OrderSignfor> list;
    list = readXls(path);
    //从数据库导出表
    /* try {
         XlsDto2Excel.xlsDto2Excel(list);
     } catch (Exception e) {
         e.printStackTrace();
     }*/
    if (list != null && list.size() > 0) {
      for (OrderSignfor os : list) {
        String[] orderno = os.getOrderNo().split(",");
        for (int i = 0; i < orderno.length; i++) {
          //  List<OrderSignfor> orderSf = orderSignforService.findByOrderNo(orderno[i]);
          OrderSignfor orderSf = findOrder(orderSignforService.findListByOrderNo(orderno[i]));

          if (orderSf != null && (os.getFastmailNo() != null && !"".equals(os.getFastmailNo()))) {
            orderSf.setFastmailTime(os.getFastmailTime());
            orderSf.setFastmailNo(os.getFastmailNo());
            orderSf.setFastmailTime(os.getFastmailTime());
            orderSignforService.updateOrderSignfor(orderSf);
          } else {
            String sql = "select o.order_num,\n" +
                "       o.total_cost,\n" +
                "       o.actual_pay_num,\n" +
                "       nvl(count(oi.nums),0),\n" +
                "       m.username,\n" +
                "       m.mobile,\n" +
                "       a.mobilephone\n" +
                "  from SJZAIXIAN.SJ_TB_ORDER o\n" +
                "  left join SJZAIXIAN.Sj_Tb_Order_Items oi\n" +
                "    on o.id = oi.order_id\n" +
                "  left join SJZAIXIAN.Sj_Tb_Members m\n" +
                "    on o.member_id = m.id\n" +
                "  left join SJZAIXIAN.Sj_Tb_Admin a\n" +
                "    on m.admin_id=a.id\n" +
                " where oi.target_type = 'sku' and o.order_num=?\n" +
                " group by o.order_num, o.total_cost, o.actual_pay_num,m.username,m.mobile,a.mobilephone";
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
            Query query1 = entityManager.createNativeQuery(sql);
            sqlQuery = query1.unwrap(SQLQuery.class);
            sqlQuery.setParameter(a, orderno[i]);
            BigDecimal partsCount = (BigDecimal) sqlQuery.uniqueResult();
            if (CollectionUtils.isNotEmpty(ret)) {
              ret.forEach(result -> {
                OrderSignfor o = new OrderSignfor();
                o.setOrderNo((String) result[0]);
                o.setCreateTime(new Date());
                o.setOrderPrice(((BigDecimal) result[1]).floatValue());
                if (ObjectUtils.notEqual(result[2],null)){
                  o.setActualPayNum(((BigDecimal) result[2]).floatValue());//实际金额
                }
                o.setPhoneCount(((BigDecimal) result[3]).intValue());
                o.setOrderStatus(OrderSignfor.OrderStatus.SUCCESS);
                o.setShopName((String) result[4]);
                //获取配件数量
//                if (CollectionUtils.isNotEmpty(resultList)) {
//                  resultList.forEach(r -> {
                    if(ObjectUtils.notEqual(partsCount,null)){
                      o.setPartsCount(Integer.valueOf(partsCount+""));
                    }

//                  });
//                }
                //查询是否已关联
                String loginAccount = registDataService.findLoginAccountByLoginAccount((String) result[5]);
                if (StringUtils.isNotEmpty(loginAccount)) {
                  o.setRelatedStatus(RelatedStatus.ENDRELATED);
                } else {
                  o.setRelatedStatus(RelatedStatus.NOTRELATED);
                }
                o.setMemberPhone((String) result[5]);
                o.setUserPhone((String) result[6]);
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
   * findOrder:(订单去重). <br/>
   *
   * @param orderlist
   * @return
   * @author Administrator
   * @since JDK 1.8
   */
  private OrderSignfor findOrder(List<OrderSignfor> orderlist) {

    if (orderlist.size() >= 1) {
      for (int i = 1; i < orderlist.size(); i++) {
        orderSignforService.deleteById(orderlist.get(i).getId());
      }
      return orderlist.get(0);
    }
    return null;
  }

  /**
   * 读取xls文件内容
   *
   * @return List<XlsDto>对象
   * @throws IOException 输入/输出(i/o)异常
   */
  private List<OrderSignfor> readXls(String filePath) throws Exception {
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
        orderSignfor.setOrderNo(isValidDate(ordernum));
        Cell fastmailNo = hssfRow.getCell(1);
        if (fastmailNo == null) {
          continue;
        }
        orderSignfor.setFastmailNo(validatFestmailNo(fastmailNo));
        // member.setUsername(getFormatName(getValue(username)));
        Cell fastmailTime = hssfRow.getCell(2);
        if (fastmailTime == null) {
          continue;
        }
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

        orderSignfor.setFastmailTime(date.parse(ExcelUtil.getValue(fastmailTime)));


        list.add(orderSignfor);
      }
    }
    return list;
  }

  /**
   * validatFestmailNo:(验证物流单号格式). <br/>
   *
   * @param param
   * @return
   * @throws Exception
   * @author Administrator
   * @since JDK 1.8
   */
  private static String validatFestmailNo(Cell param) throws Exception {
    String FestmailNo = ExcelUtil.getValue(param).replace("\r\n", "").trim();
    if (!"非法字符".equals(FestmailNo) && !"未知类型".equals(FestmailNo) && !"".equals(FestmailNo)) {
      String[] arr = {"9", "6", "5", "7"};
      if (in(arr, FestmailNo.substring(0, 1))) {

        return FestmailNo;
      }
      throw new Exception("物流单号格式不正确");
    }
    throw new Exception("非法字符 或 未知类型");

  }

  /**
   * isValidDate:(验证订单号格式). <br/>
   *
   * @param param
   * @return
   * @throws Exception
   * @author Administrator
   * @since JDK 1.8
   */
  private static String isValidDate(Cell param) throws Exception {
    String str = ExcelUtil.getValue(param).replace("\r\n", "").trim();
    // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；

    if (!"非法字符".equals(str) && !"未知类型".equals(str) && !"".equals(str)) {
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
      try {
        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        format.parse(str);
      } catch (ParseException e) {
        // e.printStackTrace();
        // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
        // convertSuccess=str;
        throw new Exception("订单格式不对！");
      }
      return str;
    }
    throw new Exception("非法字符或未知类型！");

  }

  private static boolean in(String[] arr, String fNoString) {
    for (int i = 0; i < arr.length; i++) {
      if (fNoString.equals(arr[i])) {
        return true;
      }

    }
    return false;
  }
}
