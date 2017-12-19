package com.kritacademy.spring.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author krit on 7/10/2017.
 */
@RestController
@RequestMapping("/v1/api/rooms")
@AutoFee("5.00")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @RestFilter
    @AutoFee("3.00")
    @GetMapping("")
    public List<Room> getAll() {
        return roomService.getAll();
    }

    @RestFilter
    @GetMapping(value = "", params = "priceGt")
    public List<Room> getAllByPriceGreaterThan(@RequestParam("priceGt") BigDecimal priceGt) {
        return roomService.getAllByPriceGreaterThan(priceGt);
    }
}
