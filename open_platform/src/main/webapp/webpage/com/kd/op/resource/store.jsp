<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>资源管理——存储和计算</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <%--<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">--%>
    <style type="text/css">
        tr:nth-child(even) {
            background: #f9f9f9;
        }
    </style>
    <style type="text/css">
        th{
            text-align:center;/** 设置水平方向居中 */
            vertical-align:middle/** 设置垂直方向居中 */
        }
        td{
            text-align:center;/** 设置水平方向居中 */
            vertical-align:middle/** 设置垂直方向居中 */
        }
    </style>
</head>

<body>
        <div class="container1">
    <div class="container1" style="padding-top: 0%">
        <%--<div class="local cleafix">--%>
            <%--<a>资源管理</a>--%>
            <%--<em>&gt;</em>--%>
            <%--<a href="store.jsp">存储和计算</a>--%>
        <%--</div>--%>
        <br>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12">
                <button type="button" class="btn btn-primary" style="background-color:#00caab; border: none">
                    <span class="glyphicon glyphicon-plus Btn"></span>&nbsp;录入</button>
                <button type="button" class="btn btn-primary" style="background-color:#6ed1f1; border: none">
                    <span class="glyphicon glyphicon-pencil Btn"></span>&nbsp;编辑</button>
                <button type="button" class="btn btn-primary" style="background-color:#99ccff; border: none">
                    <span class="glyphicon glyphicon-search Btn"></span>&nbsp;查看</button>
                <button type="button" class="btn btn-primary" style="background-color:white;border-color:gray; color:gray">
                    <span class="glyphicon glyphicon-search Btn"></span>&nbsp;检索</button>
            </div>
        </div>
        <br>
    </div>
    <div class="container1">
        <table class="table table-bordered">
            <thead>
                <tr style="background-color:#f9f9f9">
                    <th>
                        <input type="checkbox" id="">
                    </th>
                    <th>服务器名称</th>
                    <th>操作系统</th>
                    <th>是否为默认服务器</th>
                    <th>数据实时处理速度</th>
                    <th>CPU速度</th>
                    <th>内存容量</th>
                    <th>内存使用率</th>
                    <th>硬盘使用率</th>
                    <th>CPU使用率</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th>
                        <input type="checkbox" id="">
                    </th>
                    <td>云计算服务器1</td>
                    <td>Ubuntu</td>
                    <td>是</td>
                    <td>1ms</td>
                    <td>2600MHz</td>
                    <td>32G</td>
                    <td>12%</td>
                    <td>15%</td>
                    <td>20%</td>
                    <td><button type="button" class="btn btn-primary btn-sm" style="background-color:#2fd6d0; border: none">
                        <span class="glyphicon glyphicon-trash"></span>&nbsp;删除</button>
                        <button type="button" class="btn btn-primary btn-sm" style="background-color:#55c0db; border: none">
                        <span class="glyphicon glyphicon-pencil"></span>&nbsp;设为默认服务器</button></td>
                </tr>
                <tr>
                    <th>
                        <input type="checkbox" id="">
                    </th>
                    <td>云计算服务器2</td>
                    <td>Ubuntu</td>
                    <td>否</td>
                    <td>1ms</td>
                    <td>2600MHz</td>
                    <td>32G</td>
                    <td>12%</td>
                    <td>15%</td>
                    <td>20%</td>
                    <td><button type="button" class="btn btn-primary btn-sm" style="background-color:#2fd6d0; border: none">
                        <span class="glyphicon glyphicon-trash"></span>&nbsp;删除</button>
                        <button type="button" class="btn btn-primary btn-sm" style="background-color:#55c0db; border: none">
                        <span class="glyphicon glyphicon-pencil"></span>&nbsp;设为默认服务器</button></td>
                </tr>
                <tr>
                    <th>
                        <input type="checkbox" id="">
                    </th>
                    <td>云计算服务器3</td>
                    <td>Ubuntu</td>
                    <td>否</td>
                    <td>1ms</td>
                    <td>2600MHz</td>
                    <td>32G</td>
                    <td>12%</td>
                    <td>15%</td>
                    <td>20%</td>
                    <td><button type="button" class="btn btn-primary btn-sm" style="background-color:#2fd6d0; border: none">
                        <span class="glyphicon glyphicon-trash"></span>&nbsp;删除</button>
                        <button type="button" class="btn btn-primary btn-sm" style="background-color:#55c0db; border: none">
                        <span class="glyphicon glyphicon-pencil"></span>&nbsp;设为默认服务器</button></td>
                </tr>
            </tbody>
        </table>
    </div>
    </div>
</body>
<%--<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>--%>
<%--<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>

</html>