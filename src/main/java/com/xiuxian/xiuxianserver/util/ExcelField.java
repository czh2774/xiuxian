package com.xiuxian.xiuxianserver.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 仅应用于类
public @interface ExcelField {
    // 你可以在这里添加其他元数据（如表名、顺序等）
}
