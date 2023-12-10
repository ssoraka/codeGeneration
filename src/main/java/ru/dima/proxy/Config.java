package ru.dima.proxy;

import net.sf.cglib.proxy.Enhancer;

public class Config {
    final String str;
    public Config(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public static void main(String[] args) {
        Config str = new Config("string");

        str = BeanPostProcessor.proccess(str);

        System.out.println(str.getStr());
        System.out.println(str.hashCode());

    }


}