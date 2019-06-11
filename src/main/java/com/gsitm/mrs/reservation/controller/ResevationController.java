package com.gsitm.mrs.reservation.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsitm.mrs.reservation.dto.ReservationDTO;
import com.gsitm.mrs.reservation.service.ReservationService;
import com.gsitm.mrs.user.dto.EmployeeDTO;

/**
 * 예약 관련 프로젝트 Controller @RequestMapping("/reservation") URI 매칭
 * 
 * @Package : com.gsitm.mrs.reservation.controller
 * @date : 2019. 5. 2.
 * @author : 이종윤
 * 
 * @date : 2019. 5. 24.
 * @author : 김나윤
 */

@Controller
@RequestMapping("/reservation")
public class ResevationController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResevationController.class);
	
	@Inject
	private ReservationService service;
	
	/* ------------- 사용자 ------------- */
	
	@RequestMapping(value = "/statusCalendar", method = RequestMethod.GET)
	public String statusCalendar(HttpSession session, String employeeNo, Model model) {
		
		logger.info("(사용자) 마이페이지 - 예약 현황 달력");
		
		Object user = session.getAttribute("login");
		EmployeeDTO employee = (EmployeeDTO) user;
		
		employeeNo = employee.getEmployeeNo();
		
		List<Map<String, Object>> reservationInfo = service.getReservationInfo(employeeNo);
		
		logger.info(reservationInfo+"");
		
		model.addAttribute("reservationInfo", reservationInfo);
		
		return "user/mypage/statusCalendar";
	}
	
	@RequestMapping(value = "/getCalendar", method = RequestMethod.GET)
	public String getCalendar(int reservationNo, Model model) {
		
		logger.info("(사용자) 마이페이지 - 캘린더 상세 정보");
		
		List<ReservationDTO> calendarInfo = service.getCalendarInfo(reservationNo);
		
		logger.info(calendarInfo+"");
		
		model.addAttribute("calendarInfo", calendarInfo);
		
		return "user/mypage/statusCalendar";
		
	}
	
	
	@RequestMapping(value = "/statusList", method = RequestMethod.GET)
	public String statusList(HttpSession session, String employeeNo, Model model) {
		
		logger.info("(사용자) 마이페이지 - 예약 현황 리스트");
		
		Object user = session.getAttribute("login");
		EmployeeDTO employee = (EmployeeDTO) user;
		
		employeeNo = employee.getEmployeeNo();
		
		List<Map<String, Object>> reservationList = service.getReservationList(employeeNo);
		
		logger.info(reservationList+"");
		
		model.addAttribute("reservationList", reservationList);
		
		return "user/mypage/statusList";
	}
	
	
	@RequestMapping(value = "/updateReservation", method = RequestMethod.POST)
	public String updateReservation(String reservationNo) throws Exception {

		logger.info("(사용자) 마이페이지 - 예약 현황 리스트 - 상태 취소 변경");
		
		service.updateReservation(Integer.parseInt(reservationNo));

		return "redirect:/reservation/statusList";
	}
	
	@RequestMapping(value = "/getLatest", method = RequestMethod.GET)
	public String getLatest(HttpSession session, String employeeNo, Model model) {
		
		logger.info("(사용자) 마이페이지 - 가장 최근 예약 표시");
		
		Object user = session.getAttribute("login");
		EmployeeDTO employee = (EmployeeDTO) user;
		
		employeeNo = employee.getEmployeeNo();
		
		List<ReservationDTO> latestReservation = service.getLatestReservation(employeeNo);
		
		model.addAttribute("latestReservation", latestReservation);
		
		return "user/mypage/statusCalendar";
		
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard() {
		
		logger.info("(사용자) 대시보드");
		
		return "user/dashboard/dashboard";
	}
	
	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public String room() {
		
		logger.info("(사용자) 회의실 정보");
		
		return "user/reservation/room";
	}
	
	@RequestMapping(value = "/shortTerm_chooseDate/{roomNo}", method = RequestMethod.GET)
	public String chooseDate(@PathVariable int roomNo, Model model) {
		
		logger.info("(사용자) 예약 - 단기 예약 일자 선택");
		
		Map<String, Object> roomInfo=service.getRoomInfo(roomNo);
		List<Map<String, Object>> equipmentList=service.getEquipmentList(roomNo);
		
		model.addAttribute("roomInfo", roomInfo);
		model.addAttribute("equipmentList", equipmentList);
		
		return "user/reservation/shortTerm_chooseDate";
	}
	
	@RequestMapping(value = "/InputReservationInfo", method = RequestMethod.GET)
	public String InputReservationInfo(@ModelAttribute ReservationDTO reservationDto, 
			HttpServletRequest request, Model model) {
			
		logger.info("(사용자) 예약 - 예약 정보 입력");
		
		service.InputReservationInfo(request, reservationDto, model);
		return "user/reservation/InputReservationInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getEmployeeListByChosung", method = RequestMethod.GET)
	public Map<String, Object> getEmployeeListByChosung(@RequestParam String chosung) {
		
		logger.info("(사용자) 예약-초성에 해당하는 사원 목록 조회");
		// 사원번호, 이름 조회
		Map<String, Object> employeeList=new HashMap<>();
		employeeList.put("employeeList", service.getEmployeeListByChosung(chosung));
		return employeeList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getEmployeeListBySearching", method = RequestMethod.GET)
	public Map<String, Object> getEmployeeListBySearching(@RequestParam String keyword) {
		
		logger.info("(사용자) 예약 - 검색 키워드에 해당하는 사원 목록 조회");
		// 사원번호, 이름 조회
		Map<String, Object> employeeList=new HashMap<>();
		employeeList.put("employeeList", service.getEmployeeListBySearching(keyword));
		return employeeList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDepartmentList", method = RequestMethod.POST)
	public Map<String, Object> getDepartmentList(@RequestParam String jsonData) {
		
		logger.info("(사용자) 예약 - 사원들의 부서 목록 조회");
		// 사원번호, 이름 조회
		Map<String, Object> map=new HashMap<>();
		//map.put("departmentList", service.getDepartmentList(participation));
		return map;
	}
	
	/* ------------- 관리자 ------------- */
	
	/**
	 * (관리자) 승인 대기 목록 조회 페이지 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/approvalWaitingList", method = RequestMethod.GET)
	public String approvalWaitingList(Model model) {
		
		logger.info("(관리자) 승인 대기 목록");
		
		List<Map<String, Object>> waitingList = service.getWaitingList();
		
		model.addAttribute("waitingList", waitingList);
		
		return "admin/reservation/approvalWatingList";
	}
	
	/**
	 * (관리자) 승인 반려 목록 조회 페이지 
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/approvalCancelList", method = RequestMethod.GET)
	public String approvalCancelList(Model model) {
		
		logger.info("(관리자) 승인 반려 목록");
		
		List<Map<String, Object>> approvalCancelList = service.getApprovalCancelList();
		
		model.addAttribute("approvalCancelList", approvalCancelList);
		
		return "admin/reservation/approvalCancelList";
	}
	
	/**
	 * (관리자) 예약 완료 목록 조회 페이지 
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reservationSuccessList", method = RequestMethod.GET)
	public String reservationSuccessList(Model model) {
		
		logger.info("(관리자) 예약 완료 목록");
		
		List<Map<String, Object>> successList = service.getSuccessList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		for (int i = 0; i < successList.size(); i++) {
			Map<String, Object> map = successList.get(i);
			String startDate = (String) map.get("STARTDATE");
			String endDate = (String) map.get("ENDDATE");
			
			if (today.compareTo(startDate) < 0) {
				map.put("STATUS", 0);	// 미사용
			}
			
			if (today.compareTo(startDate) > 0 && today.compareTo(endDate) < 0) {
				map.put("STATUS", 1);	// 사용 중
			}
			
			if (today.compareTo(endDate) > 0) {
				map.put("STATUS", 2);	// 사용 완료
			}
		}
		
		model.addAttribute("successList", successList);
		
		return "admin/reservation/reservationSuccessList";
	}
	
	/**
	 * (관리자) 예약 취소 목록 조회 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reservationCancelList", method = RequestMethod.GET)
	public String reservationCancelList(Model model) {
		
		logger.info("(관리자) 예약 취소 목록");
		
		List<Map<String, Object>> reservationCancelList = service.getReservationCancelList();
		
		model.addAttribute("reservationCancelList", reservationCancelList);
		
		return "admin/reservation/reservationCancelList";
	}
	
}
