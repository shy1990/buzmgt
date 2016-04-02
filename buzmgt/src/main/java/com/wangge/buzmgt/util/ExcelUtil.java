package com.wangge.buzmgt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wangge.buzmgt.importExcel.web.ImportOrderExcel;

public class ExcelUtil {
  private static final Logger logger = Logger.getLogger(ImportOrderExcel.class);
  
  
  
    public static Workbook getWorkbook(String filePath){
     
      Workbook wb = null;
      try  
      {  

         //** 验证文件是否合法 *//*  

            //** 判断文件的类型，是2003还是2007 *//*  
            InputStream inputStream = new FileInputStream(filePath);
            if (WdwUtil.isExcel2007(filePath)){ 
             
              wb = new XSSFWorkbook(inputStream); 
            } else{
              wb = new HSSFWorkbook(inputStream);
            }
      }catch (Exception ex){  

         ex.printStackTrace();  
     }
      return wb;
    }
    
    
    public static boolean validateExcel(String filePath){  
      
      /** 检查文件名是否为空或者是否是Excel格式的文件 */  

      if (filePath == null || !(WdwUtil.isExcel2003(filePath) || WdwUtil.isExcel2007(filePath))){  

       logger.error("文件名不是excel格式");
        
          return false;  

      }  

      /** 检查文件是否存在 */  

      File file = new File(filePath);  

      if (file == null || !file.exists()){  
       // logger.error("文件不存在");

        return true;  

      }  

      return false;  

  }  
    
    /**
     * 得到Excel表中的值
     * 
     * @param hssfCell
     *            Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    public static String getValue(Cell hssfCell) {
      String cellValue = "";  
        /*if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }*/
        if (null != hssfCell){  
              // 以下是判断数据的类型  
           switch (hssfCell.getCellType()){  
              case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
                if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                  short format = hssfCell.getCellStyle().getDataFormat();  
                  SimpleDateFormat sdf = null;  
                  if(format == 14 || format == 31 || format == 57 || format == 58){  
                      //日期  
                      sdf = new SimpleDateFormat("yyyy/MM/dd");  
                  }else if (format == 20 || format == 32) {  
                      //时间  
                      sdf = new SimpleDateFormat("HH:mm");  
                  }  
                  double value = hssfCell.getNumericCellValue();  
                  Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value); 
                  cellValue = sdf.format(date); 
                }else{
                  DecimalFormat dft = new DecimalFormat("0");
                  cellValue = dft.format(hssfCell.getNumericCellValue());
                }
           
                  break;  

              case HSSFCell.CELL_TYPE_STRING: // 字符串  
                  cellValue = hssfCell.getStringCellValue();  
                  break;

              case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  
                  cellValue = String.valueOf(hssfCell.getBooleanCellValue());  
                  break;  

              case HSSFCell.CELL_TYPE_FORMULA: // 公式  
                  cellValue = hssfCell.getCellFormula();  
                  break;  

              case HSSFCell.CELL_TYPE_BLANK: // 空值  
                  cellValue = "";  
                  break;  

              case HSSFCell.CELL_TYPE_ERROR: // 故障  
                  cellValue = "非法字符";  
                  break;  

              default:  
                  cellValue = "未知类型";  
                  break;  
              }  
          } 
        return cellValue;
    }

}
