package com.service;


import org.springframework.stereotype.Service;

import com.entity.SysLog;
@Service
public interface SysLogService {

    int deleteSysLog(String id);

    int insert(SysLog record);
    
    int insertTest(SysLog record);

    SysLog selectSysLog(String id);
    
    int updateSysLog(SysLog record);

	int count();
}