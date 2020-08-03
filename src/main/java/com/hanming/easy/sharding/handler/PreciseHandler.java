package com.hanming.easy.sharding.handler;

import com.hanming.easy.sharding.config.Rule;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shenglong.tao
 * @version 1.0
 * @Copyright: Copyright (c)2019
 * @company 易宝支付(YeePay)
 * @since 2020/3/20
 */
public class PreciseHandler implements StrategyHandler {

    @Override
    public String getExp(Object value) {
        return null;
    }

    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, ComplexKeysShardingValue complexKeysShardingValue) {
        String column = null;
        if (null!=rule.getColumn()&&!"".equals(rule.getColumn().trim())) {
            column = rule.getColumn().toLowerCase();
        }
        if (null == complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column)) {
            column = rule.getColumn().toUpperCase();
        }
        //精确匹配
        Set<String> tableNamesAfterHandling = new LinkedHashSet<>();
        if (null != complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column)) {
            List<String> columnValue = (List<String>) complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column);
            //拿到表名，循环不同的值，拼好后缀
            for (String tableNameBeforeHandling : tableNames) {
                for (String value : columnValue) {
                    String tableNameAfter = tableNameBeforeHandling + "_" + value;
                    tableNamesAfterHandling.add(tableNameAfter);
                }
            }
            return tableNamesAfterHandling;
        }
        return tableNames;
    }

    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, PreciseShardingValue<String> preciseShardingValue) {
        Set<String> tableNamesAfterHandling = new LinkedHashSet<>();
        //拿到表名，循环第一个变量，拼好后缀
        for (String tableNameBeforeHandling : tableNames) {
            if (null != preciseShardingValue.getValue()) {
                tableNamesAfterHandling.add(tableNameBeforeHandling + "_" + String.valueOf(preciseShardingValue.getValue()));
            }
        }
        return tableNamesAfterHandling;
    }

    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, RangeShardingValue<String> shardingValue) {
        return null;
    }


}
