package com.ws.forum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ws.forum.dao.LogDao;
import com.ws.forum.pojo.Log;
import com.ws.forum.service.LogService;

@Transactional
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;
	
	@Async("asyncPoolExecutor")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public int saveObject(Log entity) {
		return logDao.insert(entity);
	}

}
