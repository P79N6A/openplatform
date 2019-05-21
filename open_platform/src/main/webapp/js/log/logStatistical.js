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
		$("#userName").selectpicker("refresh");
		$("#logType").selectpicker("refresh");
		$("#search_loglevel").selectpicker("refresh");
		
		loginStatistical();
	})
	
		//查询按钮
	$("#search-btn").bind("click",function(){
		loginStatistical();
	})
	
	//获取登陆状态柱状图
	loginStatistical();
})
function loginStatistical() {
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	var search_loglevel = $("#search_loglevel").val();
	var logType = $("#logType").val();
	var userName= $("#userName").val();
	var xdata = '所有日志';
	if (search_loglevel == '1') {
		xdata='登陆';
	}else if(search_loglevel == '2'){
		xdata='退出';
	}else if(search_loglevel == '8'){
		xdata='配置管理';
	}else if(search_loglevel == '9'){
		xdata='交互配置';
	}else if(search_loglevel == '10'){
		xdata='系统配置';
	}else if(search_loglevel == '11'){
		xdata='越权访问';
	}else if(search_loglevel == '12'){
		xdata='IP变化过大';
	}else if(search_loglevel == '13'){
		xdata='连续登陆失败';
	}else if(search_loglevel == '14'){
		xdata='模型管理';
	}else if(search_loglevel == '15'){
		xdata='运行管理';
	}else if(search_loglevel == '16'){
		xdata='有序充电管理';
	}else if(search_loglevel == '7'){
		xdata='其他';
	}
	$.ajax({   
		  async : false,   //设置为false。请求为同步请求
		  cache:false,   	//不设置缓存
		  type: 'post',   
		  dataType : "json",   
		  data:{"beginDate":beginDate, "endDate":endDate, "search_loglevel":search_loglevel
			  	,"userName":userName, "logType":logType},
		  url: 'audit.do?getLogStatistical',//后台处理程序,获取显示数据    	
		  error: function () {//请求失败处理函数   
			  return false;
		  },   
		  success:function(data){
			var User = data.User; //log用户
			var Times = data.Times; //次数
			var myChart = echarts.init(document.getElementById('logStstistic'));
		    var option = {
		            title:{
		                text:"日志统计分析"
		            },
		            tooltip:{
		                text:"this is tool tip"
		            },
		            legend:{
		                data:[xdata]
		            },
		            xAxis:{
		                data:User
		            },
		            yAxis:{},
		            series:[{
		                        name:xdata,
		                        type:"bar",
		                        label:{
		                        	normal:{
		                        		posotion:'top',
		                        		show:true
		                        	}
		                        },
		                        data:Times
		                    }]
		        };
		    myChart.setOption(option);
		        
		  }   
		})	
}
