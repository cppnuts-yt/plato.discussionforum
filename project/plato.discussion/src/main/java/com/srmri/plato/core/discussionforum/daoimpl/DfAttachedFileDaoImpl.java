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

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.srmri.plato.core.discussionforum.dao.DfAttachedFileDao;
import com.srmri.plato.core.discussionforum.entity.DfAttachedFile;
import com.srmri.plato.core.discussionforum.entity.DfThreadReplyFileMap;
import com.srmri.plato.core.discussionforum.service.DfThreadReplyFileMapService;

@Repository("dfAttachedFileDao")
public class DfAttachedFileDaoImpl implements DfAttachedFileDao{

	public DfAttachedFileDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 *  Method definition
	 *  Add Attached File
	 */
	@Override
	public Long dfDAddAttachedFile(DfAttachedFile attachedFile) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(attachedFile);
		sessionFactory.getCurrentSession().flush();
		return attachedFile.getFileId();
	}

	/*
	 * Method definition
	 * Remove Attached File
	 */
	@Override
	public void dfDRemoveAttachedFile(Long fileId) {
		DfAttachedFile fileObj= (DfAttachedFile) sessionFactory.getCurrentSession().load(DfAttachedFile.class, fileId);		
		sessionFactory.getCurrentSession().delete(fileObj);
		sessionFactory.getCurrentSession().flush();
	}
	
	@Override
	public void dfDRemoveAttachedFile(DfAttachedFile file){
		
		sessionFactory.getCurrentSession().delete(file);
		sessionFactory.getCurrentSession().flush();
	}
	
	@Override
	public DfAttachedFile dfDGetAttachedFile(Long fileId) {
		// TODO Auto-generated method stub
		Criteria cri = sessionFactory.getCurrentSession().createCriteria(DfAttachedFile.class);
		@SuppressWarnings("unchecked")
		List<DfAttachedFile> fileList = cri.add(Restrictions.eq("fileId", fileId)).list();
		if(!fileList.isEmpty())
				return fileList.get(0);
		else
			return new DfAttachedFile();
	}
}
