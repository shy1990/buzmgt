package com.wangge.buzmgt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
  private static String url = "http://image.3j1688.com/uploadfile/"; // 外网正式环境

	public static String saveFile(String filePath,String fileName, MultipartFile filedata,SimpleDateFormat dateformat) {
	         /* 构建文件目录 */
        File filedir = new File(filePath);    
         if (!filedir.exists()) {
          filedir.mkdirs();
        }
 
        try {
          FileInputStream input = (FileInputStream) filedata.getInputStream();
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            byte[] tmp = new byte[1024*512];
            int len = 0;
            while ((len = input.read(tmp)) > 0){
              // 写入文件
              out.write(tmp, 0, len);
              out.flush();
            }
            out.close();
            return url+dateformat.format(new Date()) + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
		
        return null;
    }

	 public static String getExt(MultipartFile formfile){
	   
     return formfile.getOriginalFilename().substring(formfile.getOriginalFilename().indexOf('.')).toLowerCase();
 }
	 
	 public static boolean deleteFile(String path) {
     File deleteFile = new File(path);

     if (deleteFile.exists()) {
         return deleteFile.delete();
     }
     return false;
 }
	 
}
