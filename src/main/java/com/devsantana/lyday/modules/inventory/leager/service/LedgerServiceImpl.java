package com.devsantana.lyday.modules.inventory.leager.service;

import com.devsantana.lyday.modules.inventory.leager.dto.LedgerResponseDto;
import com.devsantana.lyday.modules.inventory.movement.model.InventoryMovement;
import com.devsantana.lyday.modules.inventory.movement.repository.InventoryMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService{

    private final InventoryMovementRepository movementRepository;

    @Override
    public List<LedgerResponseDto> getLedger(
            Long productId,
            Long locationId,
            LocalDateTime dateStart,
            LocalDateTime dateEnd
    ){
        List<InventoryMovement> movements = movementRepository.findAll()
                .stream()
                .filter(m -> m.getProduct().getId().equals(productId))
                .collect(Collectors.toList());

        if (movements.isEmpty()){
            throw new EntityNotFoundException("Nenhuma movimentação foi encontrada para o Item");
        }
        if (dateStart != null){
            movements = movements.stream()
                    .filter(m-> !m.getCreatedAt().isBefore(dateStart))
                    .collect(Collectors.toList());
        }
        if (dateEnd != null){
            movements = movements.stream()
                    .filter(m-> !m.getCreatedAt().isAfter(dateEnd))
                    .collect(Collectors.toList());
        }
        movements.sort(Comparator.comparing(InventoryMovement::getCreatedAt));

        int balance = 0;

        List<LedgerResponseDto> ledGer = new java.util.ArrayList<>();

        for (InventoryMovement m : movements){
            int entry = 0;
            int exit = 0;

            switch (m.getType()){
                case RECEIVING -> entry = m.getQuantity();

                case PICKING -> exit = m.getQuantity();

                case TRANSFER -> {
                    if (locationId !=null){
                        if (m.getDestinationLocation() != null &&
                                m.getDestinationLocation().getId().equals(locationId)){
                            entry = m.getQuantity();
                        }
                        if (m.getSourceLocation() != null &&
                        m.getSourceLocation().getId().equals(locationId)){
                            exit = m.getQuantity();
                        }
                    } else {
                        exit = m.getQuantity();
                    }
                }
                case ADJUSTMENT -> {
                    if (m.getQuantity() >= 0){
                        entry = m.getQuantity();
                    } else {
                        exit = Math.abs(m.getQuantity());
                    }
                }
            }
            balance = balance + entry - exit;

            ledGer.add(
                    LedgerResponseDto.builder()
                            .date(m.getCreatedAt())
                            .type(m.getType())
                            .entry(entry)
                            .exit(exit)
                            .balance(balance)
                            .reason(m.getReason())
                            .build()
            );
        }
        return ledGer;
    }
}
