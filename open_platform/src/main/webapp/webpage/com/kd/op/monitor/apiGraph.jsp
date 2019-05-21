<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="shortcut icon" href="images/favicon.ico">
    <style>
        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
            margin-bottom:2px;
        }
        .table{
            table-layout: fixed;
        }
        .ztree{
            display:none;
            z-index: 1;
            background-color: white;
            position: absolute;
            border:1px solid #4aa5ff;
            width:100%;
            max-height:200px;
            overflow: auto;
        }
        .ztree ul{
            height:100%;
        }
    </style>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.min.css" %> type="text/css">
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
    <script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.js" %> type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/com/kd/op/monitor/apiGraph.js"%>"></script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 14px;">
    <div class="search-div">
        <form id="searchForm" class="searchForm" class="form-horizontal">
            <div class="form-horizontal col-xs-10 pull-left" role="form">
                    <div class="col-xs-4 form-group no-padding">
                        <label class="col-sm-5 control-label near-padding-right" style="font-size:17px;font-weight:inherit" for="search_api">能力中心</label>
                        <div class="col-sm-7 no-padding">
                            <input id="orgName" class="form-control" readonly="readonly" />
                            <input id="orgCode" type="hidden" name="groupId" />
                            <div class="ztree" style="">
                                <ul id="treeDemo"></ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4 form-group no-padding">
                        <label class="col-sm-5 control-label near-padding-right" style="font-size:17px;font-weight:inherit" for="search_api">能力</label>
                        <div class="col-sm-7 no-padding">
                            <select class="selectpicker form-control" id="search_api">
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-2 pull-left no-padding">
                <button type="button" class="btn btn-success" id="search-btn">
                    <span class="glyphicon glyphicon-search"></span>查询
                </button>
                <%--<button type="button" class="btn btn-warning" id="reset-btn">
                    <span class="glyphicon glyphicon-refresh"></span>重置
                </button>--%>
            </div>
        </form>
    </div>
    <hr>
    <div id="graph" class="col-md-12" style="height:550px;margin:0 auto;"></div>
</body>
<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
