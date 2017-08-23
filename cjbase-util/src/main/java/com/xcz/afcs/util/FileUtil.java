package com.xcz.afcs.util;

import java.io.File;

/**
 * Created by jingang on 2017/6/21.
 */
public class FileUtil {

    //文件分割符号
    private static final String SEPARATOR = File.separator;

    public static boolean exists (File file) {
        return ObjectUtil.isNotNull(file) && file.exists();
    }

    public static boolean isFile(File file) {
        return ObjectUtil.isNotNull(file) && file.isFile();
    }

    public static boolean isDirectory(File file) {
        return ObjectUtil.isNotNull(file) && file.isDirectory();
    }

    public static boolean deleteFile(File file) {
        if (!exists(file)) {
            return false;
        }
        if (isFile(file)) {
            return file.delete();
        }else{
           File[] list =  file.listFiles();
           if (ObjectUtil.isNotNull(list)) {
               for (File subFile : list) {
                   deleteFile(subFile);
               }
           }
        }
        return false;
    }

    /**
     * 根据文件URI获取文件路径
     * @param fileUri
     * @return
     */
    public static String getFilePath(String fileUri) {
        if (ValueUtil.isEmpty(fileUri)) {
            return "";
        }
        int lastIndexStart = fileUri.lastIndexOf(SEPARATOR);
        if(lastIndexStart == -1 ){
            return fileUri;
        }
        return fileUri.substring(0, lastIndexStart);
    }

    public static String getFileName(String fileUri) {
        if (ValueUtil.isEmpty(fileUri)) {
            return "";
        }
        int lastIndexStart = fileUri.lastIndexOf(SEPARATOR);
        if(lastIndexStart == -1 ){
            return fileUri;
        }
        return fileUri.substring(lastIndexStart+1);
    }

    public static String genFileUri(String filePath, String fileName) {
        if (ValueUtil.isEmpty(filePath)) {
            return fileName;
        }
        if (filePath.endsWith(SEPARATOR)) {
            return filePath + fileName;
        }else {
            return filePath + SEPARATOR + fileName;
        }
    }

    public static String getExtName(String fileName) {
        if (ValueUtil.isEmpty(fileName)) {
            return "";
        }
        int lastIndexStart = fileName.lastIndexOf(StrUtil.DOT);
        if(lastIndexStart == -1 ){
            return StrUtil.EMPTY;
        }
        return fileName.substring(lastIndexStart+1);
    }

}
