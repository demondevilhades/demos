package com.hades.zookeepertest.id;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchIdBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IdBuilder idBuilder;
    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

    private Thread thread;

    public BatchIdBuilder(String idName) {
        idBuilder = new IdBuilder(idName);
    }

    public void start() {
        idBuilder.start();

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (idBuilder.isRunning()) {
                        queue.put(idBuilder.generateId());
                    }
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
                stop();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        idBuilder.stop();
    }

    public String getId() {
        return queue.poll();
    }

    public String getIdNotNull() {
        while (true) {
            try {
                return queue.take();
            } catch (InterruptedException e) {
            }
        }
    }
}
