package com.xcz.afcs.util;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by jingang on 2017/4/19.
 */
public class ZipUtil {


    public static final void doCompress(List<File> srcFiles, List<String> entryNames,  File zipFile) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        if (srcFiles == null || entryNames == null || srcFiles.size() != entryNames.size()) {
            return;
        }
        for (int i= 0; i<srcFiles.size(); i++) {
            doCompress(srcFiles.get(i), entryNames.get(i), out);
        }
        out.close();
    }

    private static void doCompress(File srcFile, String entryName, ZipOutputStream out) throws IOException {
        if (srcFile.isFile() && srcFile.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);
                out.putNextEntry(new ZipEntry(entryName));
                int len = 0;
                // 读取文件的内容,打包到zip文件
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.closeEntry();
            } finally {
                IOUtil.close(fis);
            }
        }
    }

    public static void doUnCompress(File zipFile, String unzipFilePath) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] buffer = new byte[1024];
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            try{
                String entryFilePath = unzipFilePath + File.separator + entry.getName();
                bos = new BufferedOutputStream(new FileOutputStream(new File(entryFilePath)));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                int len = 0;
                // 读取文件的内容,打包到zip文件
                while ((len = bis.read(buffer)) > 0) {
                        bos.write(buffer, 0, len);
                }
                bos.flush();
            }finally {
                IOUtil.close(bos);
                IOUtil.close(bis);
            }
        }
    }

}
