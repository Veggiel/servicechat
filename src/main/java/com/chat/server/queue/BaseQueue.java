package com.chat.server.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.queue</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 9:37</dd>
 */
public class BaseQueue<T> {
    private final BlockingQueue<T> queue = new LinkedBlockingDeque<T>();

    /**
     * 不阻塞队列，如果queue为空，直接返回null
     *  取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null
     * @return T
     */
    public T take(){
        return queue.poll();
    }

    /**
     * 阻塞队列，不丢掉消息
     * put()如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
     * @param t
     */
    public void put(T t){
        try {
            queue.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSize(){
        return queue.size();
    }
}
