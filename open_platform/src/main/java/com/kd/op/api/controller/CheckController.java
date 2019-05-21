package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.enums.RegisterRouteEnum;
import com.kd.op.common.Pagination;
import com.kd.op.util.Encrypt;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.StringTransUtil;
import com.kd.op.util.rtdb.RtdbCommon;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.NumberComparator;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.RoleService;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sunbox.api.model.ResponseVo;
import sunbox.core.hsf.api.SysManageService;
import sunbox.core.hsf.vo.OperatorItem;
import sunbox.core.vo.api.OpenAPIResponse;
import sunbox.gateway.api.model.system.OperatorItemModel;
import sunbox.gateway.api.service.system.SysSupportApiService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/check")
public class CheckController {

	private static final Logger logger = Logger.getLogger(CheckController.class);
	
	private UserService userService;
	private SystemService systemService;
	@Autowired
	private RoleService roleService;
	@Resource
	private SysSupportApiService sysSupportApiService;
	@Resource
	private SysManageService sysManageService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 跳转审核用户界面
	 * @return
	 */
	@RequestMapping(params = "approvalUserList")
	public String approvalUserList() {
		return "/iesp/system/approvalUserList";
	}
	
	/**
	 * 待审核用户列表展示
	 * @param limit
	 * @param offset
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getapprovalUserList")
	@ResponseBody
	public JSONObject getapprovalUserList(Integer limit, Integer offset, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String, Object> params = new HashMap<>();
		// 处理前台发过来的参数
		String userName = request.getParameter("userName");

		if (userName != null && !userName.isEmpty()) {
            String s = StringTransUtil.stringReplace(userName);
            params.put("userName", "%" + s + "%");
		}
		//待审核状态
		params.put("checkStatus", Globals.User_Audit_Wait);
		String[] codes = new String[3];
		codes[0] = ResourceParams.adminCode;
		codes[1] = ResourceParams.isp_check;
		codes[2] = ResourceParams.audit;
		params.put("roleCode",codes);
		
		try{
			Pagination<TSUser> pagination = userService.getUserList(limit, offset, params);
			for (TSUser baseUser : pagination.getItems()) {
				JSONObject obj = new JSONObject();
				obj.put("id", baseUser.getId());
				obj.put("userName", baseUser.getUserName());
				obj.put("realName", baseUser.getRealName());
				obj.put("userKey", baseUser.getUserKey());
				obj.put("status", baseUser.getStatus());
				obj.put("checkStatus", baseUser.getCheckStatus());
				obj.put("registerType",baseUser.getRegisterType());
				array.add(obj);
			}
			if(offset==1) {
				systemService.recordQueryLog("check.user.list", Globals.MODULE_SYSTEM, "", params);
				if(userName!=null && !userName.isEmpty()) {
				systemService.addLog("查看待审核用户列表成功,查询条件为："+userName, Globals.MODULE_SYSTEM, Globals.Log_Leavel_INFO,"1");
				if(Pattern.matches("[A-Za-z0-9_]+",userName)) {
					logger.debug("查看待审核用户列表成功");
				}else{
					logger.debug("待审核用户名格式错误");
				}
				}else{
					systemService.addLog("查看待审核用户列表成功", Globals.MODULE_SYSTEM, Globals.Log_Leavel_INFO,"1");
				}
				logger.debug("查看待审核用户列表成功");
			}

			result.put("total", pagination.getRowsCount());
			result.put("rows", array);
			return result;
		}catch(Exception e){
			systemService.addLog("查看待审核用户列表失败", Globals.MODULE_SYSTEM,Globals.Log_Leavel_ERROR, "0");
			logger.debug("查看待审核用户列表失败");
//			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 跳转修改用户审核状态页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="editApprovalUser")
	public ModelAndView editApprovalUserView(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		TSBaseUser baseUser = new TSBaseUser();
		
		baseUser = userService.getById(id);
		
		ModelAndView model = new ModelAndView("/iesp/system/editApprovalUser");
		model.addObject("baseUser", baseUser);
		return model;
	}
	
	/**
	 * 审核用户操作
	 * @param baseUser
	 * @param request
	 * @return
	 */
	@RequestMapping(params="updateApprovalUser")
	@ResponseBody
	public JSONObject updateApprovalUser(TSBaseUser baseUser,HttpServletRequest request) {
		JSONObject result = new JSONObject();
		//用户id
		String userId = request.getParameter("baseUserId");
		//审核状态
		String checkStatus = request.getParameter("checkStatus");
		Short status = 0;
		//审核意见
		String approvalOpinion = request.getParameter("approvalOpinion");
		
		if(checkStatus != null && !checkStatus.isEmpty()) {
			status = Short.parseShort(checkStatus);
		}
		
		//log信息
		String logMessage = "";
		if ("2".equals(checkStatus)) {
			logMessage = "审核通过";
		} else if ("3".equals(checkStatus)) {
			logMessage = "审核失败";
		}
		
		try {
			//首先判断用户的来源是哪里
			TSUser user = userService.getById(userId);
			//车联网用户授权
			if(user.getRegisterType() != null && user.getRegisterType().equals(RegisterRouteEnum.chelianwang.getCode())){
				//获取accessToken
				String accessToken = RtdbCommon.loadAccessToken();
				logger.info("接入参数：" + accessToken);
				if(accessToken == null || accessToken.isEmpty()){
					result.put("msg","获取车联网的连接参数失败！");
					result.put("success", false);
					return result;
				}
				Client client = ClientManager.getInstance().getClient(request.getSession().getId());
				if (client != null) {
					logger.info("授权operator的参数：" + client.getToken());
				}
				if(client == null || client.getToken().isEmpty()){
					result.put("msg","获取车联网的连接参数失败！");
					result.put("success", false);
					return result;
				}
				//给车联网账号进行添加标签
				OperatorItemModel operator = new OperatorItemModel();
				operator.setBusinessLabels(ResourceParams.businessLabel + "");
				operator.setAccessToken(accessToken);
				operator.setId(Integer.parseInt(user.getThirdId()));
				operator.setToken(client.getToken());

				ResponseVo vo = sysSupportApiService.authOperatorBiz(operator);
				logger.info(JSONObject.toJSONString(vo));
				if(vo.getStatus() != 0){
					result.put("msg","车联网账号授权标签失败！");
					result.put("success", false);
					return result;
				}
			}
			userService.updateApprovalByUserId(userId, status, approvalOpinion);
			result.put("success", true);
			systemService.addLog("更新审核状态成功，更新内容为：" + baseUser.getUserName() + logMessage, Globals.MODULE_SYSTEM, Globals.Log_Leavel_INFO,"1");
			logger.debug("更新审核状态：" + baseUser.getUserName() + " 成功");
		} catch (Exception e) {
			logger.error("error:",e);
			result.put("success", false);
			result.put("msg", "发生未知错误");
			systemService.addLog("更新审核状态：" +baseUser.getUserName()  + " 失败" + e.getMessage(), Globals.MODULE_SYSTEM, Globals.Log_Leavel_ERROR,"0");
			/*logger.error("更新审核状态：" + baseUser.getUserName()  + " 失败," + e.getMessage());*/
		}
		return result;
	} 
	
	/**
	 * 跳转审核角色界面
	 * @return
	 */
	@RequestMapping(params = "approvalRoleList")
	public String approvalRoleList() {
		return "/iesp/system/approvalRoleList";
	}
	
	/**
	 * 获取待审核角色的列表信息
	 * @param limit
	 * @param offset
	 * @param request
	 * @return
	 */
	@RequestMapping(params="getApprovalRoleList")
	@ResponseBody
	public JSONObject getApprovalRoleList(Integer limit,Integer offset,HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String, Object> params = new HashMap<>();
		//处理前台发过来的参数
		String roleName = request.getParameter("roleName");
		if(roleName != null && !roleName.isEmpty()) {
            String s = StringTransUtil.stringReplace(roleName);
            params.put("roleName", "%" + s + "%");
		}
		//设置待审核的查询条件
		params.put("checkStatus", Globals.User_Audit_Wait);
		
		Pagination<TSRole> pagination = roleService.getTopicList(limit, offset, params);
		for(TSRole role:pagination.getItems()) {
			JSONObject obj = new JSONObject();
			obj.put("id", role.getId());
			obj.put("roleName", role.getRoleName());
			obj.put("roleCode", role.getRoleCode());
			obj.put("checkStatus",  role.getCheckStatus());
			array.add(obj);
		}
		
		if(offset==1) {
			systemService.recordQueryLog("check.role.list", Globals.MODULE_SYSTEM, "", params);
			logger.debug("查看待审核角色列表成功");
		}
		result.put("total", pagination.getRowsCount());
		result.put("rows", array);
		return result;
	}
	
	/**
	 * 跳转待审核用户的权限树画面
	 * @param roleId
	 * @return
	 */
	@RequestMapping(params = "approvalConfigFun")
	public ModelAndView approvalConfigFun(String roleId) {
		ModelAndView model = new ModelAndView("/iesp/system/approvalConfigFunctions");
		model.addObject("roleId", roleId);
		return model;
	}
	
	/**
	 * 查看待审核用户的权限树
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "approvalAuthority")
	@ResponseBody
	public JSONObject approvalAuthority(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		String roleId = request.getParameter("roleId");
		//获取角色对应的菜单范围
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		cq.notEq("functionLevel", Short.parseShort("-1"));
		//设置显示状态为1的
		cq.eq("showStatus", Short.parseShort("1"));
		Object[] objects = roleService.getFunctionsByRoleId(roleId);
		
		JSONArray array = new JSONArray();
		if(objects != null && objects.length > 0) {
			cq.in("id",objects);
			cq.add();
			List<TSFunction> functionList = systemService.getListByCriteriaQuery(
					cq, false);
			Collections.sort(functionList, new NumberComparator());
			MutiLangServiceI mutiLangUtil = (MutiLangServiceI) MutiLangUtil.getMutiLangInstance();
			for(TSFunction function:functionList) {
				JSONObject obj = new JSONObject();
				obj.put("id", function.getId());
				obj.put("name", mutiLangUtil.getLang(function.getFunctionName()));
				obj.put("pId", function.getTSFunction() == null?null:function.getTSFunction().getId());
				array.add(obj);
			}
		}
		data.put("data", array);
		
		List<String> loginActionlist = new ArrayList<String>();// 修改后待审核的权限
		TSRole role = this.systemService.get(TSRole.class, roleId);
		
		if (role != null) {
			//获得funcationid
            String functionId = role.getFunctionId();
            if (functionId != null && StringUtils.isNotEmpty(functionId)) {
                String[] roleFunctions = functionId.split(",");
                for (String functionid : roleFunctions) {
                    loginActionlist.add(functionid);
                }
            }
		}
		data.put("approval", loginActionlist);

		return data;
	}

	/**
	 * 跳转角色审核状态页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="editApprovalRole")
	public ModelAndView editApprovalRole(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		TSRole role = new TSRole();
		
		role = roleService.getById(id);
		
		ModelAndView model = new ModelAndView("/iesp/system/editApprovalRole");
		model.addObject("role", role);
		return model;
	}
	
	/**
	 * 更新审核状态操作
	 */
	@RequestMapping(params="updateApprovalRole")
	@ResponseBody
	public JSONObject updateApprovalRole(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String roleId = request.getParameter("roleId");
		String approvalOpinion = request.getParameter("approvalOpinion");
		TSRole role = this.systemService.get(TSRole.class, roleId);
		//审核状态
		String checkStatus = request.getParameter("checkStatus");
		Short status = 0;
		if(checkStatus != null && !checkStatus.isEmpty()) {
			status = Short.parseShort(checkStatus);
		}
		
		//log信息
		String logMessage = "";
		if ("2".equals(checkStatus)) {
			logMessage = "审核通过";
		} else if ("3".equals(checkStatus)) {
			logMessage = "审核失败";
		}
		
		try {
			role.setCheckStatus(status);
			role.setApprovalOpinion(approvalOpinion);
			roleService.update(role);
			result.put("success", true);
			result.put("checkStatus", checkStatus);
			systemService.addLog("审核角色权限成功，审核角色为：" + role.getRoleCode() + ",审核状态为：" + logMessage, Globals.MODULE_SYSTEM, Globals.Log_Leavel_INFO,"1");
			logger.debug("审核角色权限：" + role.getRoleCode() + " 成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "发生未知错误");
			systemService.addLog("审核角色权限：" + role.getRoleCode() + " 失败" + e.getMessage(), Globals.MODULE_SYSTEM, Globals.Log_Leavel_ERROR,"0");
			logger.error("更新审核状态：" + role.getRoleCode()  + " 失败," + e.getMessage());

		}
		return result;
	}  
	
	/**
	 * 审核角色权限，审核通过后权限生效
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "UpdateApprovalAuthority")
	@ResponseBody
	public AjaxJson UpdateApprovalAuthority(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String roleId = request.getParameter("roleId");
			//String rolefunction = request.getParameter("rolefunctions");
			TSRole role = this.systemService.get(TSRole.class, roleId);
			//获取待修改的权限id
			String rolefunction = role.getFunctionId();
			
			List<TSRoleFunction> roleFunctionList = systemService
					.findByProperty(TSRoleFunction.class, "TSRole.id",
							role.getId());
			Map<String, TSRoleFunction> map = new HashMap<String, TSRoleFunction>();
			for (TSRoleFunction functionOfRole : roleFunctionList) {
				map.put(functionOfRole.getTSFunction().getId(), functionOfRole);
			}
			if(rolefunction != null && !rolefunction.isEmpty()){
				String[] roleFunctions = rolefunction.split(",");
				Set<String> set = new HashSet<String>();
				for (String s : roleFunctions) {
					set.add(s);
				}
				updateCompare(set, role, map);
			}
			j.setSuccess(true);
			j.setMsg("权限更新成功");
			systemService.addLog("更新角色:" + role.getRoleName() + "的权限", Globals.MODULE_SYSTEM,
					Globals.Log_Leavel_INFO,"1");
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("权限更新失败");
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 权限比较
	 * 
	 * @param set
	 *            最新的权限列表
	 * @param role
	 *            当前角色
	 * @param map
	 *            旧的权限列表
	 */
	private void updateCompare(Set<String> set, TSRole role,
			Map<String, TSRoleFunction> map) {
		List<TSRoleFunction> entitys = new ArrayList<TSRoleFunction>();
		List<TSRoleFunction> deleteEntitys = new ArrayList<TSRoleFunction>();
		for (String s : set) {
			if (map.containsKey(s)) {
				map.remove(s);
			} else {
				TSRoleFunction rf = new TSRoleFunction();
				TSFunction f = this.systemService.get(TSFunction.class, s);
				if(f != null) {
					rf.setTSFunction(f);
					rf.setTSRole(role);
					entitys.add(rf);
				}
			}
		}
		Collection<TSRoleFunction> collection = map.values();
		Iterator<TSRoleFunction> it = collection.iterator();
		for (; it.hasNext();) {
			deleteEntitys.add(it.next());
		}
		systemService.batchSave(entitys);
		systemService.deleteAllEntitie(deleteEntitys);
	}
	
//	private List<ComboTree> comboTree(List<TSFunction> all, ComboTreeModel comboTreeModel, List<TSFunction> in, boolean recursive) {
//		List<ComboTree> trees = new ArrayList<ComboTree>();
//		for (TSFunction obj : all) {
//			trees.add(comboTree(obj, comboTreeModel, in, recursive));
//		}
//		all.clear();
//		return trees;
//
//	}
	
	/**
     * 构建ComboTree
     * @param obj
     * @param comboTreeModel ComboTreeModel comboTreeModel = new ComboTreeModel("id","functionName", "TSFunctions");
     * @param in
     * @param recursive 是否递归子节点
     * @return
     */
//	@SuppressWarnings("unchecked")
//	private ComboTree comboTree(TSFunction obj, ComboTreeModel comboTreeModel, List<TSFunction> in, boolean recursive) {
//		ComboTree tree = new ComboTree();
//		String id = oConvertUtils.getString(obj.getId());
//		tree.setId(id);
//		tree.setText(oConvertUtils.getString(obj.getFunctionName()));
//		
//		
//		
//		if (in == null) {
//		} else {
//			if (in.size() > 0) {
//				for (TSFunction inobj : in) {
//					String inId = oConvertUtils.getString(inobj.getId());
//                    if (inId.equals(id)) {
//						tree.setChecked(true);
//					}
//				}
//			}
//		}
//
//		List<TSFunction> curChildList = obj.getTSFunctions();
//
//		Collections.sort(curChildList, new Comparator<Object>(){
//			@Override
//	        public int compare(Object o1, Object o2) {
//	        	TSFunction tsFunction1=(TSFunction)o1;  
//	        	TSFunction tsFunction2=(TSFunction)o2;  
//	        	int flag=tsFunction1.getFunctionOrder().compareTo(tsFunction2.getFunctionOrder());
//	        	  if(flag==0){
//	        	   return tsFunction1.getFunctionName().compareTo(tsFunction2.getFunctionName());
//	        	  }else{
//	        	   return flag;
//	        	  }  
//	        }             
//	    });
//
//		if (curChildList != null && curChildList.size() > 0) {
//			tree.setState("closed");
//			//tree.setChecked(false);
//
//            if (recursive) { // 递归查询子节点
//                List<ComboTree> children = new ArrayList<ComboTree>();
//                for (TSFunction childObj : curChildList) {
//                    ComboTree t = comboTree(childObj, comboTreeModel, in, recursive);
//                    children.add(t);
//                }
//                tree.setChildren(children);
//            }
//        }
//
//		if(obj.getFunctionType() == 1){
//			if(curChildList != null && curChildList.size() > 0){
//				tree.setIconCls("icon-user-set-o");
//			}else{
//				tree.setIconCls("icon-user-set");
//			}
//		}
//
//		if(curChildList!=null){
//			curChildList.clear();
//		}
//
//		return tree;
//	}
}
