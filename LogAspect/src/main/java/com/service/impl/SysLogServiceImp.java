package com.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.define.annotation.Logs;
import com.dao.SysLogMapper;
import com.entity.SysLog;
import com.service.SysLogService;

@Service("SysLogService")
public class SysLogServiceImp implements SysLogService {

    @Autowired
    private SysLogMapper SysLogMapper;
    
    @Override
    public int deleteSysLog(String id) {
        
        return SysLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    
    public int insert(SysLog record) {
        
        return SysLogMapper.insertSelective(record);
    }

    @Override
    @Cacheable(value="myCache",key="#id")
    public SysLog selectSysLog(String id) {
        
        return SysLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSysLog(SysLog record) {
        
        return SysLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    //@CachePut(value="myCache")
    //@CacheEvict(value="myCache",allEntries=true,beforeInvocation=true)
    //@CacheEvict(value="myCache",key="0",beforeInvocation=true)
    public int insertTest(SysLog record) {
        
        return SysLogMapper.insert(record);
    }
    
    @Override
    @Cacheable(value="myCache",key="0")
    public int count() {
           int num = (int) System.currentTimeMillis();
           return num;
    }
}