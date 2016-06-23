package com.wangge.buzmgt.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 文件上传方法
 */
public class FileUtils {
    /**
     * 文件copy方法
     *
     * @param src
     * @param dest
     */
    public static void copy(InputStream src, OutputStream dest) {
        try {
            byte[] tmp = new byte[1024];
            int len = -1;
            while ((len = src.read(tmp)) != -1)
                dest.write(tmp, 0, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给文件重命名 防止覆盖
     *
     * @param fileName
     * @return 时间戳+原始文件的后缀
     */
    public static String reName(String fileName) {
        return new StringBuffer().append(new Date().getTime()).append(fileName.substring(fileName
                .indexOf("."))).toString();
    }

    /**
     * 文件保存
     *
     * @param fileName reName之后的文件名称
     * @param content
     * @param filePath 文件保存路径
     * @return
     * @throws IOException
     */
    public static String saveFile(String fileName, InputStream content, String filePath) throws
            IOException {
        FileOutputStream fos = null;
        StringBuffer contentPath = new StringBuffer(""); // 上下文地址
        try {
            contentPath.append(fileName);

            File pictureFile = new File(filePath + contentPath.toString());
            File pf = pictureFile.getParentFile();
            if (!pf.exists()) {
                pf.mkdirs();
            }
            pictureFile.createNewFile();    // 创建文件
            fos = new FileOutputStream(pictureFile);
            FileUtils.copy(content, fos);
        } catch (Exception e) {
            throw new IOException("文件保存失败!");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    throw new IOException("文件保存失败!");
                }
            }
        }
        return contentPath.toString();
    }

    public static boolean deleteFile(String path) {
        File deleteFile = new File(path);

        if (deleteFile.exists()) {
            return deleteFile.delete();
        }
        return false;
    }
}

