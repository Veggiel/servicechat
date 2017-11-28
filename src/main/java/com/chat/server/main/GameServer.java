package com.chat.server.main;


import com.chat.server.netty.NettyServer;
import com.chat.server.queue.CommonQueue;
import com.chat.server.queue.LoginQueue;
import com.chat.server.run.CmdRunnableImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
	private final static int port = 9090;

	public static void main(String[] args) {
		// 初始化本地处理逻辑线程
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new CmdRunnableImpl(CommonQueue.getInstance()));// 处理通用命令码,具体用意参考第2条(http://vincepeng.iteye.com/blog/2171581)
		exec.execute(new CmdRunnableImpl(LoginQueue.getInstance()));// 处理登录命令码
		// 初始化netty
		exec.execute( new NettyServer(port));
System.out.println("已启动！");
		// 初始化数据库,redis等其它操作

	}
}
