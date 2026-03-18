package com.devsantana.lyday.modules.inventory.leager.service;

import com.devsantana.lyday.modules.inventory.leager.dto.LedgerResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface LedgerService {

    List<LedgerResponseDto> getLedger(
            Long productId,
            Long locationId,
            LocalDateTime dateStart,
            LocalDateTime endStart
    );
}
