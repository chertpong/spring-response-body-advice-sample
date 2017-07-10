package com.kritacademy.spring.sample;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author krit on 7/10/2017.
 */
public interface RoomService {
    List<Room> getAll();

    List<Room> getAllByPriceGreaterThan(BigDecimal priceGt);
}
