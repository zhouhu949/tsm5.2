package com.qftx.tsm.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User£º bxl
 * Date£º 2016/2/25
 * Time£º 11:37
 */
public class TestLock {
    public static int ct = 0;
    private static Lock lock = new ReentrantLock();// Ëø
    // Ëø
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            final int t = i;
            new Thread() {
                @Override
                public void run() {
                    String orgId = "8decbe1278b646b5a462bbd4bc80bd58";
                    TestLockBean bean = new TestLockBean();
                    bean.setOrgId(orgId);
                  //  bean.setAccount("hn00" + (t % 2 == 0 ? "1" : "2"));
                   bean.setAccount("hn003");
                    bean.setKey(bean.getOrgId() + bean.getAccount());

                    lock(bean);
                }
            }.start();
        }
    }



    public static void lock1(TestLockBean bean) {
        if (bean == null) return;
        //  System.out.println("***"+(bean.hashCode() + "").hashCode());
        final  String str = bean.hashCode() + "";
        //  str = bean.getOrgId() + "_" + bean.getAccount();
        //  System.out.println(str.equals("8decbe1278b646b5a462bbd4bc80bd58_hn001"));
        //  str = bean.getAccount();
        System.out.println(str);
        if(str.equals("8decbe1278b646b5a462bbd4bc80bd58_hn001"))
        lock.lock();
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ct++;
            System.out.println(ct);
        }
        lock.unlock();
        //   System.out.println(bean.hashCode());
    }
    public static void lock(TestLockBean bean) {
        if (bean == null) return;
        //  System.out.println("***"+(bean.hashCode() + "").hashCode());
           final  String str = bean.hashCode() + "";
      //  str = bean.getOrgId() + "_" + bean.getAccount();
        //  System.out.println(str.equals("8decbe1278b646b5a462bbd4bc80bd58_hn001"));
      //  str = bean.getAccount();
        System.out.println(str);
        synchronized (bean.getAccount()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ct++;
            System.out.println(ct);
        }
        //   System.out.println(bean.hashCode());
    }
}
