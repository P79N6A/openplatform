package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiAnnexServiceI;
import com.kd.op.api.service.ApiAppServiceI;
import com.kd.op.util.Status;
import com.kd.op.util.StringTransUtil;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @Title: ApiAnnexController
 * @Description: 附件表
 * @author zjy
 * @date 2018-10-05 10:10:48
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/apiAnnexController")
public class ApiAnnexController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ApiAnnexController.class);

    @Autowired
    private ApiAnnexServiceI apiAnnexService;
    @Autowired
    private SystemService systemService;



    /**
     * 附件列表跳转
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list() {

        return new ModelAndView("com/kd/op/annex/annexList");
    }

    @RequestMapping(params = "auditList")
    public ModelAndView auditList(String listType) {

        return new ModelAndView("com/kd/op/annex/annexAuditList");
    }


    /**
     * 数据加载
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    @ResponseBody
    public void datagrid(ApiAnnexEntity apiAnnex, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiAnnexEntity.class, dataGrid);

        //修改为模糊查询
        if (StringUtil.isNotEmpty(apiAnnex.getAnnexName())) {
            String s = StringTransUtil.stringReplace(apiAnnex.getAnnexName());
            apiAnnex.setAnnexName("*" + s + "*");
        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiAnnex, request.getParameterMap());
        cq.add();
        this.apiAnnexService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd() {

        return new ModelAndView("com/kd/op/annex/annex-add");
    }

    /*@RequestMapping(params = "doUpload")
    @ResponseBody
    public AjaxJson doUpload(@RequestParam("file") MultipartFile[] files,ApiAnnexEntity apiAnnex) {
        AjaxJson j = new AjaxJson();
        String message = "上传成功";
        try {
            apiAnnexService.doUpload(apiAnnex,files);
        } catch (Exception e) {
            message = "上传失败";
           logger.error("error:",e);
            systemService.addLog("组件上传失败",Globals.MODULE_ANNEX,Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }*/

   /* @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiAnnexEntity apiAnnex) {
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        try {
            apiAnnexService.delAnnex(apiAnnex);
            systemService.addLog("组件["+apiAnnex.getId()+"]删除",Globals.MODULE_ANNEX,Globals.Log_Leavel_INFO,Globals.SUCCESS);
        } catch (Exception e) {
            message = "删除失败";
            logger.error("error:",e);
            systemService.addLog("组件["+apiAnnex.getId()+"]删除",Globals.MODULE_ANNEX,Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }*/

    @RequestMapping(params = "submitAudit")
    @ResponseBody
    public AjaxJson submitAudit(ApiAnnexEntity apiAnnex) {
        AjaxJson j = new AjaxJson();
        String message = "提交审核成功";
        try {
            apiAnnexService.submitAudit(apiAnnex);
            systemService.addLog("组件["+apiAnnex.getId()+"]提交审核",Globals.MODULE_ANNEX,Globals.Log_Leavel_INFO,Globals.SUCCESS);
        } catch (Exception e) {
            message = "提交审核失败";
            logger.error("error:",e);
            systemService.addLog("组件["+apiAnnex.getId()+"]提交审核",Globals.MODULE_ANNEX,Globals.Log_Leavel_ERROR,Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(ApiAnnexEntity apiAnnex,HttpServletRequest request) {
        apiAnnex = apiAnnexService.get(ApiAnnexEntity.class,apiAnnex.getId());
        request.setAttribute("apiAnnex",apiAnnex);
        return new ModelAndView("com/kd/op/annex/annex-audit");
    }

    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(ApiAnnexEntity apiAnnex) {
        AjaxJson j = new AjaxJson();
        String message = "审核成功";
        try {
            apiAnnexService.doAudit(apiAnnex);
            systemService.addLog("组件["+apiAnnex.getId()+"]",Globals.MODULE_ANNEX,Globals.Log_Leavel_INFO,Globals.SUCCESS);
        } catch (Exception e) {
            message = "审核失败";
            logger.error("error:",e);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goLook")
    public ModelAndView goLook(ApiAnnexEntity apiAnnex,HttpServletRequest request) {
        apiAnnex = apiAnnexService.get(ApiAnnexEntity.class,apiAnnex.getId());
        request.setAttribute("apiAnnex",apiAnnex);
        return new ModelAndView("com/kd/op/annex/annex-look");
    }
}