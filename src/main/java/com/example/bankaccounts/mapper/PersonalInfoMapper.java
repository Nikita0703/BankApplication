package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.PersonalInfoDTO;
import com.example.bankaccounts.entity.PersonalInfo;
import org.springframework.stereotype.Component;

@Component
public class PersonalInfoMapper {
    public PersonalInfo toPersonalInfo(PersonalInfoDTO personalInfoDTO){
        PersonalInfo personalInfo = PersonalInfo.builder()
                .passportType(personalInfoDTO.getPassportType())
                .passportID(personalInfoDTO.getPassportID())
                .firstName(personalInfoDTO.getFirstName())
                .lastName(personalInfoDTO.getLastName())
                .birthday(personalInfoDTO.getBirthday())
                .build();
        return personalInfo;
    }

    public PersonalInfoDTO toPersonalInfoDTO(PersonalInfo personalInfo){
        PersonalInfoDTO personalInfoDTO = PersonalInfoDTO.builder()
                .passportType(personalInfo.getPassportType())
                .passportID(personalInfo.getPassportID())
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .birthday(personalInfo.getBirthday())
                .build();
        return personalInfoDTO;
    }

}
