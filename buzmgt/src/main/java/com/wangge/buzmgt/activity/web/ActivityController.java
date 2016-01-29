package com.wangge.buzmgt.activity.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wangge.buzmgt.activity.service.ActivityDao;
import com.wangge.buzmgt.util.HttpUtil;
import com.wangge.buzmgt.util.UploadUtil;


@Controller
@RequestMapping(value = "/activity")
public class ActivityController {
  
  @Autowired
  private ActivityDao ad;
  
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
  /**
   * 
   * @Description: 活动图片上传
   * @param @param myfiles
   * @param @param request
   * @param @return   
   * @return String  
   * @throws
   * @author changjun
   * @date 2016年1月15日
   */
  @RequestMapping(value="/upload")
  @ResponseBody
  public String addUser( @RequestParam MultipartFile[] myfiles, HttpServletRequest request){
      String realPath = "/var/sanji/images/uploadfile/activityImg/";
//      String realPath = "D:/upload/";
      String filename = UUID.randomUUID().toString() + ".jpg";// 构建文件名称
      
      String path = UploadUtil.saveFile(realPath, filename,myfiles[0]);
      if(path != null && !"".equals(path)){
        request.getSession().setAttribute("img", path);
        System.out.println(request.getSession().getAttribute("img"));
        return "suc";
      }else{
        return "err";
      }
  }
  @RequestMapping(value="/sendActivity" ,method = RequestMethod.POST)
  @ResponseBody
  public String sendActivity(HttpServletRequest req){
    String title = req.getParameter("title");
    String content = req.getParameter("content");
    String region = req.getParameter("region");
    String  img   = req.getSession().getAttribute("img").toString();
    System.out.println(title+"==="+content+"===="+region+"==");
    String mobiles = ad.selSalesmanByRegion(region);
    System.out.println(mobiles);
    try {
      HttpUtil.sendPost("http://192.168.2.146:8082/v1/push/pushActivi", "msg={\"title\":\""+title+"\",\"content\":\""+content+"\",\"img\":\""+img+"\"}&mobiles="+mobiles+"");
      return "suc";  
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}
