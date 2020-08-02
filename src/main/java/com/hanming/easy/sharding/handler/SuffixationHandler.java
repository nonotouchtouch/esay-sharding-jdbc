package com.hanming.easy.sharding.handler;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;

/**
 * 后缀处理器
 */
public interface SuffixationHandler extends PreciseShardingAlgorithm, RangeShardingAlgorithm, ComplexKeysShardingAlgorithm {
}
