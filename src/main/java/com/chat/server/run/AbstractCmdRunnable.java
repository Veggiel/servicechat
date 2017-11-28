package com.chat.server.run;

import com.chat.server.entity.MsgEntity;
import com.chat.server.queue.BaseQueue;

/**
 * <dd>Description:类描述</dd>
 * <dd>Copyright: 版权归杭州京歌科技有限公司所有</dd>
 * <dd>Company: 杭州京歌科技有限公司</dd>
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.server.run</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/21 0021 11:06</dd>
 */
public abstract class AbstractCmdRunnable implements Runnable {
    private BaseQueue<MsgEntity> baseQueue ;

    public AbstractCmdRunnable(BaseQueue<MsgEntity> baseQueue) {
        this.baseQueue = baseQueue;
    }
    @Override
    public void run() {
        MsgEntity msgEntity = null;
        while (true){
            while (baseQueue.getSize()>0){
                msgEntity = baseQueue.take();
                if (msgEntity != null){
                    handMsg(msgEntity);
                }
            }

        }
    }

    public abstract void   handMsg(MsgEntity msgEntity);
}
