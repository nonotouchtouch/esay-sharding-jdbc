package com.hanming.easy.sharding.common;

import java.util.Set;

/**
 *
 */
public class CommonUtil {

    /**
     * 根据yml配置生成sharding原生配置
     * @param dataSourceNames
     * @return
     */
    public static String getInLineStr(Set<String> dataSourceNames) {
        StringBuffer stringBuffer=new StringBuffer("${[");
        for (String dataSourcesName:dataSourceNames) {
            stringBuffer.append("'").append(dataSourcesName).append("',");
        }
        String str=stringBuffer.substring(0,stringBuffer.length()-1);
        stringBuffer.setLength(0);
        stringBuffer.append(str).append("]}");
        return stringBuffer.toString();
    }


}
