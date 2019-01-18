package com.github.gxhunter;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author 树荫下的天空
 * @date 2019/1/14 14:35
 */
public class CodeGenerator{

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help.toString());
        if(scanner.hasNext()){
            String ipt = scanner.next();
            if(ipt.contains("all_")){
                ipt = ipt.replace("all_","");
            }else if(StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args){
        GeneratorConfig generatorConfig = new GeneratorConfig();
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        RbUtil rb = new RbUtil("generator.properties");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);
        gc.setAuthor(rb.getString("author"));
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(rb.getString("url"));
        dsc.setDriverName(rb.getString("driverName","com.mysql.jdbc.Driver"));
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(rb.getString("moduleName"));
        pc.setParent(rb.getString("parent"));
        pc.setEntity("po");
        pc.setXml("mapper");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(rb.getString("basePO"));
        strategy.setSuperEntityColumns(rb.getString("baseColumns").split(","));
        strategy.setEntityLombokModel(true);
        strategy.setSuperControllerClass(rb.getString("baseController"));
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(rb.getString("tablePrefix"));
//		strategy.setSuperControllerClass(rb.getString("baseController"));
        String tbName = scanner(String.format("表名，输入all逆向所有%s前缀的表格",rb.getString("tablePrefix")));


        if("all".equals(tbName)){
            try{
                List<String> all = CodeGenerator.exec(dsc.getUrl(),dsc.getUsername(),dsc.getPassword(),
                        strategy.getTablePrefix());
                strategy.setInclude(all.toArray(new String[all.size()]));
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            strategy.setInclude(tbName);
        }
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        覆写自带模板
        mpg.setTemplate(new TemplateConfig().setController("/controller.java"));
        mpg.execute();
    }

    /**
     * 获取指定前缀的表名
     * @param url
     * @param name
     * @param passwd
     * @param prefix
     * @return
     * @throws Exception
     */
    private static List<String> exec(String url,String name,String passwd,String[] prefix) throws Exception{
        Connection conn;
        List<String> list = new ArrayList<>();

        conn = DriverManager.getConnection(url,name,passwd);
        PreparedStatement ps = conn.prepareStatement("show tables");
        ResultSet rs = ps.executeQuery();
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
        conn.close();
        return list;
    }
}
