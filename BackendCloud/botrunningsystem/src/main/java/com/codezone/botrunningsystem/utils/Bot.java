package com.codezone.botrunningsystem.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Bot implements java.util.function.Supplier<String> {

    public String nextMove(String input) {
        System.out.println(input);
        String[] array = input.split(" ");
        int[][] g = new int[15][15];
        int color = -1, turnID = Integer.parseInt(array[0]), idx = 1;
        if (turnID % 2 == 1) color = 1; // 黑
        else color = 2; // 白

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                g[i][j] = Integer.parseInt(array[idx++]);
            }
        }

        while (true) {
            Random random = new Random();
            int x = random.nextInt(15), y = random.nextInt(15);
            if (g[x][y] > 0) continue;
            return x + " " + y;
        }
    }

    @Override
    public String get() {
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            return nextMove(sc.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
