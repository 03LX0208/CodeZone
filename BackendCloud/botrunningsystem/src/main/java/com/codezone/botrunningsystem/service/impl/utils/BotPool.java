package com.codezone.botrunningsystem.service.impl.utils;

import com.codezone.botrunningsystem.service.impl.utils.Bot;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread {
    private final ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private final Queue<Bot> bots = new LinkedList<>();

    public void addBot(Integer userId, String lang, String botCode, String input) {
        lock.lock();
        try {
            bots.add(new Bot(userId, lang, botCode, input));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    // 消费者
    private void consume(Bot bot) {
        Consumer consumer = new Consumer();
        consumer.startTimeout(5000, bot);
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (bots.isEmpty()) { // 没有任务就阻塞
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else {
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot);
            }
        }
    }
}
