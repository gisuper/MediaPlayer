package com.yangxiong.gisuper.mideaplayer.video;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by yangxiong on 2017/7/2/002.
 */

public class VideoUtil

{

    /**
     * 格式化的Builder
     */
    private static StringBuilder sFormatBuilder = new StringBuilder( );
    /**
     * 格式化的Formatter
     */
    private static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault( ));
    /**
     * 格式化的相关属性
     */
    private static final Object[] sTimeArgs = new Object[3];

    /**
     * 转换进度值为时间
     *
     * @param secs
     * @return
     */
    public static String makeTimeString(int secs) {
        /**
         * %[argument_index$][flags][width]conversion 可选的
         * argument_index 是一个十进制整数，用于表明参数在参数列表中的位置。第一个参数由 "1$"
         * 引用，第二个参数由 "2$" 引用，依此类推。 可选 flags
         * 是修改输出格式的字符集。有效标志集取决于转换类型。 可选 width
         * 是一个非负十进制整数，表明要向输出中写入的最少字符数。 可选 precision
         * 是一个非负十进制整数，通常用来限制字符数。特定行为取决于转换类型。 所需 conversion
         * 是一个表明应该如何格式化参数的字符。给定参数的有效转换集取决于参数的数据类型。
         */
        String durationformat = "%1$02d:%2$02d:%3$02d";// <xliff:g
        // id="format">%1$02d:%2$02d:%3$02d</xliff:g>
        sFormatBuilder.setLength(0);
        secs = secs / 1000;
        Object[] timeArgs = sTimeArgs;
        timeArgs[0] = secs / 3600; // 秒
        timeArgs[1] = (secs % 3600) / 60; // 分
        timeArgs[2] = (secs % 3600 % 60) % 60; // 时
        return sFormatter.format(durationformat, timeArgs).toString( ).trim( );
    }


}