<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href='/resources/css/dashboard.css' rel='stylesheet' />

<!-- Main Content -->
<div id="content">

	<!-- Begin Page Content -->
	<div class="container-fluid">

		<!-- Begin Page Content -->
		<div class="container-fluid">

			<!-- Page Heading -->
			<div class="d-sm-flex align-items-center justify-content-between mb-4">
				<h1 id="name" class="h5 mb-0 text-gray-800">
					<i class="fas fa-user"></i> 대시보드 > <span id="workplaceNameTitle"></span> <span id="roomNameTitle"></span>
				</h1>
			</div>
			
			<input type="hidden" id="empNo" value="${login.employeeNo }">

			<!-- Content Row -->
			<div class="row">
				<div class="panel with-nav-tabs panel-primary">
					<div class="panel-heading">
						<ul class="nav nav-tabs">
							<!-- 지사 목록 DB 연동 (session에 담겨있는 지사 목록) -->
							<c:forEach items="${workplaceList}" var="list" varStatus="status">
								<li class="dropdown">
		                            <a href="#workplace${list.workplaceNo}" class="nav-link workplace-list" data-toggle="dropdown" value="${list.workplaceNo}"> ${list.name} <i class="fas fa-caret-down"></i></a>
		                            <ul class="dropdown-menu" role="menu" id="roomList${list.workplaceNo}">
		                            </ul>
		                        </li>
							</c:forEach>
						</ul>
					</div>
				
					<div class="panel-body">
						<div class="tab-content">
							<div id="calendar"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</div>
</div>
<!-- End of Main Content -->
<script>
/* 상위 결재자 요청 승인 메일 통해서 접속 */
$(function(){
	var status = getUrlParams();
	var type = status.type;		// 매니저 권한으로 들어온 건지?
	var resNo = status.resNo;	// 승인 처리할 예약 번호
	var mgrNo = status.mgrNo;	// 해당 예약의 신청자의 상위 결재자 사원번호

	history.replaceState({}, null, location.pathname);	// url에서 파라미터 숨기기
	
	// 해당 예약의 상위 결재자와 현재 로그인 아이디가 같으면 승인 & 반려 처리
	if (mgrNo == $("#empNo").val()) {
		if(type == "manager") {
			swal({
				title: '해당 예약을 승인처리 하시겠습니까?',
				text: "",
				type: 'question',
				showCancelButton: true,
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				    confirmButtonText: 'Yes',
				    cancelButtonText: 'No',
				}).then( (result) => {
					if (result.value) {
		  			$.ajax({
						url : "/reservation/mgrApproval",
						type : "POST",
						data : {
							reservationNo : resNo
						}, success : function(data) {
							swal('Success!', '예약 승인이 완료되었습니다.', 'success'
					    		).then(function(){
		  		    		    	location.href="/reservation/approvalWaitingList";
		  		    		    });
						}
					});
					} else {
						swal({
							title: '정말 반려하시겠습니까?',
							text: "이후 복구는 불가능합니다.",
							type: 'warning',
							showCancelButton: true,
							confirmButtonColor: '#3085d6',
							cancelButtonColor: '#d33',
							    confirmButtonText: 'Yes',
							    cancelButtonText: 'No',
							}).then( (result) => {
								if (result.value) {
									Swal.mixin({
										  input: 'text',
										  confirmButtonText: '확인',
										  showCancelButton: true
										}).queue([
										  {
										    title: '반려 사유를 작성하세요',
										    text: '해당 사유는 신청자에게 메일로 전송됩니다.'
										  },
										]).then((result) => {
										  if (result.value) {
											  /* 반려 사유 아무것도 작성하지 않았을 경우 */
											  if ($.trim(result.value[0]) == "") {
												  return;
											  } else {
									  			$.ajax({
													url : "/reservation/mgrRefuse",
													type : "POST",
													data : {
														reservationNo : resNo,
														reason : result.value[0],
														status : 2
													}, success : function(data) {
														swal('Success!', '반려가 완료되었습니다.', 'success'
											    		).then(function(){
								  		    		    	location.href="/reservation/statusCalendar";
								  		    		    });
													}
												});
											  }
									  			
										  }
										})
								}
								  
							});
					}
					  
				});
		}
	// 같지 않으면 로그아웃 시키기
	} 
// 	else {
// 		swal('접근 제한', '잘못된 접근입니다.', 'error'
// 		).then(function(){
// 		    	location.href="/user/logout";
// 	    });
// 	}

});

/* url에 함께 온 파라미터 정보 */
function getUrlParams() {
    var params = {};
    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, 
    		function(str, key, value) { params[key] = value; });
    return params;
}
</script>

<script>

		var calendar;
		document.addEventListener('DOMContentLoaded',function() {
						var calendarEl = document.getElementById('calendar');
	
						calendar = new FullCalendar.Calendar(
								calendarEl,
								{
									plugins : [ 'interaction', 'dayGrid' ],	
									defaultDate : new Date(),
									editable : true,
									eventLimit : true, // allow "more" link when too many events
									events : [
										
										<c:forEach items="${roomDashBoard}" var="list" varStatus="status">
											<c:if test="${list.STATUS ne 3 }">
											{ 
												id : '${list.RESERVATIONNO}',
												title : '${list.RESERVATIONNAME}',
												start : '${list.STARTDATE}',
												end : '${list.ENDDATE}',
												backgroundColor: 
													<c:if test="${list.STATUS eq 0 }">
														'skyblue',
													</c:if>
													<c:if test="${list.STATUS eq 1 }">
														'pink',
													</c:if>
													<c:if test="${list.STATUS eq 2 }">
														'orange',
													</c:if>
												borderColor: 
													<c:if test="${list.STATUS eq 0 }">
														'skyblue'
													</c:if>
													<c:if test="${list.STATUS eq 1 }">
														'pink'
													</c:if>
													<c:if test="${list.STATUS eq 2 }">
														'orange'
													</c:if>
											},
											</c:if>
										</c:forEach>
									], 
									eventClick: function(info) {
										
										var reservationNo = info.event.id;
										console.log(reservationNo);
										
										$.ajax({
									        url : "/reservation/getCalendar",
									        data : {"reservationNo": reservationNo},
									        type : "GET",
									        success : function(data){
									        	
									        	console.log(data);
									        	
												$("#employeeName").val(data.EMPNAME);
								        	 	$("#roomName").val(data.ROOMNAME);
									        	$("#reservationName").val(data.RESERVATIONNAME);
									    	 	$("#purpose").val(data.PURPOSE);
									    	    $("#startDate").val(data.STARTDATE +" ~ "+data.ENDDATE);
								        	 	
								        	 	$("#infoReservationModal").modal('show');
									        },
									        error : function(){
									            alert("전체 예약 현황 조회 에러");
									        }
									    });
									},
									contentHeight: "auto"								
								});
	
						calendar.render();
	
					});	
</script>
<script type="text/javascript">


	$(function() {

		var workplaceNo = "";
		
		/* 회의실 버튼 눌렀을 때 */
		$(document).on("click", ".roomBtn", function() {
			var roomNo = $(this).attr('value');
			
			// 대시보드 > XXX > YYY (회의실 이름 동적 변경)
			$("#workplaceNameTitle").text($(this).parent().prev().text());
			$("#roomNameTitle").text(" > "+$(this).text());
			
			$('.workplace-list').removeClass('active');
			$(this).parent().prev().addClass('active');
			
			$.ajax({
				url : "/reservation/getRoomDashBoard",
				data : {
					"roomNo" : roomNo
				},
				type : "POST",
				dataType : 'json',
				success : function(data) {
					calendar.removeAllEvents();
					
					$.each(data.roomDashBoard, function(index, item){
						if(item.STATUS!=3) {
							var obj = {
										id : item.RESNO,
										title : item.RESERVATIONNAME,
										start : item.STARTDATE,
										end : item.ENDDATE
								};
							calendar.addEvent(obj);
						}
					})
				},
				error : function() {
					alert("회의실별 대쉬보드 조회 에러");
				}
			});
			
		});

		/* 지사 탭 클릭 이벤트 */
		$(".workplace-list").each(function(index, item) {
			workplaceNo = $(item).attr('value');

			// 대시보드 > XXX (지사 이름 동적 변경)
// 			$("#workplaceNameTitle").text($(item).text());

			$.ajax({
				url : "/statistic/getRoomListByWorkplaceNo",
				data : {
					"workplaceNo" : workplaceNo
				},
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					console.log(data.roomList);
					$("#roomList"+workplaceNo).empty();
					$.each(data.roomList, function(index, item){
						$("#roomList"+workplaceNo).append('<li class="roomBtn" value="'+ item.roomNo +'" style="text-align: center;"><a href="#" data-toggle="tab">'+ item.name + '</a></li>');
					});
				},
				error : function() {
					alert("지사별 해당 회의실 조회 에러");
				}
			});
			
		});

		/* 페이지 처음 로딩 시 지사 탭 제일 처음 클릭 이벤트 디폴트 처리 */
 		$('.roomBtn:first').trigger('click');
		
	});
</script>
<!-- Modal -->
<jsp:include page="include/infoReservation.jsp" />

</body>
