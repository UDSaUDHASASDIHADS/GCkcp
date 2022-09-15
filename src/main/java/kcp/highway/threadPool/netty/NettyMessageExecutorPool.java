package kcp.highway.threadPool.netty;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import kcp.highway.threadPool.IMessageExecutor;
import kcp.highway.threadPool.IMessageExecutorPool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于netty eventloop的线程池
 * Created by JinMiao
 * 2020/11/24.
 */
public class NettyMessageExecutorPool implements IMessageExecutorPool {

    private final EventLoopGroup eventExecutors;

    protected static final AtomicInteger index = new AtomicInteger();

    public NettyMessageExecutorPool(int workSize) {
        eventExecutors = new NioEventLoopGroup(workSize, r -> {
            return new Thread(r, "Runtime Netty Executor Pool -" + index.incrementAndGet());
        });
    }

    @Override
    public IMessageExecutor getIMessageExecutor() {
        return new NettyMessageExecutor(eventExecutors.next());
    }

    @Override
    public void stop() {
        if (!eventExecutors.isShuttingDown()) {
            eventExecutors.shutdownGracefully();
        }
    }
}
