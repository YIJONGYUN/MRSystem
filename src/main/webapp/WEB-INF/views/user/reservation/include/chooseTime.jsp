<%@ page contentType="text/html; charset=UTF-8"%>
<style>
	#time-label{
		color: #464646;	
	}
	
	#reservation-info{
		margin: 7px 0 0 10px;
	    font-size: 15px;
	}
	
	.disable-btn{
		background-color: grey;
	    color: white;
	    display: inline;
	    width: 30%;
	}
	
	.active-btn{
		background-color: rgb(54,93,205);
	    color: white;
	    display: inline;
	    width: 30%;
	}
	
	.modal-footer{
		display: inline;
	    text-align: center;
	 }
	 
	 #amTimeScheduleList > li{
	 	display: inline;
	 	margin: 10px;
	 }
	 
	 #pmTimeScheduleList > li{
	 	display: inline;
	 	margin: 10px;
	 }
	
	 .can-reserve-time{
	    background-color: rgb(221,227,247);
	 }
	 
	 #amTimeScheduleList span:hover, #pmTimeScheduleList span:hover{
	 	cursor: pointer;
	 }
	 
	 #amTimeSchedule>span, #pmTimeSchedule>span{
	 	padding-left: 59px;
	 	color: gray;
	 }
	 
	 #amTimeScheduleList, #pmTimeScheduleList{
	 	margin: 0;
	 	padding-left: 50px;
	 }
	 
	 #pmTimeSchedule{
	 	margin-top: 20px;
	 }
	 
	 .modal-body{
	 	margin-bottom: 15px;
	 }
	 
	 .modal{
	 	text-align: center;
	 }
	 
	 @media screen and (min-width: 768px) { 
        .modal:before {
                display: inline-block;
                vertical-align: middle;
                content: " ";
                height: 100%;
        }
	}
	 
	.modal-dialog {
	        display: inline-block;
	        text-align: left;
	        vertical-align: middle;
	}
</style>

<!-- Add Work place Modal-->
<div class="modal fade" id="chooseTimeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="time-label"></h5>
				<p id="reservation-info">30분 단위 예약가능 </p>
				<button type="button" class="close" data-dismiss="modal"> &times;</button>
			</div>
			<div class="modal-body">
		        <div id="amTimeSchedule">
		        	<span>오전</span>
		        	<ul id="amTimeScheduleList">
		        		<li><span class="time can-reserve-time">9:00</span></li>
		        		<li><span class="time can-reserve-time">9:30</span></li>
		        		<li><span class="time can-reserve-time">10:00</span></li>
		        		<li><span class="time can-reserve-time">10:30</span></li>
		        		<li><span class="time can-reserve-time">11:00</span></li>
		        		<li><span class="time can-reserve-time">11:30</span></li>
		        	</ul>
		        </div>
		        <div id="pmTimeSchedule">
		        	<span>오후</span>
		        	<ul id="pmTimeScheduleList">
		        		<li><span class="time can-reserve-time">13:00</span></li>
		        		<li><span class="time can-reserve-time">13:30</span></li>
		        		<li><span class="time can-reserve-time">14:00</span></li>
		        		<li><span class="time can-reserve-time">14:30</span></li>
		        		<li><span class="time can-reserve-time">15:00</span></li>
		        		<li><span class="time can-reserve-time">15:30</span></li>
		        		<li><span class="time can-reserve-time">16:00</span></li>
		        		<li><span class="time can-reserve-time">16:30</span></li>
		        		<li><span class="time can-reserve-time">17:00</span></li>
		        		<li><span class="time can-reserve-time">17:30</span></li>
		        		<li><span class="time can-reserve-time">18:00</span></li>
		        	</ul>
		        </div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-secondary btn-md disable-btn" id="choose-complete-btn" type="button" data-dismiss="modal" disabled>확인</button>
				<button class="btn btn-secondary btn-md disable-btn" id="choose-anotherDay-btn" type="button" disabled>종료일자 선택(장기예약)</button>			
			</div>
		</div>
	</div>
</div>
