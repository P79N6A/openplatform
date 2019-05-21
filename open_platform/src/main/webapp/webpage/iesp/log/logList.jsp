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
<script src=<%= request.getContextPath() + "/js/log/LogList.js" %>
	type="text/javascript">
</script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="search_loglevel">日志类型</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_loglevel" name="loglevel">
								<option value="">全部</option>
								<option value="1">登陆</option>
								<option value="2">退出</option>
								<option value="8">配置管理</option>
								<option value="9">交互配置</option>
								<option value="10">系统配置</option>
								<option value="11">越权访问</option>
								<option value="12">IP变化过大</option>
								<option value="13">连续登陆失败</option>
								<option value="14">模型管理</option>
								<option value="15">运行管理</option>
								<option value="16">有序充电管理</option>
								<option value="7">其他</option>

							</select>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="begin_date">日期选择</label>
						<div class="col-sm-6 no-padding">
							<div class="jeitem">
								<div class="jeinpbox">
									<input type="text" class="jeinput" id="beginDate"  name="operatetime"
										placeholder="请选择开始时间" autocomplete="off">
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
									<input type="text" class="jeinput" id="endDate" name="operatetime"
										placeholder="请选择结束时间"  autocomplete="off">
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_sysId">用户名称</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" data-size=10 id="search_userName">
                            	<option value="">全部</option>
                            	<c:forEach items="${userList}" var="user">
                            		<option value=${user.id}>${user.userName}</option>
                            	</c:forEach>
                            </select>
						</div>
					</div>
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_sysId">事件类型</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" data-size=10 id="search_Type">
                            	<option value="">全部</option>
                            		<option value="0">业务事件</option>
                            		<option value="1">系统事件</option>
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
				<button type="button" class="btn btn-primary" id="load-btn">
					<span class="glyphicon glyphicon-save"></span>导出
				</button>
			</div>
		</form>
	</div>
	<table id="TSLog"></table>
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
<script type="text/javascript">
	</script>
</html>