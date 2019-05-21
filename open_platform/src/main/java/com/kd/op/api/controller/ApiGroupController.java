package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.kd.op.api.entity.ApiGroupEntity;
import com.kd.op.api.entity.ApiInfoEntity;
import com.kd.op.api.service.ApiGroupServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.jeecgframework.core.util.ExceptionUtil;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 能力中心表
 * @date 2018-10-20 10:11:17
 */
@Controller
@RequestMapping("/apiGroupController")
public class ApiGroupController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApiGroupController.class);

    @Autowired
    private ApiGroupServiceI apiGroupService;
    @Autowired
    private SystemService systemService;


    /**
     * 能力中心列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/kd/op/api/apiGroupList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(ApiGroupEntity apiGroup, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiGroupEntity.class, dataGrid);
        if (StringUtil.isEmpty(apiGroup.getId())) {
            cq.isNull("parentId");
        } else {
            cq.eq("parentId", apiGroup.getId());
            apiGroup.setId(null);
        }
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, apiGroup, request.getParameterMap());
        cq.add();
        this.apiGroupService.getDataGridReturn(cq, true);
        TagUtil.treegrid(response, dataGrid);
    }

    /**
     * 删除能力中心
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(ApiGroupEntity apiGroup, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        apiGroup = systemService.getEntity(ApiGroupEntity.class, apiGroup.getId());
        message = "能力中心删除成功";
        try {
            //判断是否有子节点，如果有就禁止删除
            List<ApiGroupEntity> children = systemService.findByProperty(ApiGroupEntity.class, "parentId", apiGroup.getId());
            if (children != null && children.size() > 0) {
                message = "该能力中心包含子节点，删除失败";
            } else if (children == null || children.size() == 0) {
                //该能力中心下是否包含服务
                List<ApiInfoEntity> apiInfoEntities = systemService.findByProperty(ApiInfoEntity.class,"groupId",apiGroup.getId());
                if(apiInfoEntities!=null && apiInfoEntities.size()>0){
                    message = "能力中心下已经存在服务，请删除服务后重试";
                }else {
                    apiGroupService.delete(apiGroup);
                    systemService.addLog("删除能力中心[" + apiGroup.getId() + "]", Globals.MODULE_API_GROUP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
                }
            }
        } catch (Exception e) {
            logger.error("error:", e);
            message = "能力中心删除失败";
            systemService.addLog("删除能力中心[" + apiGroup.getId() + "]", Globals.MODULE_API_GROUP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }



    /**
     * 添加能力中心
     *
     * @param
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(ApiGroupEntity apiGroup, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "能力中心添加成功";
        try {
            if (StringUtil.isEmpty(apiGroup.getParentId())) {
                apiGroup.setParentId(null);
            }
            apiGroupService.save(apiGroup);
            systemService.addLog("创建能力中心[" + apiGroup.getId()+"]", Globals.MODULE_API_GROUP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "能力中心添加失败";
            systemService.addLog("创建能力中心[" + apiGroup.getId()+"]", Globals.MODULE_API_GROUP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新能力中心
     *
     * @param
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(ApiGroupEntity apiGroup, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "能力中心更新成功";
        ApiGroupEntity t = apiGroupService.get(ApiGroupEntity.class, apiGroup.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(apiGroup, t);
            if (StringUtil.isEmpty(t.getParentId())) {
                t.setParentId(null);
            }
            apiGroupService.saveOrUpdate(t);
            systemService.addLog("更新能力中心[" + apiGroup.getGroupName()+"]", Globals.MODULE_API_GROUP, Globals.Log_Leavel_INFO, Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:", e);
            message = "能力中心更新失败";
            systemService.addLog("更新能力中心[" + apiGroup.getGroupName()+"]", Globals.MODULE_API_GROUP, Globals.Log_Leavel_ERROR, Globals.FAILD);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 能力中心新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(ApiGroupEntity apiGroup, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiGroup.getId())) {
            apiGroup = apiGroupService.getEntity(ApiGroupEntity.class, apiGroup.getId());
            req.setAttribute("apiGroupPage", apiGroup);
        }
        return new ModelAndView("com/kd/op/api/apiGroup-add");
    }

    /**
     * 能力中心编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(ApiGroupEntity apiGroup, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(apiGroup.getId())) {
            apiGroup = apiGroupService.getEntity(ApiGroupEntity.class, apiGroup.getId());
            req.setAttribute("apiGroupPage", apiGroup);
            if (apiGroup.getParentId() != null) {
                ApiGroupEntity parent = systemService.getEntity(ApiGroupEntity.class, apiGroup.getParentId());
                if (parent != null) {
                    req.setAttribute("parentName", parent.getGroupName());
                }
            }
        }
        String type = req.getParameter("type");
        if (type != null && type.equals("look")) {
            req.setAttribute("look", "1");
        }
        return new ModelAndView("com/kd/op/api/apiGroup-update");
    }

    /**
     * 加载全部能力中心数据组成树形数据返回
     * @param apiGroup
     * @param request
     * @return
     */
    @RequestMapping(params = "loadAll")
    @ResponseBody
    public JSONArray loadAllGroup(ApiGroupEntity apiGroup, HttpServletRequest request) {
        JSONArray result = new JSONArray();
        try {
            result = apiGroupService.loadAllGroup(apiGroup);
        } catch (Exception e) {
            logger.error("执行失败：" + e.getMessage());
        }
        return result;
    }

}
