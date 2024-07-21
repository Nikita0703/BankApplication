package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.HistoryItemDTO;
import com.example.bankaccounts.entity.HistoryItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryItemMapper {

    public HistoryItemDTO toHisteryItemDTO(HistoryItem historyItem){
        HistoryItemDTO historyItemDTO = HistoryItemDTO.builder()
                .creationDate(historyItem.getCreationDate())
                .sum(historyItem.getSum())
                .description(historyItem.getDescription())
                .build();
        return historyItemDTO;
    }

    public HistoryItem toHisteryItem(HistoryItemDTO historyItem){
        HistoryItem historyItemm = HistoryItem.builder()
                .creationDate(historyItem.getCreationDate())
                .sum(historyItem.getSum())
                .description(historyItem.getDescription())
                .build();
        return historyItemm;
    }


    public List<HistoryItemDTO> toHisteryItemDTOList(List<HistoryItem> historyItemList){
        List<HistoryItemDTO> historyItemDTOList = new ArrayList<>();
        for (HistoryItem historyItem:historyItemList){
            historyItemDTOList.add(toHisteryItemDTO(historyItem));
        }
        return historyItemDTOList;
    }

    public List<HistoryItem> toHisteryItemList(List<HistoryItemDTO> historyItemList){
        List<HistoryItem> historyItemListt = new ArrayList<>();
        for (HistoryItemDTO historyItem:historyItemList){
            historyItemListt.add(toHisteryItem(historyItem));
        }
        return historyItemListt;
    }
}
