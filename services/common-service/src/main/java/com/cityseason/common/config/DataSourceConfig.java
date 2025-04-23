package com.cityseason.common.config;

import com.baomidou.dynamic.datasource.plugin.MasterSlaveAutoRoutingPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    /**
     * 自动切换数据源插件
     * 作用：
     * - 执行 SELECT 语句时，自动切换到从库
     * - 执行 INSERT、UPDATE、DELETE 语句时，自动切换到主库
     */
    @Bean
    public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin() {
        return new MasterSlaveAutoRoutingPlugin();
    }
}
