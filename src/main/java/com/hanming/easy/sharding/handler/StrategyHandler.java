package com.hanming.easy.sharding.handler;

import com.hanming.easy.sharding.config.Rule;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Set;

/**
 * 后缀处理器
 *
 * @author hanming.xiao
 * @date 2020-08-03
 */
public interface StrategyHandler {

    /**
     * 获取表达式
     * @return
     */
    String getExp(Object value);

    /**
     * 复杂分片算法
     *
     * @param tableNames               处理前所有table名称
     * @param rule                     处理规则
     * @param complexKeysShardingValue sql解析后的条件值
     * @return 返回拼接后的tableNames集合
     */
    Set<String> generateSuffixation(Set<String> tableNames, Rule rule, ComplexKeysShardingValue complexKeysShardingValue);

    /**
     * 精确分片算法
     *
     * @param tableNames           处理前所有table名称
     * @param rule                 处理规则
     * @param preciseShardingValue sql解析后的条件值
     * @return 返回拼接后的tableNames集合
     */
    Set<String> generateSuffixation(Set<String> tableNames, Rule rule, PreciseShardingValue<String> preciseShardingValue);

    /**
     * 范围分片算法
     *
     * @param tableNames    处理前所有table名称
     * @param rule          处理规则
     * @param shardingValue sql解析后的条件值
     * @return 返回拼接后的tableNames集合
     */
    Set<String> generateSuffixation(Set<String> tableNames, Rule rule, RangeShardingValue<String> shardingValue);

}
