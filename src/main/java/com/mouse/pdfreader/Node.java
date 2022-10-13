package com.mouse.pdfreader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/6 15:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    private int age;

    private String name;

    private SubNode node;

    public void setName(String name) {

        this.name = name + "suffix";
    }
}
