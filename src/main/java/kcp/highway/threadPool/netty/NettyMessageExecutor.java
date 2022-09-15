package kcp.highway.threadPool.netty;

import io.netty.channel.EventLoop;
import kcp.highway.threadPool.IMessageExecutor;
import kcp.highway.threadPool.ITask;

import java.util.concurrent.RejectedExecutionException;

/**
 * Created by JinMiao
 * 2020/11/24.
 */
public class NettyMessageExecutor implements IMessageExecutor {

    private final EventLoop eventLoop;

    public NettyMessageExecutor(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void execute(ITask iTask) {
        try {
            this.eventLoop.execute(iTask::execute);
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

}
