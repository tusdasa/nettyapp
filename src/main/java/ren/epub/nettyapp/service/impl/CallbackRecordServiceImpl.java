package ren.epub.nettyapp.service.impl;

import org.springframework.stereotype.Service;
import ren.epub.nettyapp.dao.CallbackrecordDAO;
import ren.epub.nettyapp.domain.Callbackrecord;
import ren.epub.nettyapp.service.CallbackRecordService;
import ren.epub.nettyapp.utils.PageUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CallbackRecordServiceImpl implements CallbackRecordService {

    private final CallbackrecordDAO callbackrecordDAO;

    public CallbackRecordServiceImpl(CallbackrecordDAO callbackrecordDAO) {
        this.callbackrecordDAO = callbackrecordDAO;
    }

    @Override
    public List<Callbackrecord> findCallbackRecords(Integer page, Integer size) {
        PageUtils pageUtils = new PageUtils(page,size);
        List<Callbackrecord> callbackrecordList = callbackrecordDAO.selectByExampleWithBLOBs(pageUtils.getPage(), pageUtils.getSize());
        return Objects.requireNonNullElse(callbackrecordList, Collections.emptyList());
    }

    @Override
    public void addCallbackRecord(String reqHeader, String reqBody) {
        Callbackrecord record = new Callbackrecord();
        record.setCreatedAt(new Date());
        record.setReqBody(reqBody!=null?reqBody:"");
        record.setReqHeader(reqHeader!=null?reqHeader:"");
        callbackrecordDAO.insert(record);
    }
}
