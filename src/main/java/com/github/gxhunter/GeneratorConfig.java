package com.github.gxhunter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import lombok.Data;

/**
 * @author 树荫下的天空
 * @date 2019/1/18 22:29
 * 代码生成器配置
 */
@Data
public class GeneratorConfig{
    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;
    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig;
    /**
     * 包配置
     */
    private PackageConfig packageConfig;
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /*public GeneratorConfig(){
        globalConfig = new GlobalConfig();
        dataSourceConfig = new DataSourceConfig();
        packageConfig = new PackageConfig();
        strategyConfig = new StrategyConfig();

        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver")
                .setDbType(DbType.MYSQL);
    }*/
}
