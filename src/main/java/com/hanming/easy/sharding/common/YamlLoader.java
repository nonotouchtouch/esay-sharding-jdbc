package com.hanming.easy.sharding.common;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * ymal文件解析器
 *
 * @author hanming.xiao
 * @date 2020-08-02
 */
public class YamlLoader {

    /**
     * 根据文件路径解析（相对路径）
     *
     * @param filePath
     * @return
     */
    public static Map<String, Object> loadYaml(String filePath) throws ShardingException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        if (null == in) {
            throw new ShardingException(ShardingException.FILE_NOT_EXIST + filePath);
        }
        return new Yaml().load(in);
    }
}
