package ren.epub.nettyapp.service.impl;

import org.springframework.stereotype.Service;
import ren.epub.nettyapp.dao.CallbackrecordDAO;
import ren.epub.nettyapp.domain.Callbackrecord;
import ren.epub.nettyapp.domain.CallbackrecordExample;
import ren.epub.nettyapp.domain.CallbackrecordWithBLOBs;
import ren.epub.nettyapp.service.CallbackRecordService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CallbackRecordServiceImpl implements CallbackRecordService {

    private final CallbackrecordDAO callbackrecordDAO;

    public CallbackRecordServiceImpl(CallbackrecordDAO callbackrecordDAO) {
        this.callbackrecordDAO = callbackrecordDAO;
    }

    @Override
    public List<Callbackrecord> findCallbackRecords(Integer page, Integer size) {
        CallbackrecordExample example = new CallbackrecordExample();
        example.setLimit(size);
        example.setOffset(page.longValue());
        example.setOrderByClause("created_at DESC");
        List<Callbackrecord> callbackrecordList = callbackrecordDAO.selectByExampleWithBLOBs(example);
        if (callbackrecordList!=null){
            return callbackrecordList;
        }else {
            return Collections.emptyList();
        }
    }

    @Override
    public void addCallbackRecord(String reqHeader, String reqBody) {
        CallbackrecordWithBLOBs record = new CallbackrecordWithBLOBs();
        record.setCreatedAt(new Date());
        record.setReqBody(reqBody!=null ? reqBody:"");
        record.setReqHeader(reqHeader!=null ? reqHeader:"");
        callbackrecordDAO.insert(record);
    }
}
