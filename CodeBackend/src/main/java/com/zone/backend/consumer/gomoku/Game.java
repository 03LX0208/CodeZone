package com.zone.backend.consumer.gomoku;

public class Game {
    final static int rows = 15;
    final static int cols = 15;
    final private int[][] g;

    public Game() {
        // 初始化五子棋棋盘
        this.g = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.g[i][j] = 0;
            }
        }
    }

    public int[][] getGameMap() {
        return this.g;
    }
}
