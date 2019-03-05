package com.book.mapper;

import com.book.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    Admin getByNameAndPassword(Admin admin);
}
