package com.hanming.easy.sharding.common;

import com.hanming.easy.sharding.config.Rule;
import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import com.hanming.easy.sharding.config.Table;
import com.hanming.easy.sharding.enums.StrategyEnum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * yml解析器
 * 用于解析sharding数据源配置
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class ShardingDateSourcesConfigLoader {


    public static final String DEFULT_PATH = "shardingconf/";

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
     */
    private static void loadAllByPath(String path, Map<String, ShardingDateSourcesConfig> map) throws ShardingException {
        File file=new File(Constant.PROJECT_PATH+path);

        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                loadAllByPath(path+subFile.getName(), map);
            }
        } else {
            ShardingDateSourcesConfig shardingDateSourcesConfig = loadSingleFileByPath(path);
            ShardingDateSourcesConfig alreadyExistConf = map.put(shardingDateSourcesConfig.getShardingDataSourcesName(), shardingDateSourcesConfig);
            if (null!=alreadyExistConf){
                throw new ShardingException(ShardingException.CONFIG_EXIST+alreadyExistConf.getShardingDataSourcesName());

            }

        }
    }


    /**
     * 通过项目下路径解析
     *
     * @param filePath 相对路径
     * @return
     */
    public static ShardingDateSourcesConfig loadSingleFileByPath(String filePath) throws ShardingException {
        Map<String, Object> ymlMap = (Map<String, Object>) YamlLoader.loadYaml(filePath);
        return createShardingDateSourcesConfig(ymlMap);
    }


    /**
     * 通过yml解析器返回生成ShardingDateSourcesConfig
     *
     * @param ymlMap
     * @return ShardingDateSourcesConfig
     */
    private static ShardingDateSourcesConfig createShardingDateSourcesConfig(Map<String, Object> ymlMap) throws ShardingException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = new ShardingDateSourcesConfig();
        shardingDateSourcesConfig.setShardingDataSourcesName((String) ymlMap.get("shardingDataSourcesName"));
        ArrayList<String> dataSources = (ArrayList<String>) ymlMap.get("datasources");
        for (String dataSource : dataSources) {
            try {
                shardingDateSourcesConfig.getDataSourceMap().put(dataSource, DataSourcesUtil.getDataSourceFromDefaultPath(dataSource));
            } catch (IOException e) {
                throw new ShardingException(ShardingException.DATA_SOURCE_FAIL+dataSource);
            }
        }
        Map<String, Map> rules = (Map<String, Map>) ymlMap.get("rules");
        for (String ruleName : rules.keySet()) {
            shardingDateSourcesConfig.getRuleMap().put(ruleName, new Rule(ruleName,(String) rules.get(ruleName).get("strategy"),
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
