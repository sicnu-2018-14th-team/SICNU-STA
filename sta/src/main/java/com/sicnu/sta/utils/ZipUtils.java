package com.sicnu.sta.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ZipUtils {

    /**
     * 缓冲大小
     */
    private static final int BUFFERSIZE = 2 << 10;


    private ZipUtils() {
    }

    /**
     * 解压缩
     * @param fileName 压缩文件路径
     * @param path 输出路径
     */
    public static void unzip(String fileName, String path) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            try (ZipFile zf = new ZipFile(new File(fileName))) {
                Enumeration<? extends ZipEntry> en = zf.entries();
                while (en.hasMoreElements()) {
                    ZipEntry zn = (ZipEntry) en.nextElement();
                    if (!zn.isDirectory()) {
                        is = zf.getInputStream(zn);
                        File f = new File(path + zn.getName());
                        File file = f.getParentFile();
                        boolean mkdirs = file.mkdirs();
                        fos = new FileOutputStream(path + zn.getName());
                        int len = 0;
                        byte[] buffer = new byte[BUFFERSIZE];
                        while (-1 != (len = is.read(buffer))) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                } if (null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
