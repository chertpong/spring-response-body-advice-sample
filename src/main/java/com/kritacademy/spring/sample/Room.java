package com.kritacademy.spring.sample;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author krit on 7/10/2017.
 */
@Data
@Accessors(chain = true)
public class Room {
    private Integer id;
    private String name;
    private BigDecimal price;
}
