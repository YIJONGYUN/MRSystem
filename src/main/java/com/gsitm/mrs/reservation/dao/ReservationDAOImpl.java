package com.gsitm.mrs.reservation.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gsitm.mrs.reservation.dto.ReservationDTO;
import com.gsitm.mrs.user.dto.EmployeeDTO;

/**
 * ReservationDAO 인터페이스를 구현하는 클래스
 * 
 * @Package : com.gsitm.mrs.reservation.dao
 * @date : 2019. 5. 8.
 * @author : 이종윤
 * 
 * @date : 2019. 5. 29.
 * @author : 김재율
 */
@Repository
public class ReservationDAOImpl implements ReservationDAO {

	@Inject
	private SqlSession sqlSession;

	private static String namespace = "com.gsitm.mrs.mappers.ReservationMapper";
	
	/** 마이페이지 예약 현황 캘린더 */
	@Override
	public List<Map<String, Object>> getReservationInfo(String employeeNo) {
		return sqlSession.selectList(namespace +".getReservationInfo", employeeNo);
	}
	
	/** 마이페이지 캘린더 상세 조회 */
	@Override
	public Map<String, Object> getCalendarInfo(int reservationNo) {
		return sqlSession.selectOne(namespace +".getCalendarInfo", reservationNo);
	}
	
	/** 마이페이지 예약 현황 리스트*/
	@Override
	public List<Map<String, Object>> getReservationList(String employeeNo) {
		return sqlSession.selectList(namespace +".getReservationList", employeeNo);
	}
	
	/** 취소 사유 추가 */
	@Override
	public void insertCancel(Map<String, Object> map) {
		sqlSession.insert(namespace + ".insertCancel", map);
	}
	
	/** 참여 사원 이메일 리스트 조회 */
	@Override
	public List<String> getEmailList(int reservaionNo) {
		return sqlSession.selectList(namespace + ".getEmailList", reservaionNo);
	}
	
	/** 마이페이지 가장 최근 예약 표시 */
	@Override
	public List<Map<String, Object>> getLatestReservation(String employeeNo) {
		return sqlSession.selectList(namespace +".getLatestReservation", employeeNo);
	}
	
	/** 가장 최근 1개 가져오기 */
	public Map<String, Object> getOne(String employeeNo) {
		
		return sqlSession.selectOne(namespace +".getOne", employeeNo);
	}
	
	/** 대시보드 */
	@Override
	public List<Map<String, Object>> getDashBoard(int roomNo) {
		return sqlSession.selectList(namespace +".getDashBoard", roomNo);
	}
	
	/** 시작 버튼 처리 - 승인상태 변경 */
	public void updateStart(Map<String, Object> map) {
		sqlSession.update(namespace+".updateStart", map);
	}
	
	/** 끝 버튼 처리 - 대여물품 삭제 */
	public void deleteBorEquip(int reservationNo) {
		sqlSession.delete(namespace+".deleteBorEquip", reservationNo);
	}
	
	/** 회의실 목록 조회 */
	@Override
	public List<Map<String, Object>> getRoomList(int workplaceNo) {
		return sqlSession.selectList(namespace + ".getRoomList", workplaceNo);
	}
	
	/** 회의실 정보 조회 */
	@Override
	public Map<String, Object> getRoomInfo(int roomNo) {
		return sqlSession.selectOne(namespace + ".getRoomInfo", roomNo);
	}

	/** 회의실 비품 목록 조회 */
	@Override
	public List<Map<String, Object>> getEquipmentList(int roomNo) {
		return sqlSession.selectList(namespace + ".getEquipmentList", roomNo);
	}

	/** 사원 정보 조회 */
	@Override
	public EmployeeDTO getEmployeeInfo(String employeeNo) {
		return sqlSession.selectOne(namespace + ".getEmployeeInfo", employeeNo);
	}
	
	/** 초성에 해당하는 사원 목록 조회 */
	@Override
	public List<Map<String, Object>> getEmployeeListByChosung(String chosung) {
		return sqlSession.selectList(namespace + ".getEmployeeListByChosung", chosung);
	}

	/** 검색 키워드에 해당하는 사원 목록 조회 */
	@Override
	public List<Map<String, Object>> getEmployeeListBySearching(String keyword) {
		return sqlSession.selectList(namespace + ".getEmployeeListBySearching", keyword);
	}

	/** 사원들의 부서 정보 조회 */
	@Override
	public List<Map<String, Object>> getDepartmentList(Map<String, Object> map) {
		System.out.println(sqlSession.selectList(namespace + ".getDepartmentList", map));
		return sqlSession.selectList(namespace + ".getDepartmentList", map);
	}
	
	/** 사원들의 정보 조회 */
	@Override
	public List<Map<String, Object>> getEmployeeList(List<String> employees) {
		return sqlSession.selectList(namespace + ".getEmployeeList", employees);
	}
	
	/** 부서번호에 해당하는 부서 정보 조회 */
	@Override
	public List<Map<String, Object>> getDepartmentListByDeptNo(List<String> departmentNos) {
		return sqlSession.selectList(namespace + ".getDepartmentListByDeptNo", departmentNos);
	}
	
	/** 비품번호에 해당하는 비품 정보 조회  */
	@Override
	public List<Map<String, Object>> getEquipmentsByEquipNo(List<Integer> equipmentNos) {
		return sqlSession.selectList(namespace + ".getEquipmentsByEquipNo", equipmentNos);
	}

	/** 예약 번호 조회 */
	@Override
	public int getReservationNo() {
		return sqlSession.selectOne(namespace + ".getReservationNo");
	}
	
	/** 예약 번호로 예약 상세 정보 조회 */
	@Override
	public Map<String, Object> getReservationInfoByResNo(int reservationNo) {
		return sqlSession.selectOne(namespace + ".getReservationInfoByResNo", reservationNo);
	}

	/** 예약정보 DB에 삽입 */
	@Override
	public void insertReservation(ReservationDTO reservation) {
		sqlSession.insert(namespace + ".insertReservation", reservation);
	}


	/** 대기 예약 DB에 삽입 */
	@Override
	public void insertWaiting(int resNo) {
		sqlSession.insert(namespace + ".insertWaiting", resNo);
	}

	/** 회의 참여자 DB에 삽입  */
	public void insertParticipation(Map<String, Object> participationMap) {
		sqlSession.insert(namespace + ".insertParticipation", participationMap);
	}
	
	/** 회의 참여 부서 DB에 삽입 */
	@Override
	public void insertParticipateDepartment(Map<String, Object> departmentMap) {
		sqlSession.insert(namespace + ".insertParticipateDepartment", departmentMap);
	}

	/** 회의 대여 비품 DB에 삽입 */
	@Override
	public void insertBorrowedEquipments(Map<String, Object> borrwedEquipmentMap) {
		sqlSession.insert(namespace + ".insertBorrowedEquipments", borrwedEquipmentMap);
	}
	
	/** 해당 방의 예약정보 조회 */
	@Override
	public List<Map<String, Object>> getReservationsByRoomNo(int roomNo) {
		return sqlSession.selectList(namespace + ".getReservationsByRoomNo", roomNo);
	}
	
	/** 해당 날짜의 예약 정보 조회 */
	@Override
	public List<Map<String, Object>> getReservationsByDate(Map<String, Object> roomData) {
		return sqlSession.selectList(namespace + ".getReservationsByDate", roomData);
	}
	
	/** 해당 부서의 회의 참여자 수 조회 */
	@Override
	public int getNumOfParticipation(Map<String, Object> infoMap) {
		return sqlSession.selectOne(namespace + ".getNumOfParticipation", infoMap);
	}

 	/** 회의실 관리자 메일 조회 */
 	public String getAdminEmail(int roomNo){
		return sqlSession.selectOne(namespace + ".getAdminEmail", roomNo);
 	}
 	
 	/** 예약자의 상위결재자 정보 조회 */
 	public EmployeeDTO getMgrInfo (String empNo){
		return sqlSession.selectOne(namespace + ".getMgrEmail", empNo);
 	}
	
	/** 사원의 이름 조회 */
	@Override
	public String getEmpName(String empNo) {
		return sqlSession.selectOne(namespace + ".getEmpName", empNo);
	}
	
	/** 방 정보 조회 */
 	public String getRoomType(int roomNo) {
 		return sqlSession.selectOne(namespace + ".getRoomType", roomNo);
 	}
 	
 	/** 예약 번호로 예약 정보 조회 */
 	public ReservationDTO getReservationByResNo(int resNo) {
 		return sqlSession.selectOne(namespace + ".getReservationByResNo", resNo);
 	 }
 	
 	/** 주관 부서와 해당 부서의 참여자 수 조회 */
 	public List<Map<String, Object>> getMainDeptParticipationCount(int resNo){
 		return sqlSession.selectList(namespace + ".getMainDeptParticipationCount", resNo);
 	}
 	
 	/** 회의실 사용 비용 업데이트 */
 	public void updateMoney(Map<String, Object> moneyMap) {
 		sqlSession.update(namespace + ".updateMoney", moneyMap);
 	}
 	
 	/** 참여사원 정보 조회  */
 	public List<Map<String, Object>> getParticipationInfo(int resNo){
 		return sqlSession.selectList(namespace + ".getParticipationInfo", resNo);
 	}
 	
 	/** 자동 노쇼 처리 정보 조회 */
 	public List<Map<String, Object>> getNoshow(String timeStr){
 		return sqlSession.selectList(namespace + ".getNoshow", timeStr);
 	}
 	
 	/** 자동 종료 처리 정보 조회 */
 	public List<Map<String, Object>> getEnd(String timeStr){
 		return sqlSession.selectList(namespace + ".getEnd", timeStr);
 	}
	/* ------------- 관리자 ------------- */
	
	/** 승인 대기 목록 조회 */
	@Override
	public List<Map<String, Object>> getWaitingList() {
		return sqlSession.selectList(namespace + ".getWaitingList");
	}
	
	/** 예약 상태 변경 */
	@Override
	public void updateStatus(Map<String, Object> map) {
		sqlSession.update(namespace + ".updateStatus", map);
	}

	/** 관리자 승인 상태 변경 */
	@Override
	public void updateAdminApproval(Map<String, Object> map) {
		sqlSession.update(namespace + ".updateAdminApproval", map);
	}
	
	/** 상위결재자 승인 상태 변경 */
	@Override
	public void updateMgrApproval(Map<String, Object> map) {
		sqlSession.update(namespace + ".updateMgrApproval", map);
	};
	
	/** 반려 사유 추가 */
	@Override
	public void insertRefuse(Map<String, Object> map) {
		sqlSession.insert(namespace + ".insertRefuse", map);
	}

	/** 승인 반려 목록 조회 */
	@Override
	public List<Map<String, Object>> getApprovalCancelList() {
		return sqlSession.selectList(namespace + ".getApprovalCancelList");
	}

	/** 예약 완료 목록 조회 */
	@Override
	public List<Map<String, Object>> getSuccessList() {
		return sqlSession.selectList(namespace + ".getSuccessList");
	}

	/** 예약 취소 목록 조회 */
	@Override
	public List<Map<String, Object>> getReservationCancelList() {
		return sqlSession.selectList(namespace + ".getReservationCancelList");
	}

	@Override
	public int updateNoshow(String timeStr) {
		return sqlSession.update(namespace+".updateNoshow", timeStr);
	}

	@Override
	public int updateCheckEnd(String timeStr) {
		return sqlSession.update(namespace+".updateCheckEnd", timeStr);
	}

}
