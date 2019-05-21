package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiAppEntity;
import com.kd.op.api.entity.ApiChargeMode;
import com.kd.op.api.entity.ApiChargeModeRela;
import com.kd.op.api.entity.ApiSceneChargeEntity;
import com.kd.op.api.service.ApiChargeModeServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/charging")
public class ChargingController {
    private static final Logger logger = Logger.getLogger(ChargingController.class);
    @Autowired
    private ApiChargeModeServiceI chargeModeService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(params="chargingModel")
    public ModelAndView chargingModel(){
        ModelAndView model = new ModelAndView("com/kd/op/charging/chargingModel");
        return model;
    }

    @RequestMapping(params="chargeModeList")
    public ModelAndView prepayment(String type){
        ModelAndView model = new ModelAndView("com/kd/op/charging/chargeModeList");
        model.addObject("type",type);
        return model;
    }



    @RequestMapping(params = "datagrid")
    public void datagrid(ApiChargeMode charge,String apiId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiChargeMode.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, charge, request.getParameterMap());
        try{
            //自定义追加查询条件
        	//只回显当前服务关联的计费模型
        	if(StringUtil.isNotEmpty(apiId)) {
        		List<ApiChargeModeRela> relas = systemService.findByProperty(ApiChargeModeRela.class,"apiId",apiId);
        		List<String> modeIds = new ArrayList<>();
        		modeIds.add("xxxxxx");
        		relas.forEach(p->{
        			modeIds.add(p.getChargeModeId());
        		});
        		cq.in("id", modeIds.toArray());
        	}
            
        	cq.eq("type",charge.getType());
        }catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.chargeModeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "datagridScene")//传回场景计费模型
    public void datagridScene(ApiChargeMode charge,String sceneId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(ApiChargeMode.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, charge, request.getParameterMap());
        try{
            //自定义追加查询条件
            //只回显当前服务关联的计费模型
            if(StringUtil.isNotEmpty(sceneId)) {
                List<ApiSceneChargeEntity> relas = systemService.findByProperty(ApiSceneChargeEntity.class,"sceneId",sceneId);
                List<String> modeIds = new ArrayList<>();
                modeIds.add("xxxxxx");
                relas.forEach(p->{
                    modeIds.add(p.getChargeId());
                });
                cq.in("id", modeIds.toArray());
            }

            cq.eq("type",charge.getType());
        }catch (Exception e) {
            logger.error("error:",e);
        }
        cq.add();
        this.chargeModeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params="goAdd")
    public ModelAndView goAddPostpaid(String type){
        ModelAndView model = new ModelAndView("com/kd/op/charging/addChargeMode");
        model.addObject("type",type);
        return model;
    }

    @RequestMapping(params = "create")
    @ResponseBody
    public AjaxJson doAdd(ApiChargeMode chargeMode, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "计费方式添加成功";
        j.setSuccess(true);
        try{
            chargeModeService.save(chargeMode);
//            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO,"1");
            systemService.addLog("计费方式["+chargeMode.getId()+"]添加成功", Globals.MODULE_CHARGE, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch(Exception e){
            logger.error("error:",e);
            systemService.addLog("计费方式["+chargeMode.getId()+"]添加失败", Globals.MODULE_CHARGE, Globals.Log_Leavel_ERROR,Globals.FAILD);
            message = "计费方式添加失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params="goEdit")
    public ModelAndView goEditPrepayment(String type,String id){
        ModelAndView model = new ModelAndView("com/kd/op/charging/editChargeMode");
        model.addObject("type",type);
        if(id != null){
            ApiChargeMode chargeMode = chargeModeService.getEntity(ApiChargeMode.class,id);
            model.addObject("chargeMode",chargeMode);
        }
        return model;
    }

    @RequestMapping(params = "update")
    @ResponseBody
    public AjaxJson doUpdate(ApiChargeMode chargeMode, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "计费方式更新成功";
        j.setSuccess(true);
        ApiChargeMode t = chargeModeService.get(ApiChargeMode.class, chargeMode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(chargeMode, t);
            chargeModeService.saveOrUpdate(t);
//            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO,"1");
            systemService.addLog("计费方式["+chargeMode.getId()+"]更新成功", Globals.MODULE_CHARGE, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        } catch (Exception e) {
            logger.error("error:",e);
            systemService.addLog("计费方式["+chargeMode.getId()+"]更新失败", Globals.MODULE_CHARGE, Globals.Log_Leavel_ERROR,Globals.FAILD);
            message = "计费方式更新失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "delete")
    @ResponseBody
    public AjaxJson doDel(ApiChargeMode chargeMode, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        chargeMode = systemService.getEntity(ApiChargeMode.class, chargeMode.getId());
        message = "计费方式删除成功";
        j.setSuccess(true);
        try{
            systemService.delete(chargeMode);
//            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO,"1");
            systemService.addLog("计费方式["+chargeMode.getId()+"]删除成功", Globals.MODULE_CHARGE, Globals.Log_Leavel_INFO,Globals.SUCCESS);
        }catch(Exception e){
            logger.error("error:",e);
            systemService.addLog("计费方式["+chargeMode.getId()+"]删除失败", Globals.MODULE_CHARGE, Globals.Log_Leavel_ERROR,Globals.FAILD);
            message = "计费方式删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "isUsed")
    @ResponseBody
    public JSONObject isUsed(String id) {
        JSONObject result = new JSONObject();
        result.put("isUsed",false);
        try{
            //判断当前计费方式是否被服务使用
            List<ApiChargeModeRela> apiCharges = systemService.findByProperty(ApiChargeModeRela.class,"chargeModeId",id);
            if(apiCharges != null && apiCharges.size() > 0){
                result.put("isUsed",true);
            }else{
                //判断当前计费方式是否被场景使用
                List<ApiSceneChargeEntity> sceneCharge = systemService.findByProperty(ApiSceneChargeEntity.class,"chargeId",id);
                if(sceneCharge != null && sceneCharge.size() > 0){
                    result.put("isUsed",true);
                }
            }
        }catch(Exception e){
            logger.error("error:",e);
        }
        return result;
    }

}
