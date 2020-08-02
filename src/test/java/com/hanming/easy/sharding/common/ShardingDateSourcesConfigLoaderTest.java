package com.hanming.easy.sharding.common;

import com.hanming.easy.sharding.config.ShardingDateSourcesConfig;
import org.junit.Test;

import java.io.IOException;

public class ShardingDateSourcesConfigLoaderTest {

    @Test
    public void baseTest() throws IOException {
        ShardingDateSourcesConfig shardingDateSourcesConfig = ShardingDateSourcesConfigLoader.loadSingleFileByPath(ShardingDateSourcesConfigLoader.DEFULT_PATH+"shardingTableExample.yml");
        System.out.println(shardingDateSourcesConfig);
    }
}
