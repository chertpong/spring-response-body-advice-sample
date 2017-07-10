package com.kritacademy.spring.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author krit on 7/10/2017.
 */
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;

    @Autowired
    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }

    @Override
    public List<Room> getAllByPriceGreaterThan(BigDecimal priceGt) {
        return roomDao.getAllByPriceGreaterThan(priceGt);
    }
}
