<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/webpage/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.fixed-table-toolbar .bs-bars,.fixed-table-toolbar .search,.fixed-table-toolbar .columns
	{
	margin-bottom: 2px;
}
</style>
<script src=<%= request.getContextPath() + "/js/log/MessageList.js" %>
	type="text/javascript"></script>

</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="search_loglevel">消息类型</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_type">
								<option value="">全部</option>
								<option value="1">订阅</option>
								<option value="2">发布</option>
							</select>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="begin_date">日期选择</label>
						<div class="col-sm-6 no-padding">
							<div class="jeitem">
								<div class="jeinpbox">
									<input type="text" class="jeinput" id="beginDate"
										placeholder="请选择开始时间" >
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="begin_date"></label>
						<div class="col-sm-6 no-padding">
							<div class="jeitem">
								<div class="jeinpbox">
									<input type="text" class="jeinput" id="endDate"
										placeholder="请选择结束时间" >
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="search_loglevel">消息区域</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="position">
								<option value="">全部</option>
								<option value="1">消息内网</option>
								<option value="3">消息外网</option>
							</select>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_name">关联主题</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_topic">
                            	<option value="">全部</option>
                            	<c:forEach items="${topics}" var="topics">
                            		<option value=${topics.id}>${topics.name}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_name">关联标签</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_tag">
                            	<option value="">全部</option>
                            	<c:forEach items="${tags}" var="tags">
                            		<option value=${tags.id}>${tags.name}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="search-btn">
					<span class="glyphicon glyphicon-search"></span>查询
				</button>
				<button type="button" class="btn btn-warning" id="reset-btn">
					<span class="glyphicon glyphicon-refresh"></span>重置
				</button>
			</div>
		</form>
	</div>
	<table id="messageLog"  style="table-layout:fixed"></table>
	<div class="modal" id="messageModal">
		<div class="modal-dialog" style="">
			<div class="modal-content"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
$('#datetimepicker').datetimepicker();
/* $('#datetimepicker1').datetimepicker(); */
$('#datetimepicker1').prop("readonly", false).datetimepicker({
	defaultDate: $('.datetimepicker1').val(),  
    dateFormat: 'YYYY-mm-dd',  
    showSecond: true,  
    timeFormat: 'hh:mm:ss',  
    stepHour: 1,  
    stepMinute: 1,  
    stepSecond: 1 
	 });
</script>
</html>