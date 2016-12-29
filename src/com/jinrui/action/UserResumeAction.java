package com.jinrui.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.jinrui.domain.UserResume;
import com.jinrui.service.UserResumeService;
import com.opensymphony.xwork2.ActionSupport;

public class UserResumeAction extends ActionSupport {
	
	private String realName;
	private Integer departmentId;
	private String position;
    private String joinTime;
	private boolean status;
	private File photo;
	
	private UserResumeService userResumeService;
	private SessionFactory sessionFactory;
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
    
	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
    
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}
	
	public UserResumeService getUserResumeService() {
		return userResumeService;
	}

	public void setUserResumeService(UserResumeService userResumeService) {
		this.userResumeService = userResumeService;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public String insertUserResume(){
		UserResume userResume = new UserResume();
		
		userResume.setRealName(realName);
		userResume.setDepartmentId(departmentId);
		userResume.setPosition(position);
		userResume.setStatus(status);
		
		Session hibernateSession = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date =  format.parse(joinTime);
			userResume.setJoinTime(date);
			
			if(photo != null){
			    hibernateSession = sessionFactory.openSession();
	            LobHelper lobHelper = hibernateSession.getLobHelper();
			    FileInputStream fis = new FileInputStream(photo);
			    userResume.setPhoto(lobHelper.createBlob(fis, fis.available()));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(hibernateSession!=null){
				hibernateSession.close();
			}
		}
		userResumeService.insert(userResume);
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		List<UserResume> userResumeList = userResumeService.getAllUserResume();
		httpSession.setAttribute("userResumeList", userResumeList);
		
		return "insert_success";
	}
	
	public String getUserResumePhoto(){
		Blob photo = userResumeService.getUserResumePhoto(4);
		byte[] b = null;
        try {
            if (photo != null) {
                long in = 1;
                b = photo.getBytes(in, (int) (photo.length()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		HttpServletResponse response = null;

        ServletOutputStream out = null;
        try {
            response = ServletActionContext.getResponse();
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
	}
	
	public String getAllUserResume(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		List<UserResume> userResumeList = userResumeService.getAllUserResume();
		session.setAttribute("userResumeList", userResumeList);
		return "get_user_resume_list_success";
	}
}
