<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/basic/editTag.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="id" name="id" value="${tag.id}" />
		    	<input type="hidden" id="createTime" name="createTime" value="${tag.createTime}" />
		    	<input type="hidden" id="state" name="state" value="${tag.state}" />
		        <div class="form-group">
		        	<label for="name" class="col-sm-2 control-label">标签名称</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="name" name="name" readonly="readonly" class="form-control" value="${tag.name}" />
		        	</div>
		        </div>
		        <div class="form-group">
					<label for="topicId" class="col-sm-2 control-label">所属主题</label>
					<div class="col-sm-10">
						<select class="form-control" id="topicId" name="topicId">
							<c:forEach items="${topics}" var="topic">
							  <c:choose>
								<c:when test="${topic.id == tag.topicId}">
									<c:if test="${topic.position == '01'}">
										<option selected value=${topic.id}>${tag.topicName}(内网)</option>
									</c:if>
									<c:if test="${topic.position == '03'}">
										<option selected value=${topic.id}>${tag.topicName}(外网)</option>
									</c:if>	
								</c:when>
								<c:otherwise>
									<c:if test="${topic.position == '01'}">
										<option value=${topic.id}>${topic.name}(内网)</option>
									</c:if>
									<c:if test="${topic.position == '03'}">
										<option value=${topic.id}>${topic.name}(外网)</option>
									</c:if>
								</c:otherwise>
							  </c:choose>
                           	</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="remark" rows="3">${tag.remark}</textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>