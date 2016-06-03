package com.wangge.buzmgt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 1.本工具主要支持List<Map<String,Object>>类型容器的输出
 * 2.支持单元格合并:MarginCell;本方法中合并单元格的内容将自动归纳整理,且不可改变;合并的单元格的个数要大于1个 
 * 3.待测试完善功能:支持List<Map<String,subList>>类型的容器输出;
 * 4
 * @author yangqc
 *
 */
public class MapedExcelExport {
	protected static final Logger logger = Logger.getLogger(MapedExcelExport.class);

	/**
	 * excel导出
	 * 
	 * @param fileName
	 *            文件名
	 * @param resultList
	 *            结果集
	 * @param gridTitles
	 *            标题
	 * @param coloumsKey
	 *            Bean字段
	 * @param request
	 * @param response
	 * @param marginList
	 */
	public static void doExcelExport(String fileName, List<Map<String, Object>> resultList, String[] gridTitles,
			String[] coloumsKey, HttpServletRequest request, HttpServletResponse response,
			List<Map<String, Object>> marginList) {

		if (StringUtils.isEmpty(fileName) || null == request) {
			logger.error("文件名或者HttpRequest为空");
			return;
		}

		if (null == resultList) {
			logger.error("文件对象不存在");
			return;
		}

		if (null == gridTitles) {
			logger.error("标题为空");
			return;
		}

		if (null == coloumsKey) {
			logger.error("字段为空");
			return;
		}

		String encodedfileName = "";
		try {
			encodedfileName = encodeFileNameForDownload(request, fileName);
			HSSFWorkbook book = getExcelContent(resultList, gridTitles, coloumsKey, marginList);

			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + encodedfileName);
			book.write(response.getOutputStream());
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持的编码类型:" + e.getMessage());
		} catch (IOException e) {
			logger.error("文件导出错误" + e.getMessage());
		}

	}

	/**
	 * 处理单元格合并的情况
	 * 
	 * @param sheet
	 * @param marginList
	 * @param style
	 */
	private static void handleMarginCell(HSSFSheet sheet, List<Map<String, Object>> marginList, HSSFCellStyle style) {
		if (null == marginList) {
			return;
		} else {
			for (Map<String, Object> marginMap : marginList) {
				int firstRow = (Integer) marginMap.get("firstRow");
				int lastRow = (Integer) marginMap.get("lastRow");
				int firstCol = (Integer) marginMap.get("firstCol");
				int lastCol = (Integer) marginMap.get("lastCol");
				// String val = marginMap.get("value") + "";
				try {
					sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));// 指定合并区域
					HSSFCell cell1 = sheet.getRow(lastRow).getCell(firstCol);
					cell1.setCellStyle(style);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// cell1.setCellValue(val);
			}
		}

	}

	/**
	 * 
	 * @param dataList
	 * @param gridTitles
	 * @param coloumsKey
	 * @param coloumsKey
	 * @param marginList
	 * @return Excel的HSSFWorkbook对象
	 */
	private static HSSFWorkbook getExcelContent(List<Map<String, Object>> dataList, String[] gridTitles,
			String[] coloumsKey, List<Map<String, Object>> marginList) {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = workBook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		for (int i = 0; i < gridTitles.length; i++) {
			row.createCell(i).setCellValue(new HSSFRichTextString(gridTitles[i]));
		}
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Map<String, Object> dmap = dataList.get(i);
				int j = 0;
				for (String key : coloumsKey) {
					Object obj = dmap.get(key);
					String val = null;
					if (obj != null && ifList(obj.getClass())) {
						@SuppressWarnings("unchecked")
						List<Object> list = (List<Object>) obj;
						for (Object listItem : list) {
							if (ifMap(val.getClass())) {
								Map<Object, Object> subMap = (Map<Object, Object>) listItem;
								int mapsize = subMap.values().size();
								if (mapsize == 1) {
									val = getVal(listItem);
								} else {
									// ToDo 多个时 如何处理
								}
							} else {
								val = getVal(listItem);
							}
							HSSFCell cell = row.createCell(j);
							if (null != val)
								cell.setCellValue(new HSSFRichTextString(val));
							cell.setCellStyle(style);
							j++;
						}
					} else {
						val = getVal(obj);
						HSSFCell cell = row.createCell(j);
						if (null != val) {
							cell.setCellValue(new HSSFRichTextString(val));
						}
						cell.setCellStyle(style);
						j++;
					}
				}
			}
		}
		// WritableSheet sheetWrite=writeWorkbook.createSheet("sheet的名称",0);
		// 把 单元格（column, row）到单元格（column1, row1）进行合并。
		handleMarginCell(sheet, marginList, style);
		return workBook;
	}

	/**
	 * @param obj
	 * @return
	 */
	private static String getVal(Object obj) {
		String val = null;
		if (obj == null) {
			return val;
		} else if (null != obj && "java.util.Date".equals(obj.getClass().getName())) {
			val = DateFormatUtils.format((Date) obj, "yyyy-MM-dd", Locale.CHINESE);

		} else {
			val = obj.toString();
		}
		return val;
	}

	/**
	 * 判断对象是否实现List
	 * 
	 * @param classz
	 * @return
	 */
	public static boolean ifList(Class<?> classz) {
		for (Class<?> classz1 : classz.getInterfaces()) {
			if (classz1 == Iterable.class) {
				System.out.println("sdfsf");
				return true;

			} else if (classz1.getInterfaces().length > 0) {
				return ifList(classz1);
			}
		}
		return false;
	}

	/**
	 * 判断对象是否实现Map
	 * 
	 * @param classz
	 * @return
	 */
	public static boolean ifMap(Class<?> classz) {
		for (Class<?> classz1 : classz.getInterfaces()) {
			if (classz1 == Map.class) {
				System.out.println("sdfsf");
				return true;

			} else if (classz1.getInterfaces().length > 0) {
				return ifMap(classz1);
			}
		}
		return false;
	}

	/**
	 * 将文件名使用UTF8编码格式重新编码，以避免下载时提示的文件名乱码
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String encodeFileNameForDownload(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		if (request == null || StringUtils.isBlank(fileName)) {
			logger.error("文件名UTF8编码失败：httpRequest与文件名不能为空！");
			return fileName;
		}
		String encodedfileName = "";
		// 火狐
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}
		// IE
		else {
			encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		}

		return encodedfileName;
	}
}
