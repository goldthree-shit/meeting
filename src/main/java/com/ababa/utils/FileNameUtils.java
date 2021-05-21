package com.ababa.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileNameUtils {
    public static String getFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        if (fileName.indexOf("\\") > 0) {
            int index = fileName.lastIndexOf("\\");
            fileName = fileName.substring(index + 1);
        }
        return fileName;
    }
}
