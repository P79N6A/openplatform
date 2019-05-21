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
	$.ajax({   
		  async : false,   //设置为false。请求为同步请求
		  cache:false,   	//不设置缓存
		  type: 'post',   
		  dataType : "json",   
		  data:{"beginDate":beginDate, "endDate":endDate},
		  url: 'audit.do?getLoginStatistical',//后台处理程序,获取显示数据    	
		  error: function () {//请求失败处理函数   
			  return false;
		  },   
		  success:function(data){
			var ipUser = data.ipUser; //ip变动过大用户 
			var ipTimes = data.ipTimes; //ip变动过大对应次数
			var reloginUsers = data.reloginUsers; //连续登陆失败用户
			var reloginUsertimes = data.reloginUsertimes//连续登陆失败用户对应次数
			//alert("ipUser=" + ipUser+",ipTimes="+ipTimes+",reloginTime="+reloginTime)
			var myChart = echarts.init(document.getElementById('loginStstistic1'));
			var myChart2 = echarts.init(document.getElementById('loginStstistic2'));
		    var option = {
		            title:{
		                text:"登陆统计分析"
		            },
		            tooltip:{
		                text:"this is tool tip"
		            },
		            legend:{
		                data:['ip变动过大']
		            },
		            xAxis:{
		                data:ipUser
		            },
		            yAxis:{},
		            series:[{
		                        name:["ip变动过大"],
		                        type:"bar",
		                        label:{
		                        	normal:{
		                        		posotion:'top',
		                        		show:true
		                        	}
		                        },
		                        data:ipTimes
		                    }]
		        };
		    var option2 = {
		            title:{
		                text:""
		            },
		            tooltip:{
		                text:"this is tool tip"
		            },
		            legend:{
		                data:['连续登陆失败次数']
		            },
		            xAxis:{
		                data:reloginUsers
		            },
		            yAxis:{},
		            series:[{
		                        name:["连续登陆失败次数"],
		                        type:"bar",
		                        label:{
		                        	normal:{
		                        		posotion:'top',
		                        		show:true
		                        	}
		                        },
		                        data:reloginUsertimes
		                    }]
		        };
		    myChart.setOption(option);
		    myChart2.setOption(option2);
		        
		  }   
		})	
}
