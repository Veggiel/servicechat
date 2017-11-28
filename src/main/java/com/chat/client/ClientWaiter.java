package com.chat.client;

import java.util.Scanner;

/**
 * <dd>Description:客服类</dd>
 * <dd>Copyright: 版权归杭州京歌科技有限公司所有</dd>
 * <dd>Company: 杭州京歌科技有限公司</dd>
 * <dd>Project_name servicechat</dd>
 * <dd>Package_name com.chat.client</dd>
 * <dd>Author: libin </dd>
 * <dd>Email veggiel@foxmail.com</dd>
 * <dd>Create_time 2017/11/18 0018 16:55</dd>
 */
public class ClientWaiter extends Client {
   public static void main(String [] args){
       Client c = new ClientWaiter();
       c.startEngine();
       c.sendSignatureVerification("客服");
       Scanner in = new Scanner(System.in);
       while (true) {
           System.out.println("请输入要发给那个用户（name:要说的话）");
           c.sendCommonMsg(in.next());
       }
   }
}
