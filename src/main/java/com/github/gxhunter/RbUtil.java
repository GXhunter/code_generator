package com.github.gxhunter;


import java.util.ResourceBundle;

/**
 * @author 树荫下的天空
 * @date 2019/1/18 17:20
 */
public class RbUtil{
    ResourceBundle mResourceBundle;

    public RbUtil(String profile){
        mResourceBundle = ResourceBundle.getBundle(profile);
        if(mResourceBundle == null){
            throw new RuntimeException("没有指定的文件" + profile);
        }
    }

    public String getString(String key,String defaultVal){
        try{
            return mResourceBundle.getString(key);
        }catch(Exception e){
            return defaultVal;
        }
    }

    public String getString(String key){
        return getString(key,null);
    }

}
