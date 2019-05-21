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
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts-gl.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts-china.js"%>"></script>

<script src=<%= request.getContextPath() + "/js/log/loginStatistical.js" %> type="text/javascript"></script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
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
	<div class="col-xs-6 pull-left no-padding">
	 	<div id="loginStstistic1" style="height: 400px;"></div>
	</div>
	<div class="col-xs-6 pull-left no-padding">
	 	<div id="loginStstistic2" style="height: 400px;"></div>
	</div>
</body>
</html>