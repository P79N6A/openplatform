package com.kd.marketplace.mapper;

import com.kd.marketplace.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User queryUser();
}
