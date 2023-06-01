package ren.epub.nettyapp.service;

import ren.epub.nettyapp.domain.Callbackrecord;

import java.util.List;

public interface CallbackRecordService {

    List<Callbackrecord> findCallbackRecords(Integer page, Integer size);

    void addCallbackRecord(String reqHeader, String reqBody);

}
