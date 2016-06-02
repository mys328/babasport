package com.lionxxw.babasport.core.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductTypeDto implements Serializable{

    private Integer id;

    private String name;

    private Integer parentId;

    private String note;

    private Boolean isDisplay;
}