package com.qftx.tsm.resource.service;

/**
 * 测试多线程返回值
 * Created by Administrator on 2016/5/31.
 */

import java.util.concurrent.Callable;

public class CallableThread implements Callable<String> {
    private String str;
    private int count=10;
    public CallableThread(String str){
        this.str=str;
    }
    //需要实现Callable的Call方法
    public String call() throws Exception {
        for(int i=0;i<this.count;i++){
            System.out.println(this.str+" "+i);
               if(i==99){
                   Thread.sleep(6000);
               }
        }
        return this.str;
    }
}