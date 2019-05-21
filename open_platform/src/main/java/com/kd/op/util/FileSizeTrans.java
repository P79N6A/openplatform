package com.kd.op.util;

/**
 * @Auther:张健云
 * @Description：文件大小转换类
 * @DATE：2019/1/11 10:56
 */
public class FileSizeTrans {

    private static final Integer ONE_KB = 1024;
    public static Long transByteToKB(Long size){
        Long kbSize = 1l;
        if(size>ONE_KB){
            kbSize = size / ONE_KB;
        }
        return kbSize;
    }
}
