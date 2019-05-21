package com.kd.op.api.controller;

import com.kd.op.api.entity.*;
import com.kd.op.api.service.*;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.StringTransUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 接口应用表
 * @author onlineGenerator
 * @date 2018-10-05 10:10:48
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/apiCharge")
public class ApiChargeController{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ApiChargeController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(params="chargingAccount")
    public ModelAndView chargingAccount(){
        ModelAndView model = new ModelAndView("com/kd/op/charging/chargingAccount");
        return model;
    }

    @RequestMapping(params="chargeAccountList")
    public ModelAndView chargeAccountList(String typename){
        ModelAndView model = new ModelAndView("com/kd/op/charging/chargeAccountList");
        model.addObject("typename",typename);
        return model;
    }

    /**
     * easyui AJAX请求服务余量数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "chargeAccountDatagrid")
    public void chargeAccountDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String typename = request.getParameter("typename");
//        typename = StringTransUtil.stringReplace(typename);
        //查询总记录数
        List<Map<String, Object>> totalList;
        //查询返回页面的数据
        List<Map<String, Object>> list;
        Map<String, Object> authorityUsers = ResourceParams.getAuthorityUsers();
        String isFilter = (String) authorityUsers.get(CustomConstant.IS_FILTER);
        String userName = (String) authorityUsers.get(CustomConstant.USERNAME);
        String sql = "SELECT aar.api_name apiName, aar.app_name appName, " +
                "aca.rest_state restState " +
                "FROM api_charge_account aca,api_app_rela aar";
        if (StringUtil.isNotEmpty(isFilter) && Boolean.parseBoolean(isFilter)) {
            sql+=" ,api_app aa ";
        }
        sql+= " WHERE aca.typename =? AND aca.api_app_rela_id=aar.id " +
                "AND aar.api_id IS NOT NULL ";
        if (StringUtil.isNotEmpty(isFilter) && Boolean.parseBoolean(isFilter)) {
            sql += " AND aa.id = aar.app_id AND aa.create_by = ? ";
           totalList = systemService.findForJdbc(sql, typename,userName);
        } else {
            totalList = systemService.findForJdbc(sql, typename);
        }
        int page =dataGrid.getPage();
        int rows=dataGrid.getRows();
        if (StringUtil.isNotEmpty(isFilter) && Boolean.parseBoolean(isFilter)) {
            list = systemService.findForJdbcParam(sql,page,rows,typename,userName);
        } else {
            list = systemService.findForJdbcParam(sql,page,rows,typename);
        }
        List<Map> results = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map resultMap = new HashMap();
            resultMap.putAll(map);
            results.add(resultMap);
        }
        dataGrid.setResults(results);
        dataGrid.setTotal(totalList.size());
        TagUtil.datagrid(response, dataGrid);
    }
}