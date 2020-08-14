package com.hanming.easy.sharding.common;

import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * ShardingDateSourcesConfig生成器，用于解析sharding数据源配置
 *
 * 未来扩展支持解析多种配置方式
 *
 * 1.yml解析器
 *  1.1 loadAllByDefaultPath()解析默认目录下yml配置
 *  1.2 loadAllByPath(String path, Map<String, ShardingDateSourcesConfig> map) 解析指定目录下所有yml文件
 *  1.3 loadSingleFileByPath(String filePath) 解析单个yml文件
 *
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class ShardingDateSourcesConfigLoaderTest {


    /**
     * 默认路径解析
     * @throws ShardingException
     */
    @Test
    public void baseTest1() throws ShardingException {
        Map<String, ShardingDateSourcesConfig> stringShardingDateSourcesConfigMap = ShardingDateSourcesConfigLoader.loadAllByDefaultPath();
        System.out.println(stringShardingDateSourcesConfigMap);
    }

    @Test
    public void baseTest2() throws ShardingException {
        Map map=new HashMap();
        ShardingDateSourcesConfigLoader.loadAllByPath(null,map);
        System.out.println(map);
    }


    @Test
    public void baseTest3() throws ShardingException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = ShardingDateSourcesConfigLoader.loadSingleFileByPath(ShardingDateSourcesConfigLoader.DEFULT_PATH+"shardingTableExample.yml");
        System.out.println(shardingDateSourcesConfig);
    }


}
