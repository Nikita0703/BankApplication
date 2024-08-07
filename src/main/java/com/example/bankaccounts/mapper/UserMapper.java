package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BankAccountMapper.class, PersonalInfoMapper.class})
public interface UserMapper {
    @Named("fullUser")
    @Mapping(target = "bankAccountDTO", source = "bankAccount",qualifiedByName = "basicBankAccount")
    @Mapping(target = "personalInfoDTO", source = "personalInfo")
    UserDTO toUserDTOFull(User user);

    @Named("basicUser")
    @Mapping(target = "bankAccountDTO", ignore = true)
    @Mapping(target = "personalInfoDTO", source = "personalInfo")
    UserDTO toBasicUserDTO(User user);

    @Mapping(target = "bankAccountDTO", ignore = true)
    @Mapping(target = "personalInfoDTO", source = "personalInfo")
    List<UserDTO> toUserDTOList(List<User> users);

}
