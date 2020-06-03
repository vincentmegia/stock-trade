package com.hackerrank.stocktrade.controller;

import com.hackerrank.stocktrade.TradesRepository;
import com.hackerrank.stocktrade.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/trades")
public class TradesController {
    @Autowired
    private TradesRepository tradesRepository;

    @PostMapping
    ResponseEntity<Trade> add(@RequestBody Trade trade) {
        boolean status =  tradesRepository.add(trade);
        if (status)
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    ResponseEntity<Trade> get(@PathVariable Long id) {
        Trade trade = tradesRepository.getById(id);
        if (trade == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(trade, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<Trade> get() {
        List<Trade> trades = tradesRepository.all();
        return trades;
    }

    @GetMapping("users/{userID}")
    ResponseEntity<?> getTradesByUserId(@PathVariable Long userID) {
        List<Trade> trades = tradesRepository.getTradesByUserId(userID);
        if (trades.size() == 0)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    @GetMapping("stocks/{stockSymbol}")
    ResponseEntity<?> getTradesByStockSymbol(@PathVariable String stockSymbol) {
        List<Trade> trades = tradesRepository.getTradesBySymbol(stockSymbol);
        if (trades.size() == 0)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    @GetMapping("users/{userID}/stocks/{stockSymbol}")
    ResponseEntity<?> getTradesByStockSymbol(@PathVariable Long userID,
                                             @PathVariable String stockSymbol) {
        List<Trade> trades = tradesRepository.getTradesByUserIdAndSymbol(userID, stockSymbol);
        if (trades.size() == 0)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(trades, HttpStatus.OK);
    }
}
