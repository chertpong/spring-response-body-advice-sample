package com.kritacademy.spring.sample;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author krit on 7/10/2017.
 */
@Repository
public class RoomDaoImpl implements RoomDao {
    @Override
    public List<Room> getAll() {
        Room room1 = new Room();
        room1.setId(1);
        room1.setName("Luxury room");
        room1.setPrice(BigDecimal.valueOf(3500));

        Room room2 = new Room();
        room2.setId(2);
        room2.setName("Premium room");
        room2.setPrice(BigDecimal.valueOf(5000));

        return Arrays.asList(room1, room2);
    }

    @Override
    public List<Room> getAllByPriceGreaterThan(BigDecimal priceGt) {
        return this.getAll().stream().filter(e -> e.getPrice().compareTo(priceGt) > 0).collect(Collectors.toList());
    }
}
