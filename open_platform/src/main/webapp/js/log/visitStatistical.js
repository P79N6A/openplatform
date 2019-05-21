$(document).ready(function(){
	 jeDate("#beginDate",{
	        format: "YYYY-MM-DD hh:mm:ss"
	    });
		 jeDate("#endDate",{
			 format: "YYYY-MM-DD hh:mm:ss"
		 });
	//重置按钮
	$("#reset-btn").bind("click",function(){
		$('#searchForm').get(0).reset();
		visitStatistical();
	})
	
		//查询按钮
	$("#search-btn").bind("click",function(){
		visitStatistical();
	})
	
	//获取登陆状态柱状图
	visitStatistical();
})

function visitStatistical() {
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	$.ajax({   
		  async : false,   //设置为false。请求为同步请求
		  cache:false,   	//不设置缓存
		  type: 'post',   
		  dataType : "json",   
		  data:{"beginDate":beginDate, "endDate":endDate},
		  url: 'audit.do?getVisitStatistical',//后台处理程序,获取显示数据    	
		  error: function () {//请求失败处理函数   
			  return false;
		  },   
		  success:function(data){ 
			var visitTimes = data.visitTimes;  
			var visitUser = data.visitUser; 
			var visitChart = echarts.init(document.getElementById('visitUser'));
		    var visitOption = {
		            title:{
		                text:"访问统计分析"
		            },
		            tooltip:{
		                text:"this is tool tip"
		            },
		            legend:{
		                data:['越权访问']
		            },
		            xAxis:{
		                data:visitUser
		            },
		            yAxis:{},
		            series:[{
		                        name:["越权访问"],
		                        type:"bar",
		                        label:{
		                        	normal:{
		                        		posotion:'top',
		                        		show:true
		                        	}
		                        },
		                        data:visitTimes
		                    }]
		        };
		    visitChart.setOption(visitOption);
		    }   
		})	
}
