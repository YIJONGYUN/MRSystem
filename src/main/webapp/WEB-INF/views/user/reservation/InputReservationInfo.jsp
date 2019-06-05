<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>

<link rel= "stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/user/reservation-chooseDate.css">
<link rel= "stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/user/reservation-inputReservationInfo.css">


<!-- Main Content -->
<!-- 이 안에 내용 채우시면 됩니당 -->
	
<div id="content">

	<!-- Begin Page Content -->
	<div class="container-fluid">

	<div class="row">

		<!-- Begin Page Content -->
		<div class="container-fluid">

			<!-- Page Heading -->
			<div class="d-sm-flex align-items-center justify-content-between mb-4">
				<h1 class="h5 mb-0 text-gray-800"> <i class="fas fa-user"></i> 예약하기 > 예약 정보 입력 </h1>
			</div>

			<div class="row">
				<div class="card-body py-2 text-right">
					<span class="pull-right text-lg"> 본사 </span>

				</div>
			</div>

			<!-- Content Row -->
			
			<div class="row">
				<div class="col-sm-6 left-padding-zero" >
					<img id="room_img" alt="본사 몰디브 회의실의 사진" src="${pageContext.request.contextPath}/resources/img/maldives.jpg">	
					<div id="room_info_div" class="background-lightgrey font-black padding-content div-border">
						<h1 class="align-center color-title">${roomInfo.ROOMNAME }</h1>
						<p id="reservation-date" class="align-center"></p>
						<hr>
						<ul>
							<li>회의실 이름 : ${roomInfo.ROOMNAME}
							<li>회의실 위치 : ${roomInfo.WORKPLACEADDRESS}
							<li>수용인원 수 : ${roomInfo.CAPACITY}명
							<li>비치물품 : ${roomInfo.EQUIPMENTS}
							<li>네트워크 : 
								<c:choose>
									<c:when test="${roomInfo.NWAVAILABLE eq 'Y'}">
										사용가능
									</c:when>
									<c:otherwise>
										사용불가능
									</c:otherwise>
								</c:choose>
							<li>사용요금 : 1시간 당 10000원
							<li>관리자 : ${roomInfo.ADMINNAME}
						</ul>	
					</div>
				</div>
				
				<div class="col-sm-6">
					<!-- calendar -->
					<div id="calendar_div" class="background-lightgrey font-black padding-content div-border">
						<h4 class="color-title">예약 정보</h4>
						<hr>
						<form action="${pageContext.request.contextPath}/reservation/checkReservation" id="option_form" method="get">
							<input type="hidden" name="roomNo" value="${roomInfo.ROOMNO}"/>
							<input type="hidden" name="employeeNo" value=""/>
							<input type="hidden" name="startDate" value="">
							<input type="hidden" name="endDate" value="">
							<input type="hidden" name="equipments" value="">
							<ul>
							<div class="form-group">
							    <label for="exampleInputEmail1">Email address</label>
							    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
							    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
							  </div>
								<li>
									
									<label>예약자</label>
									<input type="text" value="${employeeInfo.name }" disabled><br>
								</li>
								<li>
									<label>연락처</label>
									<input type="text" value="${employeeInfo.phone }" disabled><br>
								</li>
								<li>
									<label>이메일</label>
									<input type="text" value="${employeeInfo.email }" disabled><br>
								</li>
								<li>
									<label>회의명</label>
									<input type="text" name="name">
								</li>
								<li>
									<label>회의구분</label>
									<select name="purpose">
									    <option value="internelMeeting">내부회의</option>
									    <option value="customerMeeting">고객미팅</option>
									    <option value="education">교육</option>
									</select>
								</li>
								<li>
									<label>참여인원</label>
									<button class="btn btn-primary">검색</button>
								</li>
								<li>
									<label>주관부서</label>
									<button class="btn btn-primary">검색</button>
								</li>
								<li>
									<label>협조부서</label>
									<button class="btn btn-primary">검색</button>
								</li>
							</ul>
						</form>
					</div>
					
					<div id="option_div" class="background-lightgrey font-black padding-content div-border">
						<h4 class="color-title">선택 내역</h4>
						<hr>
						<ul id="option_list">
							<c:forEach var="equip" items="${equipmentList}" >
								<li>${equip.NAME} 대여
									<c:choose>
										<c:when test="${!(empty equip.need) and (equip.need eq true)}">
											Y
										</c:when>
										<c:otherwise>
											N
										</c:otherwise>
									</c:choose>
								</li>
							</c:forEach>
							<li>간식준비 여부 ${reservationInfo.snackWant }
							</li>
						</ul>
					</div>
					<button class="btn btn-disabled" id="nextBtn" disabled>다음 단계</button>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</div>
	</div>

</div>


<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		// 예약 일자 값을 설정
		Date.prototype.format = function (f) {
		    if (!this.valueOf()) return " ";
		    var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];
		    var d = this;
		
		    return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {
		        switch ($1) {
		            case "yyyy": return d.getFullYear(); // 년 (4자리)
		            case "MM": return (d.getMonth() + 1).zf(2); // 월 (2자리)
		            case "dd": return d.getDate().zf(2); // 일 (2자리)
		            case "KS": return weekKorShortName[d.getDay()]; // 요일 (짧은 한글)
		            case "HH": return d.getHours().zf(2); // 시간 (24시간 기준, 2자리)
		            case "mm": return d.getMinutes().zf(2); // 분 (2자리)
		            case "a/p": return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분
		            default: return $1;
		        }
		    });
		};

		String.prototype.string = function (len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };
		String.prototype.zf = function (len) { return "0".string(len - this.length) + this; };
		Number.prototype.zf = function (len) { return this.toString().zf(len); };

		// 예약 시작날짜와 종료날짜
		var startDate=new Date("${reservationInfo.startDate}");
		var endDate=new Date("${reservationInfo.endDate}");
		
		var reserved_date;
		// 시작일자와 종료일자가 같으면 시간만 보여준다
		if(startDate.getFullYear()==endDate.getFullYear() &&
		   startDate.getMonth()==endDate.getMonth() &&
		   startDate.getDay()==endDate.getDay()){
			reserved_date=startDate.format('yyyy. MM. dd(KS) HH:mm')+"~"+endDate.format('HH:mm');
		} else{
			reserved_date=startDate.format('yyyy. MM. dd(KS) HH:mm')+"~"+endDate.format('yyyy. MM. dd(KS) HH:mm');
		}
		$("#reservation-date").text(reserved_date);
	})
</script>