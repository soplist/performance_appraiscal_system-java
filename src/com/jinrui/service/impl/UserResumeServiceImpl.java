package com.jinrui.service.impl;

import java.sql.Blob;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jinrui.dao.BaseDAO;
import com.jinrui.domain.UserResume;
import com.jinrui.service.UserResumeService;

public class UserResumeServiceImpl implements UserResumeService {

private BaseDAO<UserResume> baseDao;
	
    public BaseDAO getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	@Transactional
	public boolean insert(UserResume userResumeEntity) {
		// TODO Auto-generated method stub
		try{
		    baseDao.add(userResumeEntity);
		    return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public Blob getUserResumePhoto(int id) {
		// TODO Auto-generated method stub
		return baseDao.get(UserResume.class, id).getPhoto();
	}

	@Override
	public List<UserResume> getAllUserResume() {
		// TODO Auto-generated method stub
		String hql = "from UserResume";
		return baseDao.qryInfo(hql);
	}

}
