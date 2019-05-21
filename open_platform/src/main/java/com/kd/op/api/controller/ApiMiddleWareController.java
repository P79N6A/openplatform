package com.kd.op.api.controller;

import com.kd.op.api.service.ApiMiddleWareService;
import com.kd.op.util.StringTransUtil;
import io.swagger.annotations.Api;
//import javafx.scene.control.AccordionBuilder;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kd.op.api.entity.*;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="ApiMiddleWare",description="中间件管理",tags="apiMiddleWareController")
@Controller
@RequestMapping("/apiMiddleWare")
public class ApiMiddleWareController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ApiMiddleWareController.class);

    @Autowired
    private ApiMiddleWareService apiMiddleWareService;

    @Autowired
    private SystemService systemService;

    /**
     * 接口应用表列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/resource/middle");
    }

    @RequestMapping(params = "configList")
    public ModelAndView configList(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/resource/middleConfigList");
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
    public void datagrid(ApiMiddleWareEntity middleWareApp, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiMiddleWareEntity.class, dataGrid);

        //修改为模糊查询
        if(StringUtil.isNotEmpty(middleWareApp.getMiddleName())) {
            String s = StringTransUtil.stringReplace(middleWareApp.getMiddleName());
            middleWareApp.setMiddleName("*"+s+"*");
        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, middleWareApp, request.getParameterMap());
        /*try{
            //自定义追加查询条件
//            if(systemService.isDeveloper()){
//                TSUser user = ResourceUtil.getSessionUser();
//                cq.eq("createBy",user.getUserName());
//            }
        }catch (Exception e) {
            logger.error("error:",e);
        }*/
        cq.add();
        this.apiMiddleWareService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除接口应用表
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiMiddleWareEntity middleWareApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        middleWareApp = systemService.getEntity(ApiMiddleWareEntity.class, middleWareApp.getId());
        message = "接口应用表删除成功";
        try{
            apiMiddleWareService.delete(middleWareApp);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO,"1");
        }catch(Exception e){
            logger.error("error:",e);
            message = "接口应用表删除失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除接口应用表
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口应用表删除成功";
        try{
            for(String id:ids.split(",")){
                ApiMiddleWareEntity middleWareApp= systemService.getEntity(ApiMiddleWareEntity.class,
                        id
                );
                apiMiddleWareService.delete(middleWareApp);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO,"1");
            }
        }catch(Exception e){
            logger.error("error:",e);
            message = "接口应用表删除失败";
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加接口应用表
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(ApiMiddleWareEntity middleWareApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口应用表添加成功";
        try{
//            apiApp.setAuditStatus(0);
            apiMiddleWareService.save(middleWareApp);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO,"1");
        }catch(Exception e){
            logger.error("error:",e);
            message = "接口应用表添加失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新接口应用表
     *
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(ApiMiddleWareEntity middleWareApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口应用表更新成功";
        ApiMiddleWareEntity t = apiMiddleWareService.get(ApiMiddleWareEntity.class, middleWareApp.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(middleWareApp, t);
//            t.setAuditStatus(0);
            apiMiddleWareService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO,"1");
        } catch (Exception e) {
            logger.error("error:",e);
            message = "接口应用表更新失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 查看接口应用表
     *
     * @return
     */
    @RequestMapping(params = "doLook")
    @ResponseBody
    public AjaxJson doLook(ApiMiddleWareEntity middleWareApp, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "接口应用表更新成功";
        ApiMiddleWareEntity t = apiMiddleWareService.get(ApiMiddleWareEntity.class, middleWareApp.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(middleWareApp, t);
//            t.setAuditStatus(0);
            apiMiddleWareService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO,"1");
        } catch (Exception e) {
            logger.error("error:",e);
            message = "接口应用表更新失败";
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 接口应用表新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiMiddleWareEntity middleWareApp, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(middleWareApp.getId())) {
            middleWareApp = apiMiddleWareService.getEntity(ApiMiddleWareEntity.class, middleWareApp.getId());
            req.setAttribute("apiMiddleWare", middleWareApp);
        }
        return new ModelAndView("com/kd/op/app/apiApp-add");
    }
    /**
     * 接口应用表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiMiddleWareEntity middleWareApp, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(middleWareApp.getId())) {
            middleWareApp = apiMiddleWareService.getEntity(ApiMiddleWareEntity.class, middleWareApp.getId());
            req.setAttribute("apiMiddleWare", middleWareApp);
        }
        return new ModelAndView("com/kd/op/app/apiApp-update");
    }

    /**
     * 接口应用表查看页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goLook")
    public ModelAndView goLook(ApiMiddleWareEntity middleWareApp, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(middleWareApp.getId())) {
            middleWareApp = apiMiddleWareService.getEntity(ApiMiddleWareService.class, middleWareApp.getId());
            req.setAttribute("apiMiddleWare", middleWareApp);
        }
        return new ModelAndView("com/kd/op/app/apiApp-Look");
    }



    @RequestMapping(params = "goAppOrderList")
    public ModelAndView goAppOrderList(ApiMiddleWareEntity middleWareApp, HttpServletRequest req) {
        ModelAndView model = new ModelAndView("com/kd/op/app/appList");
        return model;
    }

}
