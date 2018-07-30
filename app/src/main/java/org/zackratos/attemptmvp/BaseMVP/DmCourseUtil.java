package org.zackratos.attemptmvp.BaseMVP;

import java.math.BigDecimal;

/**
 * Created by leaf on 2018/6/4
 */

public class DmCourseUtil {

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2mb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
        return (returnValue + "MB");
    }
}
