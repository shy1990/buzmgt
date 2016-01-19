package com.wangge.buzmgt.activity.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wangge.buzmgt.util.UploadUtil;


@Controller
@RequestMapping(value = "/activity")
public class ActivityController {
  @RequestMapping(value = "/send")
  public String  send(){
    return "activity/activitySend";
  }
  
  
//  @RequestMapping(value = "/upload", method = RequestMethod.POST)
//  public void upload(HttpServletRequest request,
//      HttpServletResponse response) throws IOException {
//    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//    // 得到上传的文件
//    MultipartFile mFile = multipartRequest.getFile("file");
//    // 得到上传服务器的路径
//    String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
//    // 得到上传的文件的文件名
//    String filename = mFile.getOriginalFilename();
//    InputStream inputStream = mFile.getInputStream();
//    byte[] b = new byte[1048576];
//    int length = inputStream.read(b);
//    path += "\\" + filename;
//    // 文件流写到服务器端
//    FileOutputStream outputStream = new FileOutputStream(path);
//    outputStream.write(b, 0, length);
//    inputStream.close();
//    outputStream.close();
//  }
  
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    String pathdir = "D:/images/";// 构件文件保存目录
    String filename = UUID.randomUUID().toString() + ".jpg";// 构建文件名称
    try {
      String path = UploadUtil.saveFile(pathdir, filename,file);
      if (path != null && !"".equals(path)) {
      } else {
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
