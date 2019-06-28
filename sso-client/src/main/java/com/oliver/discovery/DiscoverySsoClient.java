package com.oliver.discovery;

import com.oliver.discovery.transport.SsoHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * com.oliver.bean DiscoverySsoClient
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/6/12 11:27
 */
public class DiscoverySsoClient {
    private static Logger logger = LoggerFactory.getLogger(DiscoverySsoClient.class);

    private static final String PREFIX = "SSOClient_";
    private final ScheduledExecutorService scheduler;
    private final ThreadPoolExecutor heartbeatExecutor;
    private SsoHttpClient ssoHttpClient;

    DiscoverySsoClient() {
        this.scheduler = Executors.newScheduledThreadPool(2, run -> {
            Thread thread = new Thread(run);
            thread.setName("DiscoverySsoClient-%d");
            thread.setDaemon(true);
            return thread;
        });
        this.heartbeatExecutor = new ThreadPoolExecutor(1, 10, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), run -> {
            Thread thread = new Thread(run);
            thread.setName("DiscoverySsoClient-HeartbeatExecutor-%d");
            thread.setDaemon(true);
            return thread;
        });
        initScheduledTasks();
    }

    private void initScheduledTasks() {
    }

    class CacheRefreshThread implements Runnable {
        @Override
        public void run() {
            refreshRegistry();
        }

        private void refreshRegistry() {

        }
    }

    static class TimedSupervisorTask extends TimerTask {
        private final Runnable task;
        private final ThreadPoolExecutor executor;
        private final long timeoutMillis;

        public TimedSupervisorTask(ThreadPoolExecutor executor, int timeout, TimeUnit timeUnit, Runnable task) {
            this.task = task;
            this.executor = executor;
            this.timeoutMillis = timeUnit.toMillis(timeout);
        }

        @Override
        public void run() {
            Future<?> submit;
            submit = executor.submit(task);
            try {
                submit.get(timeoutMillis, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException e) {
                logger.warn("task supervisor Exception", e);
            } catch (TimeoutException e) {
                logger.warn("task supervisor timed out", e);
            }
        }
    }


}
