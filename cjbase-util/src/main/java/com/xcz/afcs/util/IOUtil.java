package com.xcz.afcs.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;

public abstract class IOUtil {

    private static final Logger LOG = LoggerFactory.getLogger(IOUtil.class);

    public static final String CHARSET_NAME_UTF8 = "UTF-8";

    public static final Charset CHARSET_UTF8 = Charset.forName(CHARSET_NAME_UTF8);

    public static final byte[] EMPTY_BYTES = new byte[0];

    private static final int DEFAULT_SIZE = 4096;

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.error("Close closeable faild: " + closeable.getClass(), e);
            }
        }
    }

    public static byte[] base64StringToBytes(String base64String) {
        if (StringUtils.isNotEmpty(base64String)) {
            return Base64.decodeBase64(base64String);
        } else {
            return EMPTY_BYTES;
        }
    }

    public static byte[] fileToBytes(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return streamToBytes(fis);
        } catch (Exception e) {
            LOG.error("read bytes failed", e);
        } finally {
            close(fis);
        }
        return null;
    }

    public static byte[] streamToBytes(InputStream is) {
        return streamToBytes(is, false);
    }


    public static byte[] streamToBytes(InputStream is, boolean closeInputStream) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[DEFAULT_SIZE];
            int readedLen = -1;
            while (-1 != (readedLen = is.read(buff))) {
                baos.write(buff, 0, readedLen);
            }
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            LOG.error("read bytes failed", e);
        } finally {
            close(baos);
            if (closeInputStream) {
                close(is);
            }
        }
        return null;
    }

    public static String bytesToBase64String(byte[] bytes) {
        if (null != bytes) {
            return Base64.encodeBase64String(bytes);
        } else {
            return "";
        }
    }

    public static String fileToBase64String(File file) {
        byte[] bytes = fileToBytes(file);
        return bytesToBase64String(bytes);
    }

    public static String streamToBase64String(InputStream is) {
        return streamToBase64String(is, false);
    }

    public static String streamToBase64String(InputStream is, boolean closeInputStream) {
        byte[] bytes = streamToBytes(is, closeInputStream);
        if (null != bytes) {
            return Base64.encodeBase64String(bytes);
        } else {
            return "";
        }
    }

    public static boolean bytesToFile(byte[] bytes, File file) {
        if (null != bytes && null != file) {
            ByteArrayInputStream bais = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                return streamToFile(bais, file);
            } catch (Exception e) {
                LOG.error("write bytes failed", e);
            } finally {
                close(bais);
            }
        }
        return false;
    }

    public static boolean streamToFile(InputStream is, File file) {
        return streamToFile(is, file, false);
    }


    public static boolean streamToFile(InputStream is, File file, boolean closeInputStream) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buff = new byte[DEFAULT_SIZE];
            int readedLen = -1;
            while (-1 != (readedLen = is.read(buff))) {
                fos.write(buff, 0, readedLen);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            LOG.error("write bytes failed", e);
        } finally {
            close(fos);
            if (closeInputStream) {
                close(is);
            }
        }
        return false;
    }

    public static boolean base64StringToFile(String base64String, File file) {
        if (null != base64String) {
            byte[] bytes = base64StringToBytes(base64String);
            return bytesToFile(bytes, file);
        }
        return false;
    }

    public static InputStream bytesToStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static InputStream base64StringToStream(String base64String) {
        if (null != base64String) {
            byte[] bytes = base64StringToBytes(base64String);
            return new ByteArrayInputStream(bytes);
        }
        return null;
    }

    public static InputStream fileToStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static boolean ensureDir(File dir) {
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    public static void deleteFile(File file) {
        if (file != null && file.isFile() && file.exists()) {
            file.delete();
        }
    }

}
