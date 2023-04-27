package com.codezone.botrunningsystem.service.impl.utils;

import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class Consumer extends Thread {
    private Bot bot;
    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeOut, Bot bot) {
        this.bot = bot;
        this.start();
        try {
            this.join(timeOut); // 等待timeOut秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt(); // 中断
        }
    }

    private String addUid(String code, String uid) {
        int k = code.indexOf(" implements java.util.function.Supplier<String>");
        return code.substring(0, k) + uid + code.substring(k);
    }

    private String getMoveCpp() throws IOException, InterruptedException {
        // 将bot写入cpp文件
        File file = new File("bot.cpp");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getBotCode());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 编译并运行
        String command = "g++ bot.cpp -o bot.exe && cat input.txt | ./bot.exe";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // 获得cpp的输出
        String move = reader.readLine();
        // 删除生成的这三个文件
        command = "rm bot.cpp bot.exe input.txt";
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }

        process.waitFor();
        return move;
    }

    private String getMovePython() throws IOException, InterruptedException {
        // 将bot写入py文件
        File file = new File("bot.py");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getBotCode());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 编译并运行
        String command = "cat input.txt | python3 bot.py";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // 获得python的输出
        String move = reader.readLine();
        // 删除生成的文件
        command = "rm bot.py input.txt";
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }

        process.waitFor();
        return move;
    }

    private String getMoveGolang() throws IOException, InterruptedException {
        // 将bot写入golang文件
        File file = new File("bot.go");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getBotCode());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 编译并运行
        String command = "cat input.txt | go run bot.go";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // 获得go的输出
        String move = reader.readLine();
        // 删除生成的文件
        command = "rm bot.go input.txt";
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }

        process.waitFor();
        return move;
    }

    private String getMoveJava() throws IOException, InterruptedException {
        // 将bot写入java文件
        File file = new File("Main.java");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getBotCode());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 编译并运行
        String command = "javac -encoding utf-8 Main.java && cat input.txt | java Main";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        // 获得java的输出
        String move = reader.readLine();
        // 删除生成的文件
        command = "rm Main.* input.txt";
        try {
            process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", command });
        } catch (IOException e) {
            e.printStackTrace();
        }

        process.waitFor();
        return move;
    }

    @Override
    public void run() {
        // 将输入写入一个文件里
        File file = new File("input.txt");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getInput());
            fout.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String move = "";
        if (Objects.equals(bot.getLang(), "java")) {
            try {
                move = getMoveJava();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else if (bot.getLang().equals("cpp")) {
            try {
                move = getMoveCpp();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else if (bot.getLang().equals("python")) {
            try {
                move = getMovePython();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else if (bot.getLang().equals("golang")) {
            try {
                move = getMoveGolang();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", bot.getUserId().toString());
        data.add("move", move);

        restTemplate.postForObject("http://127.0.0.1:3081/game/receive/bot/move/", data, String.class);
    }
}
