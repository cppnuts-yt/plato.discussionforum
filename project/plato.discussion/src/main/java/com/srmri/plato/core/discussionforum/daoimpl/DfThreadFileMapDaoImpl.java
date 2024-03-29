/**********************************************************************************
 * File-name - DfAttachedFileDaoImpl
 * Version - 1.0
 * Author - SRM RI
 ***********************************************************************************
 *
 * Copyright (c) 2015 SRM Research Institute, Bangalore. All rights reserved.
 * No part of this product may be reproduced in any form by any means without prior
 * written authorization of SRM Research Institute and its licensors, if any.
 *
 ***********************************************************************************
 *
 * Description: <Add description about the file>
 *
 ****************************************************************************/

package com.srmri.plato.core.discussionforum.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.srmri.plato.core.discussionforum.dao.DfThreadFileMapDao;
import com.srmri.plato.core.discussionforum.entity.DfThreadFileMap;
import com.srmri.plato.core.discussionforum.entity.DfThreadReplyFileMap;
import com.srmri.plato.core.discussionforum.service.DfAttachedFileService;

@Repository("dfThreadFileMapDao")
public class DfThreadFileMapDaoImpl implements DfThreadFileMapDao{

	public DfThreadFileMapDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private DfAttachedFileService attachedFileService;
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void dfDAddThreadFileMap(DfThreadFileMap threadFileMap) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(threadFileMap);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void dfDRemoveThreadFileMap(DfThreadFileMap threadFileMap) {
		// TODO Auto-generated method stub
		Long fileIdForDelete = threadFileMap.getFileId();
		sessionFactory.getCurrentSession().delete(threadFileMap);
		sessionFactory.getCurrentSession().flush();
		attachedFileService.dfSRemoveAttachedFile(fileIdForDelete);
	}

	@Override
	public List<DfThreadFileMap> dfDGetTheadFileMapList(Long threadId) {
		// TODO Auto-generated method stub
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(DfThreadFileMap.class);
		cri.add(Restrictions.eq("threadId", threadId));
		return cri.list();
	}

	@Override
	public List<Long> dfDGetFileList(Long threadId) {
		// TODO Auto-generated method stub
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(DfThreadFileMap.class);
		cri.add(Restrictions.eq("threadId", threadId));
		System.out.println(cri.list().size());
		List<DfThreadFileMap> objList = cri.list();

		List<Long> fileIdList = new ArrayList<Long>();
		
		if(!objList.isEmpty() || objList != null )
		{
			for(DfThreadFileMap obj: objList){
				fileIdList.add(obj.getFileId());
			}
		}
		return fileIdList;
	}

	@Override
	public DfThreadFileMap dfDGetThreadFileMap(Long fileId) {
		// TODO Auto-generated method stub
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(DfThreadFileMap.class);
		cri.add(Restrictions.eq("fileId", fileId));
		if(cri.list().isEmpty())
			return null;
		else
			return (DfThreadFileMap) cri.list().get(0);
	}

	@Override
	public void dfDAddThreadFileMap(Long threadId, Long uploadedFileId) {
		// TODO Auto-generated method stub
		DfThreadFileMap obj = new DfThreadFileMap();
		obj.setFileId(uploadedFileId);
		obj.setThreadId(threadId);
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void dfDRemoveThreadFileMap(Long fileId) {
		// TODO Auto-generated method stub
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(DfThreadFileMap.class);
		cri.add(Restrictions.eq("fileId", fileId));
		DfThreadFileMap obj = (DfThreadFileMap) cri.list().get(0);
		
		if(obj != null){
			sessionFactory.getCurrentSession().delete(obj);
			sessionFactory.getCurrentSession().flush();	
			attachedFileService.dfSRemoveAttachedFile(fileId);
		}	
	}
}
