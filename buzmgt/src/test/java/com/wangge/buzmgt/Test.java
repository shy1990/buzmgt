package com.wangge.buzmgt;

import junit.framework.TestCase;

public class Test extends TestCase {
  
  public void testDo() {
    String vl = null;
    System.out.println(Integer.valueOf(vl));
  }
  
  public void testPrint() {
    String[] names = "SJZAIXIAN,AVENGER,SJ_DB,SJ_BUZMGT,SJ_IMEI,SJ_YEWU,SJ_MEMBER".split(",");
    for (String name : names) {
      System.out.println("alter user " + name + " default tablespace " + name + ";");
    }
  }
  // private static String getAging(Long between) {
  // Long hour = between / 1000 / 3600;
  // System.out.println(hour);
  // Long minute = (between % (1000 * 3600)) / (1000 * 60);
  // System.out.println(minute);
  // return hour + "小时" + minute + "分钟";
  // }
  //
  // public static String formtUrl(String url) {
  // //
  // url="http://localhost:8081/ordersignfor/showrecord/A37010003140#box_tab2";
  // int beginIndex = url.indexOf("#");
  // System.out.println(beginIndex);
  // if (beginIndex == -1) {
  // return "";
  // } else {
  // return url.trim().substring(beginIndex + 1, url.length());
  // }
  // }
  //
  // public static String formtStr(String regionId) {
  // String sql = "";
  // if (StringUtils.isNotEmpty(regionId)) {
  // sql += "and (";
  // String[] regionIdarr = regionId.split(",");
  // for (int i = 0; i < regionIdarr.length; i++) {
  // if (i == 0) {
  // sql += "a.salesman_id like '%s' ";
  // } else {
  // sql += " or a.salesman_id like '%s'";
  // }
  // }
  // sql += ")";
  // System.out.println(sql);
  // sql = String.format(sql, regionIdarr);
  // }
  // return sql;
  //
  // }
  //
  // public static void testDouble() {
  // double s = 5D / 6D;
  // String rate = String.format("%10.2f%%", s).trim().substring(2);
  // System.out.println(rate);
  // }
  //
  // /**
  // *
  // */
  // public void testExcle() {
  // HSSFWorkbook wb = new HSSFWorkbook();
  // HSSFSheet sheet = wb.createSheet("new sheet");
  // HSSFCellStyle style = wb.createCellStyle(); // 样式对象
  //
  // style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
  // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
  // HSSFRow row = sheet.createRow(0);
  // HSSFCell cell = row.createCell(0);
  // for (int i = 1; i < 5; i++) {
  // row = sheet.createRow(i);
  // for (int j = 0; j < 5; j++) {
  // cell = row.createCell(j);
  // cell.setCellValue("Th");
  // }
  // }
  //
  // /*
  // * excle合并区域 CellRangeAddress cre = new CellRangeAddress(0, 0, 0, 4);
  // */
  // try {
  // sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 2));// 指定合并区域
  // HSSFCell cell1 = sheet.getRow(0).getCell(0);
  // cell1.setCellStyle(style);
  // cell1.setCellValue("This is a test of merging");
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  //
  // // Write the output to a file
  // FileOutputStream fileOut;
  // try {
  // fileOut = new FileOutputStream("d://workbook.xls");
  // wb.write(fileOut);
  // fileOut.close();
  // } catch (FileNotFoundException e) {
  // e.printStackTrace();
  // } catch (IOException e) {
  // e.printStackTrace();
  // }
  // }
  //
  // public void testList() {
  // Class<?> classz = new String[] {}.getClass();
  // System.out.println(ifIterable(classz));
  // }
  //
  // public boolean ifIterable(Class<?> classz) {
  // for (Class<?> classz1 : classz.getInterfaces()) {
  // if (classz1 == Iterable.class) {
  // System.out.println("sdfsf");
  // return true;
  //
  // } else if (classz1.getInterfaces().length > 0) {
  // return ifIterable(classz1);
  // }
  // }
  // return false;
  // }
  //
  // public void test() {
  // String[] ar1 = new String[] { "1", "2", "3", "4", "5" };
  //
  // }
  //
  // public void testJson() {
  // Map<String, String> params = new HashMap<String, String>();
  // params.put("mobiles", "18769727652");
  // params.put("msg", "下月的月任务已生成");
  // String xml =
  // HttpUtil.sendPostJson("http://192.168.1.225:8818/v1/push/mainMonthTask",
  // params);
  // System.out.println(xml);
  
  // }
}
