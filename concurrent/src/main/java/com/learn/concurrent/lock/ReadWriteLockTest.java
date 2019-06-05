package com.learn.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReadWriteLockTest
 * @Description TODO
 * @Author xianjun@ixiaopu.com
 * @Date 30/10/2018 23:38
 */
public class ReadWriteLockTest {
    /*
     * ReentrantLock属于排他锁，这些锁在同一时刻只允许一个线程进行访问，
     * 而读写锁在同一时刻可以允许多个线程访问，但是在写线程访问时，所有的读和其他写线程都被阻塞
     * write-write
     * read-read 只有这种场景 是共享的 其他场景都是排它锁
     * write-read
     * read-write
     */


    static Map<String, Object> cache = new HashMap<>();
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock r = readWriteLock.readLock();
    static Lock w = readWriteLock.writeLock();

    public static final Object get(String key) {
        r.lock();
        try {
            return cache.get(key);
        } finally {
            r.unlock();
        }
    }

    public static final Object put(String key, String value) {
        w.lock();
        try {
            return cache.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public static final void clear() {
        w.lock();
        try {
            cache.clear();
        } finally {
            w.unlock();
        }
    }

}
