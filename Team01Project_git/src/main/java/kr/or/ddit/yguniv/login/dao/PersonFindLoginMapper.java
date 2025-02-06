package kr.or.ddit.yguniv.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PersonFindLoginMapper {
    String selectFindForm(@Param("nm") String nm, @Param("brdt") String brdt);
}

