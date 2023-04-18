package com.zone.backend.consumer.gomoku;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zone.backend.consumer.WebSocketServer;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    final static int rows = 15;
    final static int cols = 15;
    final private int[][] g;
    final private Player playerA, playerB; // 这场游戏的两个玩家

    private int turnId = 1; // 奇数是黑 偶数是白
    private Piece nowStep = null;
    final private ReentrantLock lock = new ReentrantLock();
    private String status = "playing";  //  这局游戏的状态 "finished"表示游戏已经结束
    private String loser = ""; // "all"表示平局 "A"表示A输了

    public Game(Integer idA, Integer idB) {
        // 初始化五子棋棋盘
        this.g = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.g[i][j] = 0;
            }
        }
        this.playerA = new Player(idA, new ArrayList<>());
        this.playerB = new Player(idB, new ArrayList<>());
    }

    public Player getPlayerA() {
        return this.playerA;
    }

    public void setNowStep(Piece nowStep) {
        lock.lock();
        try {
            if (this.g[nowStep.getX()][nowStep.getY()] == 0) {
                this.nowStep = nowStep;
                if (this.turnId % 2 == 1) {
                    g[nowStep.getX()][nowStep.getY()] = 1;
                } else {
                    g[nowStep.getX()][nowStep.getY()] = 2;
                }
                System.out.println(this.nowStep);
                this.turnId++;
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean waitNowStep() { // 等待这名玩家
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    if (nowStep != null) {
                        playerA.getSteps().add(nowStep);
                        playerB.getSteps().add(nowStep);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Player getPlayerB() {
        return this.playerB;
    }

    public int[][] getGameMap() {
        return this.g;
    }

    private void sendAMessage(String message) {
        WebSocketServer.users.get(this.playerA.getUserId()).sendMessage(message);
    }

    private void sendBMessage(String message) {
        WebSocketServer.users.get(this.playerB.getUserId()).sendMessage(message);
    }

    private void sendResult() { // 向两名玩家广播游戏结果
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", this.loser);
        sendAMessage(resp.toJSONString());
        sendBMessage(resp.toJSONString());
    }

    private void sendDraw() {
        lock.lock();
        try {
            JSONObject respA = new JSONObject();
            JSONObject respB = new JSONObject();
            respA.put("event", "draw");
            respA.put("nx", this.nowStep.getX());
            respA.put("ny", this.nowStep.getY());
            respB.put("event", "draw");
            respB.put("nx", this.nowStep.getX());
            respB.put("ny", this.nowStep.getY());
            if (this.turnId % 2 == 1) {
                respA.put("my_turn", true);
                respB.put("my_turn", false);
                respA.put("color", 2);
                respB.put("color", 2);
            } else {
                respA.put("my_turn", false);
                respB.put("my_turn", true);
                respA.put("color", 1);
                respB.put("color", 1);
            }
            this.nowStep = null;
            sendAMessage(respA.toJSONString());
            sendBMessage(respB.toJSONString());
        } finally {
            lock.unlock();
        }
    }



    private void isWin() {

    }

    @Override
    public void run() {
        for (int i = 0; i < 225; i++) {
            if (waitNowStep()) {
                isWin();
                if ("finished".equals(this.status)) {
                    sendResult();
                    break;
                } else {
                    sendDraw();
                }
            } else {
                lock.lock();
                try {
                    this.status = "finished";
                    if (turnId % 2 == 1) {
                        this.loser = "A";
                    } else this.loser = "B";
                } finally {
                    lock.unlock();
                }
                break;
            }
        }
    }
}
