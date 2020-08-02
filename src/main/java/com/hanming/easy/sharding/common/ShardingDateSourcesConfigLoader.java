package com.hanming.easy.sharding.common;

import com.hanming.easy.sharding.config.Rule;
import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import com.hanming.easy.sharding.config.Table;
import com.hanming.easy.sharding.enums.StrategyEnum;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
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
     * 通过全路径解析
     *
     * @param filePath
     * @return
     */
    public static ShardingDateSourcesConfig loadSingleFileByPath(String filePath) throws IOException {
        Map<String, Object> ymlMap = (Map<String, Object>) YamlLoader.loadYaml(filePath);

        return createShardingDateSourcesConfig(ymlMap);
    }

    /**
     * 通过yml解析器返回生成ShardingDateSourcesConfig
     *
     * @param ymlMap
     * @return ShardingDateSourcesConfig
     */
    private static ShardingDateSourcesConfig createShardingDateSourcesConfig(Map<String, Object> ymlMap) throws IOException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = new ShardingDateSourcesConfig();
        shardingDateSourcesConfig.setShardingDataSourcesName((String) ymlMap.get("shardingDataSourcesName"));
        ArrayList<String> dataSources = (ArrayList<String>) ymlMap.get("datasources");
        for (String dataSource : dataSources) {
            shardingDateSourcesConfig.getDataSourceMap().put(dataSource, DataSourcesUtil.getDataSourceFromDefaultPath(dataSource));
        }
        Map<String, Map> rules = (Map<String, Map>) ymlMap.get("rules");
        for (String ruleName : rules.keySet()) {
            shardingDateSourcesConfig.getRuleMap().put(ruleName, new Rule(ruleName, StrategyEnum.valueOf((String) rules.get(ruleName).get("strategy")),
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

    /**
     * 解析默认路径下所有配置
     *
     * @return
     */
    public static Map<String, ShardingDateSourcesConfig> loadAllByDefaultPath() {
        return null;
    }

}
