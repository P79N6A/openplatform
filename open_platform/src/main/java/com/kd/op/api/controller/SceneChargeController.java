package com.kd.op.api.controller;

import com.kd.op.api.entity.ApiAppRelaEntity;
import com.kd.op.api.entity.ApiAppSceneRelaEntity;
import com.kd.op.api.entity.ApiChargeAccountEntity;
import com.kd.op.api.entity.ApiSceneEntity;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.StringTransUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sceneCharge")
public class SceneChargeController {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private SystemService systemService;

    @RequestMapping(params="chargingScene")
    public ModelAndView chargingScene(){
        ModelAndView model = new ModelAndView("com/kd/op/charging/chargingScene");
        return model;
    }
    @RequestMapping(params="chargeSceneList")
    public ModelAndView chargingScene(String typename){
        ModelAndView model = new ModelAndView("com/kd/op/charging/chargeSceneList");
        model.addObject("typename",typename);
        return model;
    }

    /**
     * easyui AJAX请求场景余量数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String typename = request.getParameter("typename");
//        typename=StringTransUtil.stringReplace(typename);
        //查询总记录数
        List<Map<String, Object>> totalList;
        //查询返回页面的数据
        List<Map<String, Object>> list;
        Map<String, Object> authorityUsers = ResourceParams.getAuthorityUsers();
        String isFilter = (String) authorityUsers.get(CustomConstant.IS_FILTER);
        String userName = (String) authorityUsers.get(CustomConstant.USERNAME);
        String sql = "SELECT aar.scene_id sceneId, aar.app_name appName, " +
                "aca.rest_state restState " +
                "FROM api_charge_account aca,api_app_rela aar ";
        if (StringUtil.isNotEmpty(isFilter) && Boolean.parseBoolean(isFilter)) {
            sql+=" ,api_app aa";
        }
        sql+= " WHERE aca.typename =? AND aca.api_app_rela_id=aar.id " +
                "AND aar.scene_id IS NOT NULL ";
        if (StringUtil.isNotEmpty(isFilter) && Boolean.parseBoolean(isFilter)) {
            sql += " AND aa.id = aar.app_id AND aa.create_by = ?";
            totalList = systemService.findForJdbc(sql, typename, userName);
        } else {
            totalList = systemService.findForJdbc(sql, typename);
        }

        int page =dataGrid.getPage();
        int rows=dataGrid.getRows();
        if (StringUtil.isNotEmpty(isFilter) && Boolean.parseBoolean(isFilter)) {
            list = systemService.findForJdbcParam(sql, page, rows, typename,userName);
        } else {
            list = systemService.findForJdbcParam(sql,page,rows,typename);
        }
        List<Map> results = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map resultMap = new HashMap();
            String sceneId = (String) map.get("sceneId");
            String sceneName = systemService.get(ApiSceneEntity.class, sceneId).getSceneName();
            resultMap.put("appName",map.get("appName"));
            resultMap.put("sceneName",sceneName);
            resultMap.put("restState",map.get("restState"));
            results.add(resultMap);
        }
        dataGrid.setResults(results);
        dataGrid.setTotal(totalList.size());
        TagUtil.datagrid(response, dataGrid);
    }

}
