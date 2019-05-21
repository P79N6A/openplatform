package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiAppConnect;
import com.kd.op.api.entity.ApiAppEntity;
import com.kd.op.api.entity.ApiSysEnvironment;
import com.kd.op.api.service.ApiAppConnectService;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.api.service.ApiSysEnvironService;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.Status;
import com.kd.op.util.StringTransUtil;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.security.pkcs11.wrapper.Constants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 服务环境配置
 */
@Controller
@RequestMapping("/apiAppConnectController")
public class ApiAppConnectController {

    @Resource
    private ApiAppConnectService apiAppConnectService;
    @Resource
    private SystemService systemService;
    @Resource
    private ApiAppServiceI apiAppService;


    @RequestMapping(params = "list")
    public ModelAndView list() {
        return new ModelAndView("com/kd/op/app/appConnectList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    @ResponseBody
    public void datagrid(ApiAppConnect connect, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        connect.setDeleteFlag(0);
        //修改为模糊查询
        String appName = request.getParameter("appName_search");
        if (StringUtil.isNotEmpty(appName)) {
            String s = StringTransUtil.stringReplace(appName);
            if(s!=null){
                connect.setAppName("*" + s + "*");
            }
        }
        CriteriaQuery cq = new CriteriaQuery(ApiAppConnect.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, connect, request.getParameterMap());
        String userType = request.getParameter("userType");
        if (userType != null && userType.equals("isv")) {
            TSUser user = ResourceUtil.getSessionUser();
            cq.eq("createBy", user.getUserName());
        }
        //
        //根据审核员过滤
        String username = ResourceUtil.getSessionUserName().getUserName();
        TSBaseUser tsBaseUser = systemService.findUniqueByProperty(TSBaseUser.class, "userName", username);
        if(tsBaseUser!=null){
            //获取角色
            String userKey = tsBaseUser.getUserKey();
            if(userKey.equals("系统管理员")){

            }
            if(userKey.equals("开放平台审核员")){
                cq.eq("auditBy",username);
            }
        }
        cq.add();
        this.apiAppConnectService.getDataGridReturn(cq, true);
        List<ApiSysEnvironment> environments = systemService.loadAll(ApiSysEnvironment.class);
        ApiSysEnvironment testEnviron = null;
        ApiSysEnvironment normalEnviron = null;
        for(ApiSysEnvironment enviro:environments){
            if(enviro.getType() == 0){
                testEnviron = enviro;
            }else if(enviro.getType() == 1){
                normalEnviron = enviro;
            }
        }
        if(dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
                for(int i = 0;i < dataGrid.getResults().size();i++){
                    ApiAppConnect con = (ApiAppConnect) dataGrid.getResults().get(i);
                    if(con.getAuditStatus() == 2){
                        if(con.getType() == 0 && testEnviron != null){
                            con.setConnectAddr(testEnviron.getIp());
                        }else if(con.getType() == 1){
                            if(normalEnviron != null){
                                con.setConnectAddr(normalEnviron.getIp());
                            }
                        }
                    }
                }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(String id) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appConnectAdd");
        //获取当前用户创建的应用
        TSUser user = ResourceUtil.getSessionUser();
        String hql = " from ApiAppEntity where createBy= ? and auditStatus != ? and publishStatus != ?";
        List<ApiAppEntity> apps =  systemService.findHql(hql,user.getUserName(),2,-1);
        model.addObject("apps",apps);
        return model;
    }

    @RequestMapping(params = "doSave")
    @ResponseBody
    public JSONObject doSave(ApiAppConnect connect){
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","应用连接申请创建成功");
        try {
            //首先判断应用的连接类型是否已经存在
            List<ApiAppConnect> connects = apiAppConnectService.getByAppAndType(connect.getAppId(),connect.getType());
            if(connects != null && connects.size() > 0){
                result.put("success",false);
                result.put("msg","该应用的这种连接申请已经存在，请确认后重试！");
                return result;
            }
            ApiAppEntity app = systemService.getEntity(ApiAppEntity.class,connect.getAppId());
            connect.setAppName(app == null?"":app.getAppName());
            connect.setDeleteFlag(0);
            connect.setAuditStatus(0);
            apiAppConnectService.save(connect);
            systemService.addLog("创建应用接入申请[" + connect.getId() + "]" , Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","应用连接申请创建失败");
            systemService.addLog("创建应用接入申请[" + connect.getId() + "]" , Globals.MODULE_APP, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        return result;
    }

    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(String id) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appConnectEdit");
        ApiAppConnect connect = apiAppConnectService.getEntity(ApiAppConnect.class,id);
        if(connect != null){
            model.addObject("connect",connect);
        }
        //获取当前用户创建的应用
        TSUser user = ResourceUtil.getSessionUser();
        List<ApiAppEntity> apps = systemService.findByProperty(ApiAppEntity.class,"createBy",user.getUserName());
        model.addObject("apps",apps);
        return model;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public JSONObject doUpdate(ApiAppConnect connect){
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","应用连接申请修改成功");
        try {
            ApiAppConnect old = apiAppConnectService.getEntity(ApiAppConnect.class,connect.getId());
            //判断该连接申请是否允许编辑
            if(old.getAuditStatus() == 1 || old.getAuditStatus() == 2){
                result.put("success",false);
                result.put("msg","当前应用连接申请不允许编辑，如需修改请删除后重新创建");
                return result;
            }
            old.setAuditStatus(0);
            old.setIp(connect.getIp());
            apiAppConnectService.updateEntitie(old);
            systemService.addLog("更新应用接入申请[" + connect.getId() + "]", Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","应用连接申请修改失败");
            systemService.addLog("更新应用接入申请[" + connect.getId() + "]", Globals.MODULE_APP, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        return result;
    }


    @RequestMapping(params = "doDelete")
    @ResponseBody
    public JSONObject doDelete(String id){
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","应用连接申请删除成功");
        try {
            ApiAppConnect connect = apiAppConnectService.getEntity(ApiAppConnect.class,id);
            connect.setDeleteFlag(1);
            apiAppConnectService.updateEntitie(connect);
            systemService.addLog("删除应用接入申请[" + id + "]" , Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","应用连接申请修删除失败");
            systemService.addLog("删除应用接入申请[" + id + "]" , Globals.MODULE_APP, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        return result;
    }

    @RequestMapping(params = "auditList")
    public ModelAndView auditList() {
        ModelAndView model = new ModelAndView("com/kd/op/app/appConnectAuditList");
        return model;
    }

    /**
     * 连接申请提交审核
     * @param id
     * @return
     */
    @RequestMapping(params = "submitAudit")
    @ResponseBody
    public JSONObject submitAudit(String id){
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","应用连接提交审核成功");
        try {
            ApiAppConnect connect = apiAppConnectService.getEntity(ApiAppConnect.class,id);
            connect.setAuditStatus(CustomConstant.AUDIT_WAIT);
            apiAppConnectService.updateEntitie(connect);
            systemService.addLog("应用连接申请[" + id + "]提交审核", Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","应用连接提交修审核失败");
            systemService.addLog("应用连接申请[" + id + "]提交审核", Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }
        return result;
    }

    /**
     * 订单提交给指定审核员
     *
     * @param apiAppConnect
     * @param request
     * @return
     */
    @RequestMapping(params = "orderSubmitToCheck")
    @ResponseBody
    public AjaxJson orderSubmitToCheck(ApiAppConnect apiAppConnect, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "应用连接提交审核成功";
        try {
            String apiData = request.getParameter("apiData");
            JSONArray data = JSONArray.parseArray(apiData);
            JSONObject jb = (JSONObject)data.get(0);
            ApiAppConnect apiAppConnectEntity = apiAppConnectService.get(ApiAppConnect.class, apiAppConnect.getId());
            apiAppConnectEntity.setAuditStatus(CustomConstant.AUDIT_WAIT);
            if (jb.get("auditBy") != null) {
                apiAppConnectEntity.setAuditBy(jb.get("auditBy").toString());
            }
            apiAppConnectService.updateEntitie(apiAppConnectEntity);
            systemService.addLog("应用连接申请[" + apiAppConnect.getId() + "]提交审核", Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        } catch (Exception e) {
            message = "应用连接提交审核失败";
            systemService.addLog("应用连接申请[" + apiAppConnect.getId() + "]提交审核", Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }
        j.setMsg(message);
        return j;
    }



    @RequestMapping(params = "goAudit")
    public ModelAndView aoAudit(String id) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appConnectAudit");
        ApiAppConnect connect = apiAppConnectService.getEntity(ApiAppConnect.class,id);
        if(connect != null){
            model.addObject("connect",connect);
        }
        return model;
    }

    @RequestMapping(params = "doAudit")
    @ResponseBody
    public JSONObject doAudit(String id,Integer auditStatus,String auditMsg){
        JSONObject result = new JSONObject();
        result.put("success",true);
        result.put("msg","应用连接申请审核成功");
        ApiAppConnect connect =null;
        try {
            connect = apiAppConnectService.getEntity(ApiAppConnect.class,id);
            connect.setAuditStatus(auditStatus);
            connect.setAuditMsg(auditMsg);
            ApiAppEntity apiApp= apiAppService.getApiAppEntityById(connect.getAppId());
            apiApp.setAuditStatus(auditStatus);
            apiAppService.saveOrUpdate(apiApp);
            apiAppConnectService.updateEntitie(connect);
            systemService.addLog("应用接入申请[" + id + "]执行审核", Globals.MODULE_APP, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch (Exception e){
            result.put("success",false);
            result.put("msg","应用连接申请修审核失败");
            systemService.addLog("应用接入申请[" + id + "]执行审核：", Globals.MODULE_APP, Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        return result;
    }
}
