package com.github.gxhunter;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
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
    private final GlobalConfig globalConfig;
    /**
     * 数据源配置
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * 包配置
     */
    private final PackageConfig packageConfig;
    /**
     * 策略配置
     */
    private final StrategyConfig strategyConfig;

    /**
     * 初始化默认行为
     */
    public GeneratorConfig(){
        globalConfig = new GlobalConfig();
        dataSourceConfig = new DataSourceConfig();
        packageConfig = new PackageConfig();
        strategyConfig = new StrategyConfig();

        String outPutDir = System.getProperty("user.dir")+"/src/main/java";
        globalConfig.setOutputDir(outPutDir);
        strategyConfig.setSuperMapperClass(null)
                .setSuperServiceClass(null)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setSuperServiceImplClass(null)
        ;

    }
}
