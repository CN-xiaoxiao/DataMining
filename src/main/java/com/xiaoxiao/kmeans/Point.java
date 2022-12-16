package com.xiaoxiao.kmeans;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Point {
    private int id;
    private String name;
    private double x;
    private double y;
}
