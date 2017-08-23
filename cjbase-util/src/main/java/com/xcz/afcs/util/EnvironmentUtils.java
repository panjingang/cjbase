package com.xcz.afcs.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentUtils {
    
    private static final String CLASSPATH_PREFIX = "classpath:";
    
    /**
     * 获取系统运行期配置文件路径
     * @return
     */
    public static String getRuntimeConfigPath() {
        String path = System.getenv().get("RUNTIME_CONFIG_ROOT");
        if (StringUtils.isBlank(path)) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0) {
                path = "C:" + File.separator + "runtime_config_root";
            } else {
                path = "/runtime_config_root";
            }
        }
        return path;
    }
    
    /**
     * 获取Classpath根目录地址
     * @return
     */
    public static String getClasspathRoot() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        return url == null ? null : url.getPath();
    }

    public static String getClasspathFilePath(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        return url == null ? null : url.getPath();
    }

    public static String getFileAbsolutePath(String filename) throws FileNotFoundException {
        if (filename.startsWith(CLASSPATH_PREFIX)) {
            filename = filename.substring(CLASSPATH_PREFIX.length());
        }
        if (filename.startsWith("/")) {
            filename = filename.substring(1);
            String fileUrl = EnvironmentUtils.getRuntimeConfigPath() + File.separator + filename;
            File file = new File(fileUrl);
            if (file.exists()) {
                return fileUrl;
            }
            fileUrl = getClasspathRoot() + filename;
            file = new File(fileUrl);
            if (file.exists()) {
                return fileUrl;
            } else {
                throw new FileNotFoundException(filename + " Not Found In System ClassPath ...");
            }
        } else {
            String configRoot = EnvironmentUtils.getRuntimeConfigPath();
            List<File> list = new ArrayList<File>();
            findFile(new File(configRoot), filename, list);
            if (list.isEmpty()) {
                String fileUrl = getClasspathRoot() + filename;
                File file = new File(fileUrl);
                if (file.exists()) {
                    return fileUrl;
                } else {
                    throw new FileNotFoundException(filename + " Not Found In System ClassPath ...");
                }
            } else {
                return list.get(0).getAbsolutePath();
            }
        }
        
    }
    
    public static String findFileInRuntimeConfigDirOrClasspath(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        boolean inClasspath = false;
        if (fileName.startsWith(CLASSPATH_PREFIX)) {
            inClasspath = true;
            fileName = fileName.substring(CLASSPATH_PREFIX.length());
        }
        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        if (!inClasspath) {
            String filePath = getRuntimeConfigPath() + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                return filePath;
            }
        }
        String filePath = getClasspathFilePath(fileName);
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }
        return null;
    }
    
    private static void findFile(File file, String filename, List<File> list) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                findFile(subFile, filename, list);
            }
        } else {
            if (file.getAbsolutePath().contains(filename)) {
                list.add(file);
            }
        }
    }
    
}
