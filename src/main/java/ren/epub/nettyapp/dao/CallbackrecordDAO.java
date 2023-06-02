package ren.epub.nettyapp.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import ren.epub.nettyapp.domain.Callbackrecord;

import java.util.List;

/**
 * CallbackrecordDAO继承基类
 */
@Repository
@Mapper
public interface CallbackrecordDAO {

    @Insert("INSERT INTO `table_callbackrecord` (`created_at`, `req_header`, `req_body`) VALUES (#{record.createdAt}, #{record.reqHeader}, #{record.reqBody})")
    int insert(@Param("record") Callbackrecord record);

    @Select("SELECT count('id') FROM `table_callbackrecord`")
    long count();

    @Select("SELECT `created_at`, `req_header`, `req_body` FROM `table_callbackrecord` ORDER BY `created_at` DESC LIMIT #{page},#{size}")
    List<Callbackrecord> selectByExampleWithBLOBs(@Param("page") Integer page, @Param("size") Integer size);
}