package com.hanming.easy.sharding;



import com.hanming.easy.sharding.algorithm.EasyPreciseShardingAlgorithm;
import com.hanming.easy.sharding.common.CommonUtil;
import com.hanming.easy.sharding.common.ShardingException;
import com.hanming.easy.sharding.config.Config;
import com.hanming.easy.sharding.config.Rule;
import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import com.hanming.easy.sharding.config.Table;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 几种使用sharding的方式
 *
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class EasyShardingFactory implements EasySharding {


    /**
     * 根据配置生成sharding数据源(使用默认方式配置数据源)
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     *
     * @param shardingDataSourceName 配置的sharding数据源名称（yml中配置）
     * @return DataSource sharding数据源
     */
    @Override
    public DataSource createDataSource(String shardingDataSourceName) throws SQLException, ShardingException {
        //获取yml配置
        ShardingDateSourcesConfig shardingDateSourcesConfig = Config.getStringShardingDateSourcesConfig(shardingDataSourceName);
        //数据分片
        ShardingRuleConfiguration shardingRuleConfiguration=createFromShardingDateSourcesConfig(shardingDateSourcesConfig);
        //使用sharding API获取sharding数据源
        return ShardingDataSourceFactory.createDataSource(shardingDateSourcesConfig.getDataSourceMap(),shardingRuleConfiguration,shardingDateSourcesConfig.getProperties());
    }


    /**
     * 根据配置生成sharding数据源
     * 如果数据源配置方式和默认的不一样，手动传入基础数据源
     * 分库情况下，把不同dataSources包装成一个sharding数据源
     *
     * @param dataSourceMap  数据源（分库的情况下有多个）
     * @param configFileName 配置文件名称，配置文件需放在默认目录下
     * @return DataSource sharding数据源
     */
    @Override
    public DataSource createDataSource(Map<String, DataSource> dataSourceMap, String configFileName) {
        return null;
}


    /**
     * 根据yml配置转换成sharding-jdbc的配置
     * 官方文档说明
     * https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/configuration/java-api/sharding/
     * @param shardingDateSourcesConfig
     * @return
     */
    private ShardingRuleConfiguration createFromShardingDateSourcesConfig(ShardingDateSourcesConfig shardingDateSourcesConfig) {
        ShardingRuleConfiguration shardingRuleConfiguration=new ShardingRuleConfiguration();
        //分片表规则列表
        createTableRuleConfigs(shardingRuleConfiguration,shardingDateSourcesConfig);
        //其他配置
        return shardingRuleConfiguration;
    }

    /**
     * 分片表规则列表
     *      Collection<TableRuleConfiguration> tableRuleConfigs = new LinkedList();
     *      Collection<String> bindingTableGroups = new LinkedList();
     *      Collection<String> broadcastTables = new LinkedList();
     *      String defaultDataSourceName;
     *      ShardingStrategyConfiguration defaultDatabaseShardingStrategyConfig;
     *      ShardingStrategyConfiguration defaultTableShardingStrategyConfig;
     *      KeyGeneratorConfiguration defaultKeyGeneratorConfig;
     *      Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfigs = new LinkedList();
     *      EncryptRuleConfiguration encryptRuleConfig;
     * @param shardingRuleConfiguration
     * @param shardingDateSourcesConfig
     */
    private void createTableRuleConfigs(ShardingRuleConfiguration shardingRuleConfiguration, ShardingDateSourcesConfig shardingDateSourcesConfig) {
        //真实数据库名称生成前缀
        String dataSourcesStr= CommonUtil.getInLineStr(shardingDateSourcesConfig.getDataSourceMap());



        //每个表配置规则 tableRuleConfigs
        for (String tableName:shardingDateSourcesConfig.getTableMap().keySet()) {
            StringBuilder builder=new StringBuilder(dataSourcesStr).append(".").append(tableName);

            //逻辑表配置
            Table table = shardingDateSourcesConfig.getTableMap().get(tableName);

            //所有分表字段
            StringBuilder column =new StringBuilder();

            //使用的规则
            List<String> ruleNames = table.getTableShardingRuleNames();
            if(null!=ruleNames && !ruleNames.isEmpty()){
                for (String ruleName: ruleNames) {
                    Rule rule=shardingDateSourcesConfig.getRuleMap().get(ruleName);
                    if(null!=rule.getColumn()&&!"".equals(rule.getColumn().trim())){
                        //根据分表字段顺序拼接表名
                        column.append(rule.getColumn()).append(",");
                    }
                    String exp=rule.getStrategyEnum().getExp().replace("VALUE",rule.getValue().toString());
                    builder.append("_").append(exp);
                }

            }

            TableRuleConfiguration tableRuleConfiguration=new TableRuleConfiguration(tableName,builder.toString());

            String columnStr=column.substring(0,column.length()-2);
            if(null!=table.getTableShardingRuleNames()) {
                if (1 == table.getTableShardingRuleNames().size()){
                    tableRuleConfiguration.setTableShardingStrategyConfig(
                            new StandardShardingStrategyConfiguration(columnStr,new EasyPreciseShardingAlgorithm(shardingDateSourcesConfig)));

                }else {
                    //tableRuleConfiguration.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration(columnStr,new ComplexKeysShardingAlgorithm()));
                }
            }
            shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
        }


    }

}
