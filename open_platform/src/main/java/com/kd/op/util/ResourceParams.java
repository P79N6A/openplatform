package com.kd.op.util;

import com.kd.op.api.service.InnerCommonServiceI;
import com.kd.op.common.CustomConstant;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 缓存参数类，存放需要缓存的数据
 */
@Component
public class ResourceParams {
    private static final Logger looger = Logger.getLogger(ResourceParams.class);

    public static String adminCode = null;//平台管理员角色编码
    public static String ispCode = null;//ISP角色编码
    public static String isvCode = null;//ISV角色编码
    public static String appBuyer = null;//普通用户角色编码
    public static String adManager = null;//super 管理员账号
    public static String isp_check = null;//开放平台审核员
    public static String audit = null;//审计管理员
    public static Integer businessLabel = null;//业务标签（车联网中台需要）


    private static SystemService systemService;

    @Autowired
    public void setSystemService(SystemService systemService){
        ResourceParams.systemService = systemService;
    }

    static{
        try {
            ResourceBundle rb = ResourceBundle.getBundle("sysConfig");
            adminCode = rb.getString("admin");
            ispCode = rb.getString("isp");
            isvCode = rb.getString("isv");
            appBuyer = rb.getString("appBuyer");
            adManager = rb.getString("adManager");
            audit = rb.getString("audit");
            isp_check = rb.getString("isp_check");
            String businessLabelStr = rb.getString("businessLabel");
            try {
                businessLabel = Integer.parseInt(businessLabelStr);
            }catch (Exception e){
                looger.error("error:",e);
            }
        }catch (Exception e){
            looger.error("获取配置文件中的角色编码失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前用户，列表加载数据时所限定的用户范围
     * @return
     */
    public static String[] getLoadedUsers() {
        List<String> users = new ArrayList<>();
        //首先获取当前登录用户
        TSUser user = ResourceUtil.getSessionUser();
        if(user == null){
            return null;
        }
        //判断是否是super管理员（账号来源必须是来自平台本身）
        //判断平台本身用户
        if(user.getRegisterType() == null || user.getRegisterType().equals("00")){
            if(!user.getUserName().equals(ResourceParams.adManager)){
                users.add(user.getUserName());
            }
        }else{//来自其他平台的授权用户
            users.add(user.getUserName());
        }
        if(users.isEmpty()){
            return null;
        }
        String[] array = new String[users.size()];
        return users.toArray(array);
    }


    /**
     * 是否过滤当前用户数据
     * @return
     */
    public static Map<String,Object> getAuthorityUsers() {
        Map<String,Object> result = new HashMap<>();
        String roleCode = null;
        //首先获取当前登录用户
        TSUser user = ResourceUtil.getSessionUser();
        if(user == null){
            return null;
        }
        // 根据用户id得到角色关联信息
        String sql = "select * from t_s_role_user where userid='"+user.getId()+"'";
        List<Map<String,Object>> roleUseList = systemService.findForJdbc(sql);
        for(Map<String,Object> map:roleUseList){
            String roleid = (String)map.get("roleid");
            String rolesql = "select * from t_s_role where id ='"+roleid+"'";
            List<Map<String,Object>> roleList = systemService.findForJdbc(rolesql);
            for(Map<String,Object> rolemap:roleList){
                // 判断角色不是admin，开放平台审核员，审计管理员，需要过滤返回true，不需要返回false
                   roleCode = (String)rolemap.get("rolecode");
                    if((roleCode.equals(ResourceParams.adminCode))||(roleCode.equals(ResourceParams.isp_check))||(roleCode.equals(ResourceParams.audit))){
                        result.put(CustomConstant.USERNAME,user.getUserName());
                        result.put(CustomConstant.IS_FILTER,CustomConstant.FILTER_DATA_FALSE);
                    }else{
                        result.put(CustomConstant.USERNAME,user.getUserName());
                        result.put(CustomConstant.IS_FILTER,CustomConstant.FILTER_DATA_TRUE);
                    }
                }
            }
        return result;
    }
}
