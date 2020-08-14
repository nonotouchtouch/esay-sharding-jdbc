package com.hanming.easy.sharding.common;

import com.hanming.easy.sharding.config.Rule;
import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import com.hanming.easy.sharding.config.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ShardingDateSourcesConfig生成器，用于解析sharding数据源配置
 * <p>
 * 未来扩展支持解析多种配置方式
 * <p>
 * 1.yml解析器
 * 1.1 loadAllByDefaultPath()解析默认目录下yml配置
 * 1.2 loadAllByPath(String path, Map<String, ShardingDateSourcesConfig> map) 解析指定目录下所有yml文件
 * 1.3 loadSingleFileByPath(String filePath) 解析单个yml文件
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class ShardingDateSourcesConfigLoader {


    /**
     * 默认配置文件存放路径
     */
    public static final String DEFULT_PATH = "shardingconf";

    /**
     * 解析默认路径下所有配置
     *
     * @return sharding数据源配置 map
     */
    public static Map<String, ShardingDateSourcesConfig> loadAllByDefaultPath() throws ShardingException {
        Map<String, ShardingDateSourcesConfig> map = new HashMap<>(16);
        loadAllByPath(DEFULT_PATH, map);
        return map;
    }

    /**
     * 解析路径下所有文件
     *
     * @param path
     * @param map
     * @return
     * @throws ShardingException sharding数据源名称配置重复
     */
    public static void loadAllByPath(String path, Map<String, ShardingDateSourcesConfig> map) throws ShardingException {
        if (null == map) {
            throw new IllegalArgumentException("loadAllByPath fail! map can not be null!");
        }
        File file = new File(Constant.PROJECT_PATH + path);

        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                loadAllByPath(path + "/" + subFile.getName(), map);
            }
        } else {
            ShardingDateSourcesConfig shardingDateSourcesConfig = loadSingleFileByPath(path);
            ShardingDateSourcesConfig alreadyExistConf = map.put(shardingDateSourcesConfig.getShardingDataSourcesName(), shardingDateSourcesConfig);
            if (null != alreadyExistConf) {
                throw new ShardingException(ShardingException.CONFIG_EXIST + alreadyExistConf.getShardingDataSourcesName());
            }
        }
    }


    /**
     * 通过项目下路径解析
     *
     * @param filePath 相对路径
     * @return ShardingDateSourcesConfig
     */
    public static ShardingDateSourcesConfig loadSingleFileByPath(String filePath) throws ShardingException {
        if (null == filePath) {
            throw new ShardingException(ShardingException.FILE_NOT_EXIST + " null");
        }
        Map<String, Object> ymlMap = YamlLoader.loadYaml(filePath);
        return createShardingDateSourcesConfig(ymlMap);
    }


    /**
     * 通过yml解析器返回生成ShardingDateSourcesConfig
     *
     * @param ymlMap
     * @return ShardingDateSourcesConfig
     */
    private static ShardingDateSourcesConfig createShardingDateSourcesConfig(Map<String, Object> ymlMap) throws ShardingException {
        //创建ShardingDateSourcesConfig对象，每个对象对应一个sharding数据源（因为这个方法是yml解析调过来的，所以这里也对应一个yml文件）
        ShardingDateSourcesConfig shardingDateSourcesConfig = new ShardingDateSourcesConfig((String) ymlMap.get("shardingDataSourcesName"));
        //获取真实数据源列表
        ArrayList<String> dataSources = (ArrayList<String>) ymlMap.get("datasources");
        shardingDateSourcesConfig.getDataSourcesNames().addAll(dataSources);


        Map<String, Map> rules = (Map<String, Map>) ymlMap.get("rules");
        for (String ruleName : rules.keySet()) {
            shardingDateSourcesConfig.getRuleMap().put(ruleName, new Rule(ruleName, (String) rules.get(ruleName).get("strategy"),
                    (String) rules.get(ruleName).get("columm"), rules.get(ruleName).get("value")));
        }
        Map<String, Map> tables = (Map<String, Map>) ymlMap.get("tables");
        for (String tableName : tables.keySet()) {
            shardingDateSourcesConfig.getTableMap().put(tableName, new Table(tableName,
                    (ArrayList<String>) tables.get(tableName).get("tableShardingRuleNames"),
                    (ArrayList<String>) tables.get(tableName).get("dataSourcesShardingRuleNames")));
        }

        return shardingDateSourcesConfig;
    }

}
