package com.hanming.easy.sharding.common;

import javax.sql.DataSource;
import java.util.Map;

/**
 *
 */
public class CommonUtil {

    /**
     * 根据yml配置生成sharding原生配置
     * @param dataSourceMap
     * @return
     */
    public static String getInLineStr(Map<String, DataSource> dataSourceMap) {
        StringBuffer stringBuffer=new StringBuffer("${[");
        for (String dataSourcesName:dataSourceMap.keySet()) {
            stringBuffer.append("'").append(dataSourcesName).append("',");
        }
        String str=stringBuffer.substring(0,stringBuffer.length()-1);
        stringBuffer.setLength(0);
        stringBuffer.append(str).append("]}");
        return stringBuffer.toString();
    }


}
