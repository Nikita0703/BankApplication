package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.PersonalInfoDTO;
import com.example.bankaccounts.entity.PersonalInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonalInfoMapper {
    PersonalInfo toPersonalInfo(PersonalInfoDTO personalInfoDTO);
    PersonalInfoDTO toPersonalInfoDTO(PersonalInfo personalInfo);
}
