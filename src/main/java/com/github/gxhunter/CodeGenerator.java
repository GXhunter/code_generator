package com.github.gxhunter;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author 树荫下的天空
 * @date 2019/1/14 14:35
 * 代码生成器
 */
@Mojo(name = "generate")
public class CodeGenerator extends AbstractMojo{

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private String scanner(String tip){
        System.out.println(tip + "：");
        try(
                Scanner scanner = new Scanner(System.in);
        ){
            if(scanner.hasNext()){
                String ipt = scanner.next();
                if(StringUtils.isNotEmpty(ipt)){
                    return ipt;
                }
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    /**
     * 获取指定前缀的表名
     *
     * @param prefix
     * @return
     * @throws Exception
     */
    private List<String> exec(DataSourceConfig dataSourceConfig,String... prefix) throws Exception{
        List<String> list = new ArrayList<>();
        try(
                Connection conn = DriverManager.getConnection(dataSourceConfig.getUrl(),dataSourceConfig.getUsername(),dataSourceConfig.getPassword());
                PreparedStatement ps = conn.prepareStatement("show tables");
                ResultSet rs = ps.executeQuery()
        ){
            while(rs.next()){
                String tableName = rs.getString(1);
                if(prefix != null){
                    for(String aPrefix : prefix){
                        if(tableName.startsWith(aPrefix)){
                            list.add(rs.getString(1));
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException{
        String input;
        try{
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/generator.json");
            input = FileUtils.readFileToString(file,"UTF-8");
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        //读取文件
        //将读取的数据转换为JSONObject
        GeneratorConfig config = JsonUtil.parse(input,GeneratorConfig.class);
        assert config != null;

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(config.getGlobalConfig());

        // 数据源配置
        mpg.setDataSource(config.getDataSourceConfig());
        // 包配置
        mpg.setPackageInfo(config.getPackageConfig());

        // 策略配置
        StrategyConfig strategy = config.getStrategyConfig();
        String tbName = scanner(String.format("请输入表名，输入all逆向所有%s前缀的表格",config.getStrategyConfig().getTablePrefix()));
        if("all".equals(tbName)){
            try{
                List<String> all = exec(config.getDataSourceConfig(),config.getStrategyConfig().getTablePrefix());
                strategy.setInclude(all.toArray(new String[all.size()]));
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            strategy.setInclude(tbName);
        }

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
