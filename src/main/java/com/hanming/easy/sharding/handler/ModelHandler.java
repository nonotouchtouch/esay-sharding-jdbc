package com.hanming.easy.sharding.handler;

import com.hanming.easy.sharding.config.Rule;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;

/**
 * 取模处理器
 *
 * @author hanming.xiao
 * @date 2020-08-03
 */
public class ModelHandler implements StrategyHandler {

    @Override
    public String getExp(Object value) {
        Integer model =(Integer)value;
        int maxSuff=model-1;
        return "${0.."+maxSuff+"}";
    }

    /**
     * 复杂分表对取模的处理
     *
     * @param tableNames               处理前所有table名称
     * @param rule                     处理规则
     * @param complexKeysShardingValue sql解析后的条件值
     * @return 返回拼接后的tableNames集合
     */
    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, ComplexKeysShardingValue complexKeysShardingValue) {
        String column = null;
        if(null!=rule.getColumn()&&!"".equals(rule.getColumn().trim())){
            column = rule.getColumn().toLowerCase();
        }
        if(null == complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column)){
            column = rule.getColumn().toUpperCase();
        }
        //精确匹配
        if (null != complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column)) {
            Set<String> tableNamesAfterHandling = new LinkedHashSet<>();
            List<String> columnValue = (List<String>) complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column);
            //拿到表名，循环不同的值，拼好后缀
            for (String tableNameBeforeHandling : tableNames) {
                for (String value : columnValue) {
                    String tableNameAfter = generateSuffixationByColumnValue(tableNameBeforeHandling, value, (Integer)rule.getValue());
                    tableNamesAfterHandling.add(tableNameAfter);
                }
            }
            return tableNamesAfterHandling;
        }
        //范围匹配，返回所有表
        else {
            return generateSuffixationByRange(tableNames, rule);
        }
    }

    /**
     * 取模，精确匹配
     *
     * @param tableNames           处理前所有table名称
     * @param rule                 处理规则
     * @param preciseShardingValue sql解析后的条件值
     * @return
     */
    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, PreciseShardingValue<String> preciseShardingValue) {
        Set<String> tableNamesAfterHandling = new LinkedHashSet<>();
        //拿到表名，循环第一个变量，拼好后缀
        for (String tableNameBeforeHandling : tableNames) {
            String tableNameAfter = generateSuffixationByColumnValue(tableNameBeforeHandling, preciseShardingValue.getValue(), (Integer)rule.getValue());
            tableNamesAfterHandling.add(tableNameAfter);
        }
        return tableNamesAfterHandling;
    }

    /**
     * 取模 范围匹配
     *
     * @param tableNames    处理前所有table名称
     * @param rule          处理规则
     * @param shardingValue sql解析后的条件值
     * @return
     */
    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, RangeShardingValue<String> shardingValue) {
        return generateSuffixationByRange(tableNames, rule);
    }



    /**
     * 取模分表 如果有区间，默认路由所有分表
     *
     * @param tableNames 路由前分表
     * @param rule       路由规则
     * @return 路由后的分表
     */
    private Set<String> generateSuffixationByRange(Set<String> tableNames, Rule rule) {
        Set<String> tableNamesAfterHandling = new LinkedHashSet<>();

        for (String tableNameBeforeHandling : tableNames) {
            for (int i = 0; i < (Integer) rule.getValue(); i++) {
                String tableNameAfter = generateSuffixationByColumnValue(tableNameBeforeHandling, i, (Integer)rule.getValue());
                tableNamesAfterHandling.add(tableNameAfter);
            }
        }
        return tableNamesAfterHandling;
    }

    /**
     * 根据原表名，分表字段值，取模处理并返回处理后的表名
     *
     * @param tableNameBeforeHandling 原表名
     * @param columnValue             分表主键值
     * @param ruleConf                取模数
     * @return 处理后的表名
     */
    private String generateSuffixationByColumnValue(String tableNameBeforeHandling, Object columnValue, Integer ruleConf) {

        if (columnValue instanceof Integer) {
            return tableNameBeforeHandling + "_" + (Integer)columnValue % ruleConf;
        }

        //如果全部为数字直接取模，如果包含字母，使用hashCode取模
        if (columnValue.toString().matches("[0-9]+")) {
            return tableNameBeforeHandling + "_" + Long.valueOf(columnValue.toString()) % ruleConf;
        } else {
            return tableNameBeforeHandling + "_" + Math.abs(Objects.hashCode(columnValue) % ruleConf);
        }
    }

}
