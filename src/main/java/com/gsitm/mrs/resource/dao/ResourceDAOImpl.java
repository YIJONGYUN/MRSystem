package com.gsitm.mrs.resource.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gsitm.mrs.reservation.dto.BorrowedEquipmentDTO;
import com.gsitm.mrs.resource.dto.EquipmentDTO;
import com.gsitm.mrs.resource.dto.RoomDTO;
import com.gsitm.mrs.resource.dto.WorkplaceDTO;

/**
 * ResourceDAO 인터페이스를 구현하는 클래스
 * 
 * @Package : com.gsitm.mrs.resource.dao
 * @date : 2019. 5. 8.
 * @author : 이종윤
 */
@Repository
public class ResourceDAOImpl implements ResourceDAO {

	@Inject
	private SqlSession sqlSession;

	private static String namespace = "com.gsitm.mrs.mappers.ResourceMapper";
	
	/* ------------- 지사 ------------- */

	/** 지사 목록 조회 */
	@Override
	public List<WorkplaceDTO> getWorkplaceList() {
		return sqlSession.selectList(namespace + ".getWorkplaceList");
	}
	
	/** 지사 추가 */
	@Override
	public void addWorkplace(WorkplaceDTO workplaceDTO) {
		sqlSession.insert(namespace + ".addWorkplace", workplaceDTO);		
	}

	/** 지사 수정 */
	@Override
	public void editWorkplace(WorkplaceDTO workplaceDTO) {
		sqlSession.update(namespace + ".editWorkplace", workplaceDTO);
	}
	
	/** 지사 삭제 */
	@Override
	public void deleteWorkplace(int workplaceNo) {
		sqlSession.update(namespace + ".deleteWorkplace", workplaceNo);
	}
	
	
	/* ------------- 회의실 ------------- */
	
	/** 회의실 목록 조회 */
	@Override
	public List<Map<String, Object>> getRoomList() {
		return sqlSession.selectList(namespace + ".getRoomList");
	}
	
	/** 회의실 추가 */
	@Override
	public int addRoom(RoomDTO roomDTO) {
		return sqlSession.insert(namespace + ".addRoom", roomDTO);
	}
	
	/** 회의실 수정 */
	@Override
	public void editRoom(RoomDTO roomDTO) {
		sqlSession.update(namespace + ".editRoom", roomDTO);
	}
	
	/** 회의실 삭제 */
	@Override
	public void deleteRoom(int roomNo) {
		sqlSession.update(namespace + ".deleteRoom", roomNo);
	}

	/* ------------- 비품 ------------- */

	/** 비품 목록 조회 */
	@Override
	public List<Map<String, Object>> getEquipmentList() {
		return sqlSession.selectList(namespace + ".getEquipmentList");
	}
	
	/** 대여 비품 목록 조회 */
	@Override
	public List<BorrowedEquipmentDTO> getBorrowedEquipmentList() {
		return sqlSession.selectList(namespace + ".getBorrowedEquipmentList");
	}
	
	/** 비품 목록 이름 중복제거 조회 */
	@Override
	public List<String> getEquipmentListDistinct() {
		return sqlSession.selectList(namespace + ".getEquipmentListDistinct");
	}

	/** 비품 추가*/
	@Override
	public void addEquipment(EquipmentDTO equipmentDTO) {
		sqlSession.insert(namespace + ".addEquipment", equipmentDTO);		
	}

	/** 비품 삭제 */
	@Override
	public void deleteEquipment(int equipmentNo) {
		sqlSession.delete(namespace + ".deleteEquipment", equipmentNo);		
	}

	/** 비품 수정 */
	@Override
	public void editEquipment(EquipmentDTO equipmentDTO) {
		sqlSession.update(namespace + ".editEquipment", equipmentDTO);
	}

}
