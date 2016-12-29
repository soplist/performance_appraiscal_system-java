package com.jinrui.service;

import java.sql.Blob;
import java.util.List;

import com.jinrui.domain.UserResume;

public interface UserResumeService {
	public boolean insert(UserResume userResumeEntity);
	public Blob getUserResumePhoto(int id);
	public List<UserResume> getAllUserResume();
}
