package com.devsantana.lyday.modules.inventory.leager.controller;

import com.devsantana.lyday.modules.inventory.leager.dto.LedgerResponseDto;
import com.devsantana.lyday.modules.inventory.leager.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/inventory/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService service;

    @GetMapping
    public List<LedgerResponseDto> getLeger(
            @RequestParam Long productId,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) LocalDateTime dateStart,
            @RequestParam(required = false) LocalDateTime dateEnd
    ){
        return service.getLedger(productId, locationId, dateStart, dateEnd);
    }
}
