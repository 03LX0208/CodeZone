package com.zone.backend.consumer.gomoku;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zone.backend.consumer.WebSocketServer;
import com.zone.backend.pojo.Record;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    final static int rows = 15;
    final static int cols = 15;
    final private int[][] g;    // 黑棋是1 白棋是2
    final private Player playerA, playerB; // 这场游戏的两个玩家
    private List<Piece> steps = null;   // 记录这一局的每一步
    private int turnId = 1; // 奇数是黑 偶数是白
    private Piece nowStep = null;
    final private ReentrantLock lock = new ReentrantLock();
    private String status = "playing";  //  这局游戏的状态 "finished"表示游戏已经结束
    private String loser = ""; // "all"表示平局 "A"表示A输了

    public Game(Integer idA, String aUsername, Integer idB, String bUsername) {
        // 初始化五子棋棋盘
        this.g = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.g[i][j] = 0;
            }
        }
        this.playerA = new Player(idA, aUsername);
        this.playerB = new Player(idB, bUsername);
        this.steps = new ArrayList<>();
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
        for (int i = 0; i < 300; i++) {
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if (nowStep != null) {
                        this.steps.add(nowStep);
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

    private void setToDatabase() {
        StringBuilder res = new StringBuilder();
        for (Piece p : this.steps) {
            res.append(p.getX().toString()).append(" ").append(p.getY().toString()).append(" ");
        }
        Record record = new Record(null,
                "gomoku",
                this.playerA.getUserId(),
                this.playerA.getUsername(),
                this.playerB.getUserId(),
                this.playerB.getUsername(),
                res.toString(),
                this.loser,
                new Date()
                );
        WebSocketServer.recordMapper.insert(record);
    }

    private void sendResult() { // 向两名玩家广播游戏结果
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", this.loser);
        sendAMessage(resp.toJSONString());
        sendBMessage(resp.toJSONString());
        setToDatabase();
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
        int cnt = 0, color = 1, x = this.nowStep.getX(), y = this.nowStep.getY();
        if (this.turnId % 2 == 1) color = 2;

        // 横
        for (int i = Math.max(x - 4, 0); i <= Math.min(14, x + 4); i++) {
            if (g[i][y] == color) {
                ++cnt;
                if (cnt >= 5) {
                    this.status = "finished";
                    if (color == 1) {
                        this.loser = "B";
                    } else this.loser = "A";
                    return;
                }
            } else cnt = 0;
        }

        // 竖
        cnt = 0;
        for (int i = Math.max(y - 4, 0); i <= Math.min(14, y + 4); i++) {
            if (g[x][i] == color) {
                ++cnt;
                if (cnt >= 5) {
                    this.status = "finished";
                    if (color == 1) {
                        this.loser = "B";
                    } else this.loser = "A";
                    return;
                }
            } else cnt = 0;
        }

        // 撇
        cnt = 0;
        for (int i = -4; i <= 4; i++) {
            int dx = x + i, dy = y + i;
            if (dx < 0 || dx > 14 || dy < 0 || dy > 14) continue;
            if (g[dx][dy] == color) {
                ++cnt;
                if (cnt >= 5) {
                    this.status = "finished";
                    if (color == 1) {
                        this.loser = "B";
                    } else this.loser = "A";
                    return;
                }
            } else cnt = 0;
        }

        // 捺
        cnt = 0;
        for (int i = -4; i <= 4; i++) {
            int dx = x + i, dy = y - i;
            if (dx < 0 || dx > 14 || dy < 0 || dy > 14) continue;
            if (g[dx][dy] == color) {
                ++cnt;
                if (cnt >= 5) {
                    this.status = "finished";
                    if (color == 1) {
                        this.loser = "B";
                    } else this.loser = "A";
                    return;
                }
            } else cnt = 0;
        }

        if (this.turnId == 226) {
            this.status = "finished";
            this.loser = "all";
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 225; i++) {
            if (waitNowStep()) {
                isWin();
                sendDraw();
                if ("finished".equals(this.status)) {
                    sendResult();
                    break;
                }
            } else {
                lock.lock();
                try {
                    this.status = "finished";
                    if (turnId % 2 == 1) {
                        this.loser = "A";
                    } else this.loser = "B";
                    sendResult();
                } finally {
                    lock.unlock();
                }
                break;
            }
        }
    }
}
