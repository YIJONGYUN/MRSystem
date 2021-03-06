package com.gsitm.mrs.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsitm.mrs.reservation.dto.BorrowedEquipmentDTO;
import com.gsitm.mrs.resource.dao.ResourceDAO;
import com.gsitm.mrs.resource.dto.EquipmentDTO;
import com.gsitm.mrs.resource.dto.RoomDTO;
import com.gsitm.mrs.resource.dto.WorkplaceDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class ResourceDaoTest {

	@Inject
	private ResourceDAO dao;

	Logger logger = Logger.getLogger(ResourceDaoTest.class);

	/** 지사 목록 조회 테스트 */
	@Test
	public void TestWorkplaceList() {

		List<WorkplaceDTO> list = dao.getWorkplaceList();

		for (WorkplaceDTO workplaceDTO : list) {
			logger.info(workplaceDTO);
		}

	}
	
	/** 회의실 목록 조회 테스트 */
	@Test
	public void TestRoomList() {

		List<Map<String, Object>> list = dao.getRoomList();

		for (Map<String, Object> map : list) {
			logger.info(map);
		}

	}

	/** 비품 목록 조회 테스트 */
	@Test
	public void TestEquipmentList() {

		List<Map<String, Object>> list = dao.getEquipmentList();

		for (Map<String, Object> map : list) {
			logger.info(map);
		}

	}

	/** 지사 추가 테스트 */
	@Test
	public void TestAddWorkplace() {

		dao.addWorkplace(new WorkplaceDTO(1, "GS SHOP", "서울특별시 영등포구 문래동6가 선유로 75"));

		logger.info("지사 추가 완료!");

	}

	/** 지사 수정 테스트 */
	@Test
	public void TestEditWorkplace() {

		dao.editWorkplace(new WorkplaceDTO(7, "수정수정", "수정"));

		logger.info("지사 수정 완료!");

	}

	/** 비품 추가 테스트 */
	@Test
	public void TestAddEquipment() {

		dao.addEquipment(new EquipmentDTO(1, 1, "에어컨", "2019-05-24"));

		logger.info("비품 추가 완료!");

	}

	/** 비품 추가를 위한 지사 및 회의실 정보 조회 테스트 */
	@Test
	public void TestRoomListForEquipment() {

		List<Map<String, Object>> list = dao.getRoomList();
		List<Object> workplaceNameList = new ArrayList<>();

		for (Map<String, Object> map : list) {
			logger.info(map);

			Set<Map.Entry<String, Object>> entries = map.entrySet();

			for (Map.Entry<String, Object> entry : entries) {
				System.out.print("key: " + entry.getKey());
				System.out.println(", Value: " + entry.getValue());

				/* 지사 이름 중복 제거 */
				if (entry.getKey().equals("WORKPLACENAME")) {
					if (!workplaceNameList.contains(entry.getValue()))
						workplaceNameList.add(entry.getValue());
				}
			}
		}
		System.out.println("----------------------------------");

		for (Object object : workplaceNameList) {
			System.out.println(object);
		}

		System.out.println("----------------------------------");

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);

			Set<Map.Entry<String, Object>> entries = map.entrySet();

			for (Map.Entry<String, Object> entry : entries) {
				if (entry.getValue().equals("본사")) {
					System.out.println(map.toString());
				}

			}
		}
	}

	/** 지사 삭제 테스트 */
	@Test
	public void TestDeleteEquipment() {

		dao.deleteEquipment(41);

		logger.info("비품 삭제 완료!");
	}

	/** 대여 비품 조회 테스트 */
	@Test
	public void TestBorrowedEquipmentList() {

		List<BorrowedEquipmentDTO> list = dao.getBorrowedEquipmentList();

		for (BorrowedEquipmentDTO borrowedEquipmentDTO : list) {
			logger.info(borrowedEquipmentDTO);
		}
	}

	@Test
	public void TestEquipment() {
		List<Map<String, Object>> equipmentList = dao.getEquipmentList();
		List<BorrowedEquipmentDTO> borrowedEquipmentList = dao.getBorrowedEquipmentList();

		for (Map<String, Object> map : equipmentList) {

			Integer equipNo = Integer.valueOf(map.get("EQUIPNO").toString());
			
			for (int i = 0; i < borrowedEquipmentList.size(); i++) {
				if (borrowedEquipmentList.get(i).getEquipmentNo() == equipNo) {
					System.out.println(equipNo);
					map.put("isBorrowed", "Y");
					System.out.println(borrowedEquipmentList.get(i).getEquipmentNo());
				} else {
//					map.put("isBorrowed", "N");
				}
			}

		}
		
		for (Map<String, Object> map : equipmentList) {
			logger.info(map);
		}
	}
	
	/** 비품 수정 테스트 */
	@Test
	public void TestEditEquipment() {

		dao.editEquipment(new EquipmentDTO(35, 7, "비품 수정", "2019-06-03"));

		logger.info("비품 수정 완료!");

	}
	
	/** 비품 이름 중복 제거 테스트 */
	@Test
	public void TestEquipmentDistinct() {

		List<String> list = dao.getEquipmentListDistinct();
		
		for (String string : list) {
			logger.info(string);
		}

	}
	
	/** 회의실 추가 테스트 */
	@Test
	public void TestAddRoom() {
		
		RoomDTO roomDTO = new RoomDTO(1, 1, "Test222", "회의실", 10, "Y", "0f2e7167-5412-4080-8d4a-23cc9bdceb47_hawaii.jpg", "admin_it0003");
		dao.addRoom(roomDTO);
		logger.info("현재 시퀀스 : " + roomDTO.getRoomNo());
		logger.info("회의실 추가 완료");
	}


}
