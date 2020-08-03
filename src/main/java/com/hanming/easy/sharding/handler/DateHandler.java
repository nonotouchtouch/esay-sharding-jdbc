package com.hanming.easy.sharding.handler;

import com.google.common.collect.Range;

import com.hanming.easy.sharding.common.DateUtils;
import com.hanming.easy.sharding.config.Rule;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理器
 *
 * @author hanming.xiao
 * @date 2020-08-03
 */
public class DateHandler implements StrategyHandler {

    /**
     * 按照时间范围，生成后缀
     *
     * @param tableNames         处理前所有table名称
     * @param rule               处理规则
     * @param rangeShardingValue sql解析后的条件值
     * @return 返回拼接后的tableNames集合
     */
    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, RangeShardingValue<String> rangeShardingValue) {
        //时间范围匹配
        return generateSuffixationByRangeTimeValue(tableNames, rangeShardingValue.getValueRange());
    }


    @Override
    public String getExp(Object value) {
        return null;
    }

    /**
     * 根据sql中的条件决定：精确匹配，范围匹配，还是默认往前推一年
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
            //根据精确时间拼接后缀
            return generateSuffixationByPreciseTimeValue(tableNames,
                    (List) complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(column));
        }
        //时间范围匹配，使用between and会进入此分支
        else if (null != complexKeysShardingValue.getColumnNameAndRangeValuesMap().get(column)) {
            return generateSuffixationByRangeTimeValue(tableNames,
                    (Range) complexKeysShardingValue.getColumnNameAndRangeValuesMap().get(column));
        } else {
            Set<String> tableNamesAfterHandling = new LinkedHashSet<>();

            //没有时间条件，默认查找近一年
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            for (String tableNameBeforeHandling : tableNames) {
                calendar.setTime(now);
                for (int i = 0; i < 2; i++) {
                    String tableNameAfter = generateSuffixationByColumnValue(tableNameBeforeHandling, calendar.getTime());
                    tableNamesAfterHandling.add(tableNameAfter);
                    calendar.add(Calendar.MONTH, -1);
                }
            }
            return tableNamesAfterHandling;
        }
    }


    /**
     * 时间范围匹配
     *
     * @param tableNames 处理前的表名
     * @param range      时间范围
     * @return tableNamesAfterHandling
     */
    private Set<String> generateSuffixationByRangeTimeValue(Set<String> tableNames, Range range) {
        Date startTime = DateUtils.getDate(range.lowerEndpoint());
        Date endTime = DateUtils.getDate(range.upperEndpoint());
        Set<String> tableNamesAfterHandling = new LinkedHashSet<>();

        List<String> result = DateUtils.getMonthBetweenForSharding(DateUtils.convertDate(startTime, null), DateUtils.convertDate(endTime, null));
        for (String tableNameBeforeHandling : tableNames) {
            for (String str : result) {
                StringBuilder builder = new StringBuilder(tableNameBeforeHandling);
                builder.append("_").append(str);
                tableNamesAfterHandling.add(builder.toString());
            }
        }
        return tableNamesAfterHandling;
    }


    /**
     * 时间精确匹配
     *
     * @param tableNames           处理前所有table名称
     * @param rule                 处理规则
     * @param preciseShardingValue sql解析后的条件值
     * @return 返回处理好的表名
     */
    @Override
    public Set<String> generateSuffixation(Set<String> tableNames, Rule rule, PreciseShardingValue<String> preciseShardingValue) {
        List<String> columnValue = new LinkedList<>();
        columnValue.add(preciseShardingValue.getValue());
        return generateSuffixationByPreciseTimeValue(tableNames, columnValue);
    }

    /**
     * 根据精确时间拼接后缀
     *
     * @param tableNames 处理前的表名
     * @param columnValue 分表主键值
     * @return tableNamesAfterHandling 处理后的表名
     */
    private Set<String> generateSuffixationByPreciseTimeValue(Set<String> tableNames, List columnValue) {
        //存放处理后的表名
        Set<String> tableNamesAfterHandling = new LinkedHashSet<>();
        for (String tableNameBeforeHandling : tableNames) {
            for (Object value : columnValue) {
                String tableNameAfter = generateSuffixationByColumnValue(tableNameBeforeHandling, value);
                tableNamesAfterHandling.add(tableNameAfter);
            }
        }
        return tableNamesAfterHandling;
    }


    /**
     * 根据原表名+时间获取新的表名
     *
     * @param tableNameBeforeHandling 原表明
     * @param dataValue               时间，用于生成后缀
     * @return 处理后表名
     */
    private String generateSuffixationByColumnValue(String tableNameBeforeHandling, Object dataValue) {
        if (dataValue instanceof String) {
            SimpleDateFormat sufFormat = new SimpleDateFormat("yyMM");
            String str = sufFormat.format(DateUtils.getDateByStandardStr(dataValue.toString()));
            return tableNameBeforeHandling + "_" + str;
        }
        SimpleDateFormat sufFormat = new SimpleDateFormat("yyMM");
        String str = sufFormat.format(dataValue);
        return tableNameBeforeHandling + "_" + str;
    }


}
