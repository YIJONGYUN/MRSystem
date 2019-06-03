<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Add Equipment Modal-->
<div class="modal fade" id="editEquipModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">비품 수정</h5>
				<button type="button" class="close" data-dismiss="modal"> &times;</button>
			</div>
			
			<form action="/resource/addEquipment" method="post">
				<div class="modal-body">
					
					<div class="form-group">
						<div class="row">
							<div class="col-xs-2 col-sm-2 text-center">
								<label> 비품명 </label>
							</div>
							<div class="col-xs-9 col-sm-9">
								<input type="text" class="form-control" id="editName" name="name" placeholder="비품 이름을 입력하세요" />
							</div>
						
							<div class="clearfix"></div>
						</div>
						
						<br>
						
						<div class="row">
							<div class="col-xs-2 col-sm-2 text-center">
								<label> 구매일 </label>
							</div>
							<div class="col-xs-9 col-sm-9">
				                <div class="input-group input-append date" id="editDatePicker">
				                    <input type="date" class="form-control" name="buyDate" id="editBuyDate"/>
				                    <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
							</div>
						
							<div class="clearfix"></div>
						</div>
						
						<br>
						
						<div class="row">
							<div class="col-xs-2 col-sm-2 text-center">
								<label> 비치장소 </label>
							</div>
							<div class="col-xs-9 col-sm-9">
								<input type="text" class="form-control" id="editRoomName" readonly="readonly"/>
							</div>
						
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
					<input type="submit" class="btn btn-primary" value="추가" >
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	$(function() {
	    $('#editDatePicker').datepicker({
            format: 'yyyy-mm-dd'
        });
	    
	    $('#option-droup-demo-edit').multiselect({buttonWidth: '340px'});
	});
	
	/* 모달 사라졌을 때 입력 값 초기화 */
	$('.modal').on('hidden.bs.modal', function (e) {
	  $(this).find('form')[0].reset()
	});
	
	var arrSelected = [];
	$('#option-droup-demo-edit').on('change', function(){
	    var selected = $(this).find("option:selected");
	    arrSelected = [];
	    selected.each(function(){
	       arrSelected.push($(this).val());
	    });
	    $('#roomNo').val(arrSelected);
	});
	
	var equipNo="";
	var equipName="";
    var buyDate="";
    var roomName = "";
	
	$(function() {     
        $('#editEquipModal').on('show.bs.modal', function(event) {          
        	equipNo = $(event.relatedTarget).data('equipno');
        	equipName = $(event.relatedTarget).data('equipname');
        	buyDate = $(event.relatedTarget).data('buydate');
        	roomName = $(event.relatedTarget).data('roomname');
        	
        	$("#editEquipNo").val(equipNo);
        	$("#editName").val(equipName);
    	    $("#editBuyDate").val(buyDate);
    	    $("#editRoomName").val(roomName);
        });
    });
	
</script>