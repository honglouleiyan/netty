package com.heaven.server.service;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Sharable
public class ConnectionCountHandler extends ChannelInboundHandlerAdapter {
    //jdk1.5 并发包中的用于计数的类
    private AtomicInteger nConnection = new AtomicInteger();

    public ConnectionCountHandler() {
        /**
         *  每两秒统计一下连接数
         */

//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
//            System.out.println("connections: " + nConnection.get());
//        }, 0, 2, TimeUnit.SECONDS);

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1,1,10,TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());
            threadPool.execute(() -> {
                try {
                    System.out.println("connections: " + nConnection.get());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    threadPool.shutdown();// 关闭线程池
                }
            });


    }
    /**
     *  每次过来一个新连接就对连接数加一
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        nConnection.incrementAndGet();
    }
    /**
     *  端口的时候减一
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        nConnection.decrementAndGet();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
        }
    }
}