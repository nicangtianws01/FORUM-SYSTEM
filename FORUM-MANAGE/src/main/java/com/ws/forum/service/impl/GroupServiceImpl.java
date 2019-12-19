package com.ws.forum.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.forum.dao.GroupDao;
import com.ws.forum.pojo.Group;
import com.ws.forum.service.GroupService;
import com.ws.forum.vo.CheckBox;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public List<CheckBox> getGroupItems() {
		List<CheckBox> ckList = new ArrayList<>();
		List<Group> groupList = groupDao.selectList(null);
		for (Group group : groupList) {
			ckList.add(new CheckBox(group.getId(),group.getName()));
		}
		return ckList;
	}

}
