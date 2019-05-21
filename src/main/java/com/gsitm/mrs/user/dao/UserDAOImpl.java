package com.gsitm.mrs.user.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gsitm.mrs.user.dto.EmployeeDTO;

/**
 * UserDAO 인터페이스를 구현하는 클래스
 * 
 * @Package : com.gsitm.mrs.user.dao
 * @date : 2019. 5. 2.
 * @author : 이종윤
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Inject
	private SqlSession sqlSession;

	private static String namespace = "com.gsitm.mrs.mappers.UserMapper";
	
	/** 로그인 */
	@Override
	public EmployeeDTO login(EmployeeDTO employee) {
		return sqlSession.selectOne(namespace +".login", employee); 
	}
	
}
