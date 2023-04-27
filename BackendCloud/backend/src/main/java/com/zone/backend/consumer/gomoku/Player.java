package com.zone.backend.consumer.gomoku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer userId;
    private Integer botId; // 0表示亲自出马
    private String lang; // 语言类型
    private String botCode;
    private String username;
}
