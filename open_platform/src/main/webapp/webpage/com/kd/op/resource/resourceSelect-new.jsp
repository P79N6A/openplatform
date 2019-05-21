<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <script src=<%= request.getContextPath() + "/plug-in/api-test-treeview.js"%>></script>
    <style>
        .childTr{
            margin-left:20px;
        }
        .btn-div a{
            border-color:#ccc;
            border-radius:3px;
        }
        #step-1,#step-2,#step-3,#step-4,#step-5{
            padding-top:10px;
        }
        .table-div{
            overflow:auto;
            min-height: 200px;
            max-height: 300px;
        }
        .nodisplay{
            display:none;
        }
        .tree-icon{
            color:#00CAAB;
        }
    </style>
</head>
<body>
<div style="index:9999">
    <div class="container" style="width:100%;overflow-x:hidden;">
        <div class="panel panel-default">
            <div class="panel-body">
                <form id="form">
                <div class="table-responsive" style="max-height:300px;overflow: auto">
                    <table id="initTestList"></table>
                </div><br>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        initTest();

    })

    $('#btn_sub').click(function(){
        debugger;
        var params = {};
        var allData = loadAllData();
        var rootNodes = [];
        $.each(allData,function(i,row){
            if(row.parentId == null){
                rootNodes.push(row);
            }
        });
        getPara(rootNodes,params);
        params["appId"] = "WebTest";
        var paramString = JSON.stringify(params);

    })

    function getPara(nodes,params) {
        $.each(nodes,function(i,row){
            var children = getFirstLevelChildren(loadAllData(),row.id);
            if(children.length > 0){
                params[row.paramName] = {};
                getPara(children,params[row.paramName]);
            }else{
                params[row.paramName] = row.defaultValue;
            }
        });
    }

    function loadAllData(){
        return loadNowData($("#initTestList").find("tbody"));
    }

    function getFirstLevelChildren(allData,id){
        var children = new Array();
        $.each(allData,function(i,row){
            if(row.parentId == id){
                children.push(row);
            }
        });
        return children;
    }
    //加载参数列表
    function initTest() {
        var selects = $("#apiInfoList").bootstrapTable("getSelections");
        var loadedData = [];
        loadedData = ${paramMap};
        $.each(loadedData,function(i,row){
            if(getFirstLevelChildren(loadedData,row.id).length > 0){
                row.hidden = false;
                row.isLeaf = false;
            }else{
                row.isLeaf = true;
            }
        });
        $('#initTestList').bootstrapTable({
            data : loadedData,
            method : 'post', //请求方式（*）
            class: 'table table-hover table-bordered',
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            sidePagination: 'server',
            pagination: false,
            treeView: true,
            treeId: "id",
            treeField: "paramName",
            striped:false,
            rowAttributes: function (row, index) {
                return {
                    xx:index
                };
            },
            columns : [
                {
                    field: 'paramName',
                    title: '参数名称',
                    width:"10%",
                },{
                    field: 'parentId',
                    visible:false,
                },{
                    field: 'dataType',
                    visible:false,
                }, {
                    field: 'value',
                    title: '参数值',
                    width:"10%",
                    formatter:function(value,row,index){
                        if(value == undefined){
                            value = "";
                        }
                        var parentId = row.parentId;
                        if(parentId == undefined){
                            parentId = "";
                        }
                        var paramName = row.paramName;
                        if(paramName == undefined){
                            paramName = "";
                        }
                        var visibleState = row.visibleState;
                        var paramValue = row.value;
                        var dataType = row.dataType;

                        return "<input name='value' style='display:"+ visibleState +"' class='defaultValue' value='" + paramValue + "'/>"+
                            "<input name='parentId' type='hidden' value='" + parentId + "'/>"+
                            "<input name='dataType' type='hidden' value='" + dataType + "'/>"+
                            "<input name='paramName' type='hidden' value='" + paramName + "'/>"+
                            "<input name='isLeaf' type='hidden' value='" + row.isLeaf + "'/>"+
                            "<input name='id' type='hidden' value='" + row.id + "'/>";
                    }
                }],
        });
    }
</script>
</body>
</html>
