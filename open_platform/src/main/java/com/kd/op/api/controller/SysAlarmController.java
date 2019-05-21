package com.kd.op.api.controller;

import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.api.service.ApiInfoServiceI;
import com.kd.op.api.service.SysAlarmServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sysAlarmController")
public class SysAlarmController extends BaseController {

    private static final Logger logger = Logger.getLogger(SysAlarmController.class);

    @Autowired
    SysAlarmServiceI sysAlarmService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiInfoServiceI apiInfoService;
    @Autowired
    private ApiAppServiceI apiAppService;

    @RequestMapping(params = "remainderAlarmList")
    public ModelAndView dataResourceList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/alarm/remainderAlarmList");
    }

    @RequestMapping(params = "fuseAlarmList")
    public ModelAndView fuseResourceList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/alarm/fuseAlarmList");
    }

    @RequestMapping(params = "remainderdatagrid")
    public void remainderDatagrid(SysAlarmEntity alarmInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SysAlarmEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, alarmInfo);
        try{
            //自定义追加查询条件
            cq.eq("typeName", "2");
            String type = request.getParameter("type");
            if(type != null && type.equals("isv")) {
                cq.in("appId", apiAppService.getCurrentUserAppIds());
            }else {
                cq.in("apiId", apiInfoService.getCurrentUserApiids());
            }

        }catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        sysAlarmService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "fusedatagrid")
    public void fuseDatagrid(SysAlarmEntity alarmInfo, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SysAlarmEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, alarmInfo);
        try{
            cq.eq("typeName", "1");
            String type = request.getParameter("type");
            if(type != null && type.equals("isv")) {
                cq.in("appId", apiAppService.getCurrentUserAppIds());
            }else {
                cq.in("apiId", apiInfoService.getCurrentUserApiids());
            }

        }catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        sysAlarmService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "alarmdelete")
    @ResponseBody
    public AjaxJson doFlowDel(SysAlarmEntity alarmInfo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        alarmInfo = systemService.getEntity(SysAlarmEntity.class, alarmInfo.getId());
        String message = "资源信息删除成功";
        try{
            systemService.delete(alarmInfo);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO,"1");
        }catch(Exception e){
            logger.error("error:",e);
            message = "资源信息删除失败";
        }
        j.setMsg(message);
        return j;
    }

}
