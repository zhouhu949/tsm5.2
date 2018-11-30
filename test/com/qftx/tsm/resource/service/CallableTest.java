package com.qftx.tsm.resource.service;

/**
 * ≤‚ ‘
 * Created by Administrator on 2016/5/31.
 */

import com.qftx.base.util.JsonUtil;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
    public static void main(String[] args) {
        ExecutorService exs = Executors.newCachedThreadPool();
        ArrayList<Future<String>> al = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            al.add(exs.submit(new CallableThread("String " + i)));
        }
        for (int i = 0; i < al.size(); i++) {
            try {
                System.out.println("i=" + i);
                System.out.println("start i=" + i + "isDone=" + al.get(i).isDone());
                System.out.println("i=" + i + al.get(i).get());
                System.out.println("end i=" + i + "isDone=" + al.get(i).isDone());
//                System.out.println("i=" + i + "isDone=" + al.get(i).isDone());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("6666666666666666666666666");
    }

}
