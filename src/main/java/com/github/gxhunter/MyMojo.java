package com.github.gxhunter;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 代码生成器
 */
@Mojo(name = "generate")
public class MyMojo extends AbstractMojo{

    @Override
    public void execute(){
        System.out.println("hello world");
    }
}
