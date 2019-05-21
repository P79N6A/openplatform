package com.kd.op.util;

public class TaskUtil {
	public String taskType(String type){
		String name="";
		switch(type){
		case "1":name="手动任务";
			break;
		case "2":name="自动任务";
			break;
		default:
			name = "";
			break;
		}
		return name;
	}
	public String freeType(String freeType){
		String name="";
		switch(freeType){
		case "1":name="秒";
			break;
		case "2":name="分";
			break;
		case "3":name="时";
			break;
		case "4":name="天";
			break;
		case "5":name="周";
			break;
		case "6":name="月";
			break;
		case "7":name="年";
			break;
		default:
			name = "";
			break;
		}
		return name;
	}
	public String incrFlag(int incrFlag){
		String name="";
		switch(incrFlag){
		case 1:name="全量";
			break;
		case 2:name="增量";
			break;
		default:
			name = "";
			break;
		}
		return name;
	}
	public String userState(short status){
		String name="";
		switch(status){
		case 1:name="已激活";
			break;
		case 2:name="已删除";
			break;
		case 3:name="新建";
			break;
		case 4:name="编辑";
			break;
		case 0:name="锁定";
			break;
		default:
			name = "";
			break;
		}
		return name;
	}
	public String exceptionLevel(String level){
		String name="";
		switch(level){
		case "1":name="普通异常";
			break;
		case "2":name="严重异常";
			break;
		default:break;
		}
		return name;
	}
	public String roleStatus(short status){
		String name="";
		switch(status){
		case (short)1:name="待审核";
		break;
		case (short)2:name="审核成功";
		break;
		case (short)3:name="审核失败";
		break;
		default: break;
		}
		return name;
	}
}
