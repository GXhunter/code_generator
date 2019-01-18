package com.github.gxhunter;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * @author 树荫下的天空
 * @date 2019/1/18 22:18
 */
public class Runner{
    public void start() throws IOException{
        ClassPathResource resource = new ClassPathResource("generator.json");
        File filePath = resource.getFile();

        //读取文件
        String input = FileUtils.readFileToString(filePath, "UTF-8");
        //将读取的数据转换为JSONObject
        GeneratorConfig res = JsonUtil.parse(input,GeneratorConfig.class);

        System.out.println(res);
    }

    public static void main(String[] args) throws IOException{
        new Runner().start();
    }
}
