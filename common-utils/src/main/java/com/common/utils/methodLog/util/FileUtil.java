//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.utils.methodLog.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public FileUtil() {
    }

    public static File createFile(String filename) throws Exception {
        File file = newFile(filename);
        if (!file.canWrite()) {
            String dirName = file.getPath();
            int i = dirName.lastIndexOf(File.separator);
            if (i > -1) {
                dirName = dirName.substring(0, i);
                File dir = newFile(dirName);
                dir.mkdirs();
            }

            file.createNewFile();
        }

        return file;
    }

    public static int getFileSize(String fileName) {
        File file = new File(fileName);
        FileInputStream fis = null;

        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                int var3 = fis.available();
                return var3;
            }
        } catch (FileNotFoundException var16) {
            logger.error("", var16);
        } catch (IOException var17) {
            logger.error("", var17);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException var15) {
                    logger.error("failed to close file", var15);
                }
            }

        }

        return 0;
    }

    public static File newFile(String pathName) throws Exception {
        try {
            return (new File(pathName)).getCanonicalFile();
        } catch (IOException var2) {
            logger.error("", var2);
            throw new Exception("创建文件失败", var2);
        }
    }

    public static String getClassFilePath(Class<?> clazz) {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;

        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (UnsupportedEncodingException var4) {
            ;
        }

        File file = new File(filePath);
        return file.getAbsolutePath();
    }

    public static void writeByteArraysToFile(File file, List<byte[]> bytes) {
        BufferedOutputStream bufferedOut = null;

        try {
            FileOutputStream out = openOutputStream(file, true);
            bufferedOut = new BufferedOutputStream(out);
            Iterator i$ = bytes.iterator();

            while(i$.hasNext()) {
                byte[] s = (byte[])i$.next();
                bufferedOut.write(s);
            }

            bufferedOut.flush();
        } catch (Exception var14) {
            logger.error(">>>> Excute write Exception: ", var14);
        } finally {
            try {
                if (bufferedOut != null) {
                    bufferedOut.close();
                }
            } catch (IOException var13) {
                logger.error(">>>> Clost BufferedWriter Exception:", var13);
            }

        }

    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("File '" + file + "' could not be created");
            }
        }

        return new FileOutputStream(file, true);
    }
}
