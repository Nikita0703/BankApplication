package com.example.bankaccounts.mapper;
import com.example.bankaccounts.dto.HistoryItemDTO;
import com.example.bankaccounts.entity.HistoryItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryItemMapper {
    HistoryItemDTO toHistoryItemDTO(HistoryItem historyItem);

    HistoryItem toHistoryItem(HistoryItemDTO historyItemDTO);

    List<HistoryItemDTO> toHistoryItemDTOList(List<HistoryItem> historyItemList);

    List<HistoryItem> toHistoryItemList(List<HistoryItemDTO> historyItemDTOList);

}
