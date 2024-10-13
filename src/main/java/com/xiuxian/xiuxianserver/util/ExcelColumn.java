package com.xiuxian.xiuxianserver.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标注实体类的字段，用于生成Excel表头和注释。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    String headerName();  // Excel列的表头名称
    String comment();     // Excel列的注释
}
