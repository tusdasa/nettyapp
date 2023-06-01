package ren.epub.nettyapp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ren.epub.nettyapp.domain.Callbackrecord;
import ren.epub.nettyapp.domain.CallbackrecordExample;

/**
 * CallbackrecordDAO继承基类
 */
@Repository
@Mapper
public interface CallbackrecordDAO extends MyBatisBaseDao<Callbackrecord, Long, CallbackrecordExample> {
}