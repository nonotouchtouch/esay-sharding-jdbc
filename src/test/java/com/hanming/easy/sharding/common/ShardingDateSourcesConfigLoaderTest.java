package com.hanming.easy.sharding.common;

import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import org.junit.Test;

import java.util.Map;

public class ShardingDateSourcesConfigLoaderTest {

    @Test
    public void baseTest() throws ShardingException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = ShardingDateSourcesConfigLoader.loadSingleFileByPath(ShardingDateSourcesConfigLoader.DEFULT_PATH+"shardingTableExample.yml");
        System.out.println(shardingDateSourcesConfig);
    }

    @Test
    public void baseTest2() throws ShardingException {
        Map<String, ShardingDateSourcesConfig> stringShardingDateSourcesConfigMap = ShardingDateSourcesConfigLoader.loadAllByDefaultPath();
        System.out.println(stringShardingDateSourcesConfigMap);
    }
}
