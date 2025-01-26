package com.stock.mapper;

import com.stock.dto.UserRegistryDTO;
import com.stock.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User UserRegistryDTOToUser(UserRegistryDTO userRegistryDTO);

}
