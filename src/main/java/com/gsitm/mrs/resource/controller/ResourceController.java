package com.gsitm.mrs.resource.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsitm.mrs.reservation.dto.BorrowedEquipmentDTO;
import com.gsitm.mrs.resource.dto.EquipmentDTO;
import com.gsitm.mrs.resource.dto.WorkplaceDTO;
import com.gsitm.mrs.resource.service.ResourceService;

/**
 * 자원 관련 프로젝트 Controller @RequestMapping("/reservation") URI 매칭
 * 
 * @Package : com.gsitm.mrs.resource.controller
 * @date : 2019. 5. 8.
 * @author : 이종윤
 * 
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Inject
	private ResourceService service;

	/**
	 * 지사 관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/workplaceList", method = RequestMethod.GET)
	public String workplaceList(Model model) {

		logger.info("(관리자) 지사 관리");

		List<WorkplaceDTO> workplaceList = service.getWorkplaceList();

		model.addAttribute("workplaceList", workplaceList);

		return "admin/resource/workplaceList";
	}

	/**
	 * 지사 추가
	 * 
	 * @param workplaceDTO
	 *            추가할 지사 객체
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addWorkplace", method = RequestMethod.POST)
	public String addWorkplace(WorkplaceDTO workplaceDTO) throws Exception {

		service.addWorkplace(workplaceDTO);

		return "redirect:/resource/workplaceList";
	}

	/**
	 * 지사 수정
	 * 
	 * @param workplaceDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editWorkplace", method = RequestMethod.POST)
	public String editWorkplace(WorkplaceDTO workplaceDTO) throws Exception {

		service.editWorkplace(workplaceDTO);

		return "redirect:/resource/workplaceList";
	}

	@RequestMapping(value = "/roomList", method = RequestMethod.GET)
	public String roomList() {

		logger.info("(관리자) 회의실 관리");

		return "admin/resource/roomList";
	}

	/**
	 * 비품 관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/equipmentList", method = RequestMethod.GET)
	public String equipmentList(Model model) {

		logger.info("(관리자) 비품 관리");

		List<Map<String, Object>> equipmentList = service.getEquipmentList();
		List<Map<String, Object>> roomList = service.getRoomListForEquipment();
		List<BorrowedEquipmentDTO> borrowedEquipmentList = service.getBorrowedEquipmentList();
		List<Object> workplaceNameList = new ArrayList<>();

		for (Map<String, Object> map : roomList) {

			Set<Map.Entry<String, Object>> entries = map.entrySet();

			for (Map.Entry<String, Object> entry : entries) {

				/* 지사 이름 중복 제거 */
				if (entry.getKey().equals("WORKPLACENAME")) {
					if (!workplaceNameList.contains(entry.getValue()))
						workplaceNameList.add(entry.getValue());
				}
			}
		}
		
		for (Map<String, Object> map : equipmentList) {

			Integer equipNo = Integer.valueOf(map.get("EQUIPNO").toString());
			
			for (int i = 0; i < borrowedEquipmentList.size(); i++) {
				if (borrowedEquipmentList.get(i).getEquipmentNo() == equipNo) {
					map.put("isBorrowed", "Y");
				} 
			}
		}
		
		model.addAttribute("equipmentList", equipmentList);
		model.addAttribute("roomList", roomList);
		model.addAttribute("workplaceNameList", workplaceNameList);

		return "admin/resource/equipmentList";
	}
	
	/**
	 * 비품 추가
	 * 
	 * @param equipmentDTO 추가할 비품 객체
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
	public String addEquipment(EquipmentDTO equipmentDTO, String roomNoList) throws Exception {

		 StringTokenizer token = new StringTokenizer(roomNoList , ",");
		 while(token.hasMoreTokens()) {
			 equipmentDTO.setRoomNo(Integer.parseInt(token.nextToken()));
			 service.addEquipment(equipmentDTO);
		 }

		return "redirect:/resource/equipmentList";
	}
	
	@RequestMapping(value = "/deleteEquipment", method = RequestMethod.POST)
	public String deleteEquipment(String equipmentNo) throws Exception {

		 service.deleteEquipment(Integer.parseInt(equipmentNo));

		return "redirect:/resource/equipmentList";
	}
}
