package com.kd.op.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.enums.RegisterRouteEnum;
import com.kd.op.util.Encrypt;
import com.kd.op.util.ResourceParams;
import com.kd.op.util.rtdb.RtdbCommon;
import com.kd.op.util.sm.SM2Key;
import com.kd.op.util.sm.SM2Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.commonJar.CommonUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.util.RandomStr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sunbox.api.model.ResponseVo;
import sunbox.core.hsf.api.SysManageService;
import sunbox.core.hsf.vo.OperatorItem;
import sunbox.core.vo.api.OpenAPIResponse;
import sunbox.gateway.api.model.system.OperatorItemModel;
import sunbox.gateway.api.model.system.OperatorRespModel;
import sunbox.gateway.api.service.system.SysSupportApiService;
import sunbox.gateway.api.service.system.SysTokenApiService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/apiLogin")
public class ApiLoginController {
    private static final Logger logger = Logger.getLogger(ApiLoginController.class);

    @Resource
    private SysSupportApiService sysSupportApiService;
    @Resource
    private SystemService systemService;
    @Resource
    private SysTokenApiService sysTokenApiService;
    @Resource
    private SysManageService sysManageService;

    private static final String accessTokenUser = "test";
    private static final String asdf = "AAAA";

    /**
     * 检查用户名称
     *
     * @param user
     * @param req
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping(params = "checkuser")
    @ResponseBody
    public AjaxJson checkuser(TSUser user, HttpServletRequest req, HttpServletResponse response) {
        HttpSession session = req.getSession();
        AjaxJson j = new AjaxJson();
        //将前台传过来的密码解密
        //首先获取密钥和随机数
        String ppp = req.getParameter("ppp");
        user.setPassword(ppp);
        String randomStr = req.getParameter("str");
        String passKey = SM2Key.DBPubKey;
        //判断是否是重放攻击
        String str = (String) session.getAttribute(passKey);
        if (str == null || str.isEmpty()) {
            j.setMsg("服务端缺少参数，登录失败");
            j.setSuccess(false);
            clearSession(session,passKey,randomStr,user.getUserName());
            systemService.addLoginFaildLog("用户"+user.getUserName()+"登陆时服务端缺少参数，登录失败！",
                    Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
            return j;
        }
        if(randomStr!=null&&StringUtils.isNotEmpty(randomStr)){
            if (!randomStr.equals(str)) {
                session.removeAttribute(passKey);
                j.setMsg("此次登陆不符合服务端要求，登录失败");
                j.setSuccess(false);
                clearSession(session,passKey,randomStr,user.getUserName());
                systemService.addLoginFaildLog("用户"+user.getUserName()+"此次登陆不符合服务端要求，登录失败！",
                        Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
                return j;
            }else {
                session.removeAttribute(str);
                String ss = RandomStr.createRandomString(15);
                session.setAttribute(passKey, ss);
                session.setAttribute(ss, passKey);
            }
        }

        //验证码
        String randCode = req.getParameter("randCode");

        //客户端ip
        String remoteIp = IpUtil.getIpAddr(req);
        //登录时间
        Date loginTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String accessTime =  sdf.format(loginTime);
        int at = Integer.parseInt(accessTime);
        user.setUserIp(remoteIp);
        user.setBeginDate(accessTime);

        if (randCode==null || StringUtils.isEmpty(randCode)) {
            j.setMsg("请输入验证码");
            j.setSuccess(false);
            clearSession(session,passKey,randomStr,user.getUserName());
        } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
            j.setMsg("验证码错误");
            j.setObj("1");//用来标记登陆失败，然后刷新验证码的标识
            j.setSuccess(false);
            //lf添加验证码错误-登录失败日志
            systemService.addLoginFaildLog("用户"+user.getUserName()+"验证码错误，登录失败！",
                    Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
            clearSession(session,passKey,randomStr,user.getUserName());
        } else {
            //对密码解密
            //处理密码中被拦截的+
            user.setPassword(user.getPassword().replaceAll(" ", "+"));
            user.setUserName(user.getUserName().replaceAll(" ","+"));
            String decrypted="";
            String decry="";
            try {
                user.setUserName(SM2Utils.decrypt(SM2Key.DBPrivKey, user.getUserName()));
                decrypted = SM2Utils.decrypt(SM2Key.DBPrivKey, user.getPassword());
                String[] sm3decrypt = SM2Utils.sm3decrypt(decrypted);
                decry=sm3decrypt[0];
            } catch (Exception e) {
                logger.error("error:",e);
                j.setMsg("用户名或密码错误！");
                j.setSuccess(false);
                j.setObj("1");//用来标记登陆失败，然后刷新验证码的标识
                //lf 账号或密码错误，登录失败日志记录
                systemService.addLoginFaildLog("用户"+user.getUserName()+"密码错误，登录失败",
                        Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
                clearSession(session,passKey,randomStr,user.getUserName());
                return j;
            }
            //语言选择
            String langCode = req.getParameter("langCode");
            if (langCode!=null) {
                req.getSession().setAttribute("lang", langCode);
            }
            //处理登录方式
            String routeType = req.getParameter("routeType");
            if(routeType != null && !routeType.isEmpty()){
                //处理车联网用户登陆
                if(routeType.equals(RegisterRouteEnum.chelianwang.getCode())){
                    j = clwLogin(user.getUserName(),decrypted,req,response);
                }
            }else{//没有其他登录方式就是按平台自己的账号登录处理
                //判断用户的数据库中的密码是否被篡改
                List<TSUser> tus = systemService.findByProperty(TSUser.class, "userName", user.getUserName());
                if (tus != null && tus.size() > 0) {
                    TSUser tu = tus.get(0);
                    try {
                        String dbDecrypt = null;
                        dbDecrypt = SM2Utils.decrypt(SM2Key.DBPrivKey, tu.getPassword());
                        if(!dbDecrypt.equals(decry)){
                            j.setMsg("用户名或密码错误！");
                            j.setSuccess(false);
                            j.setObj("1");//用来标记登陆失败，然后刷新验证码的标识
                            //lf 账号或密码错误，登录失败日志记录
                            systemService.addLoginFaildLog("用户"+user.getUserName()+"密码错误，登录失败",
                                    Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
                            clearSession(session,passKey,randomStr,user.getUserName());
                            return j;
                        }
                    } catch (Exception e) {
                        logger.error("error:",e);
                        j.setMsg("用户名或密码错误！");
                        j.setSuccess(false);
                        j.setObj("1");//用来标记登陆失败，然后刷新验证码的标识
                        //lf 账号或密码错误，登录失败日志记录
                        systemService.addLoginFaildLog("用户"+user.getUserName()+"密码错误，登录失败",
                                Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
                        clearSession(session,passKey,randomStr,user.getUserName());
                        return j;
                    }
                    //当前登陆的账号密码正确，所以将以前的错误登陆异常信息记录给删掉
                    if (tu.getStatus() == 1) {
                        //认证通过后记录日志
                        saveLoginSuccessInfo(req, tu,response,null);
                    }else {
                        j.setMsg("该账号不处于激活状态，不允许登陆！");
                        j.setSuccess(false);
                        //lf 连续登录失败日指添加
                        systemService.addLoginFaildLog("用户"+user.getUserName()+"不处于激活状态，不允许登陆",
                                Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
                        clearSession(session,passKey,randomStr,user.getUserName());
                        return j;
                    }
                }else{//用户名不存在
                    j.setMsg("用户名或密码错误！");
                    j.setSuccess(false);
                    //lf 连续登录失败日指添加
                    systemService.addLog("用户名"+user.getUserName()+"不存在，登录失败！",
                            Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0");
                    clearSession(session,passKey,randomStr,user.getUserName());
                    return j;
                }
            }
        }
        return j;
    }

    /**
     * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
     * @param req request
     * @param user 当前登录用户
     */
    private void saveLoginSuccessInfo(HttpServletRequest req, TSUser user,HttpServletResponse response,String token) {
        String message = null;
        Map<String, Object> userOrgMap = systemService.findOneForJdbc("select org_id as orgId from t_s_user_org where user_id=?", user.getId());
        String orgId = "";
        if(userOrgMap != null){
            orgId = userOrgMap.get("orgId") + "";
        }
        TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
        user.setCurrentDepart(currentDepart);

        HttpSession session = ContextHolderUtils.getSession();
        //复制session存放的登陆时的缓存的密钥之后，就将此次登陆的缓存数据删掉
        String passKey = (String) session.getAttribute(user.getUserName());
        String randomStr = (String) session.getAttribute(passKey);
        clearSession(session, passKey, randomStr, user.getUserName());

        HashMap old = new HashMap();
        String oldSessionId = session.getId();
        Enumeration keys = (Enumeration) session.getAttributeNames();

        while (keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            old.put(key, session.getAttribute(key));
            //			session.removeAttribute(key);
        }
        HttpSession newSession = session;
        session.invalidate();

        newSession = ContextHolderUtils.getSession();
        for (Iterator it = old.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getKey().equals(oldSessionId)) {
                newSession.setAttribute(newSession.getId(), entry.getValue());
            }else{
                newSession.setAttribute((String) entry.getKey(), entry.getValue());
            }
        }
        ResourceUtil.cacheUser(newSession,user);

        message = "用户: " + user.getUserName() + "["+ (currentDepart==null?"":currentDepart.getDepartname()) + "]登陆成功";

        //获取session中
        //当前session为空 或者 当前session的用户信息与刚输入的用户信息一致时，则更新Client信息
        Client clientOld = ClientManager.getInstance().getClient(newSession.getId());
        if(clientOld == null || clientOld.getUser() ==null ||user.getUserName().equals(clientOld.getUser().getUserName())){
            Client client = new Client();
            client.setIp(IpUtil.getIpAddr(req));
            client.setLogindatetime(new Date());
            client.setUser(user);
            client.setPassKey(passKey);
            client.setPrivKey(SM2Key.DBPrivKey);
            client.setSession(newSession);
            client.setExpiredSessionTime(ResourceUtil.safeParams.get("sessionExpiredTime"));
            client.setLastSessionTime(new Date());
            client.setRandomStr(randomStr);
            logger.info("client获取参数：" + token);
            client.setToken(token);
            ClientManager.getInstance().addClinet(newSession.getId(), client);
        } else {//如果不一致，则注销session并通过session=req.getSession(true)初始化session
            ClientManager.getInstance().removeClinet(newSession.getId());
            newSession.invalidate();
            newSession = req.getSession(true);//session初始化
            newSession.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
            String randCode = req.getParameter("randCode");
            if(randCode != null){
                newSession.setAttribute("randCode",randCode);//保存验证码
            }else{
                newSession.setAttribute("randCode",null);//保存验证码
            }

            checkuser(user,req,response);
        }
        //判断账号此次登陆是否IP变动过大
        boolean ipChange = systemService.checkIpChange(user.getId(), IpUtil.getIpAddr(req));
        if (ipChange) {
            systemService.addLog(user.getUserName() + "在客户端[" + IpUtil.getIpAddr(req) + "]登陆，IP地址变动过大",
                    Globals.Log_Type_IP_CHANGE_MORE, Globals.Log_Leavel_INFO,null);
            //记录异常信息
            systemService.createException(user.getUserName() + "登陆IP地址变动过大",
                    user.getUserName() + "在客户端[" + IpUtil.getIpAddr(req) + "]登陆，IP地址变动过大","2",user.getUserName(),Globals.Log_Type_IP_CHANGE_MORE);
        }
        // 添加登陆日志
        systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO,"1");
    }

    /**
     * 用户登录
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping(params = "login")
    public String login(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        TSUser user = ResourceUtil.getSessionUserName();
        StringBuffer stringBuffer = new StringBuffer();
        String roles = "";
        if (user != null) {
            List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            for (TSRoleUser ru : rUsers) {
                TSRole role = ru.getTSRole();
                stringBuffer.append(role.getRoleName() + ",");
            }
            if (stringBuffer.toString().length() > 0) {
                roles = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
            }
            modelMap.put("roleName", roles);
            modelMap.put("userName", user.getUserName());

            String departmentName = "";
            if(ClientManager.getInstance().getClient().getUser().getCurrentDepart() != null){
                departmentName = ClientManager.getInstance().getClient().getUser().getCurrentDepart().getDepartname();
            }
            modelMap.put("currentOrgName", departmentName);

            request.getSession().setAttribute("CKFinder_UserRole", "admin");

            request.getSession().setMaxInactiveInterval(30*60);//设置session过期时间

            SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
//            if ("ace".equals(sysTheme.getStyle())) {
                request.setAttribute("menuMap", getFunctionMap(user));
//            }
            //shortcut_top(request);  //获取菜单
            CommonUtils.setCookie(sysTheme,response);
            //判断当前登录用户是否是系统管理员或者是审计管理员
            boolean isAdmin = systemService.isAdmin();
            modelMap.put("isAdmin", isAdmin);
            boolean isAudit = systemService.isAudit();
            modelMap.put("isAudit", isAudit);
            return sysTheme.getIndexPath();
//			}else{
//				request.setAttribute("passUpdateFlag", user.getPass_update_flag());
//				return "login/login";
//			}
        } else {
            return "login/login";
        }
    }

    private Map<Integer, List<TSFunction>> getFunctionMap(TSUser user) {
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());
        if (client.getFunctionMap() == null || client.getFunctionMap().size() == 0) {
            Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
            //原来代码
            Map<String, TSFunction> loginActionlist = getUserFunction(user);
            if (loginActionlist.size() > 0) {
                Collection<TSFunction> allFunctions = loginActionlist.values();
                for (TSFunction function : allFunctions) {
                    if(function.getFunctionType().intValue()==Globals.Function_TYPE_FROM.intValue()){
                        //如果为表单或者弹出 不显示在系统菜单里面
                        continue;
                    }
                    if (!functionMap.containsKey(function.getFunctionLevel() + 0)) {
                        functionMap.put(function.getFunctionLevel() + 0,
                                new ArrayList<TSFunction>());
                    }
                    functionMap.get(function.getFunctionLevel() + 0).add(function);
                }
                // 菜单栏排序
                Collection<List<TSFunction>> c = functionMap.values();
                for (List<TSFunction> list : c) {
                    Collections.sort(list, new NumberComparator());
                }
            }
            client.setFunctionMap(functionMap);

            //清空变量，降低内存使用
            loginActionlist.clear();

            return functionMap;
        }else{
            return client.getFunctionMap();
        }
    }

    /**
     * 获取权限的map
     * @return
     */
    private Map<String, TSFunction> getUserFunction(TSUser user) {
        HttpSession session = ContextHolderUtils.getSession();
        Client client = ClientManager.getInstance().getClient(session.getId());

        if (client.getFunctions() == null || client.getFunctions().size() == 0) {

            Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();

            //如果为管理员，就显示所有可见菜单，否则正常显示
            if((user.getRegisterType() == null || user.getRegisterType().equals("00")) && user.getUserName().equals(ResourceParams.adManager)) {
                List<TSFunction> list1 = systemService.findByProperty(TSFunction.class, "showStatus", (short)1);
                for(TSFunction function:list1){
                    loginActionlist.put(function.getId(),function);
                }
                client.setFunctions(loginActionlist);
                //清空变量，降低内存使用
                list1.clear();
            }else {
                StringBuilder hqlsb1=new StringBuilder("select distinct f from TSFunction f,TSRoleFunction rf,TSRoleUser ru  ").append("where ru.TSRole.id=rf.TSRole.id and rf.TSFunction.id=f.id and ru.TSUser.id=? ");

                StringBuilder hqlsb2=new StringBuilder("select distinct c from TSFunction c,TSRoleFunction rf,TSRoleOrg b,TSUserOrg a ")
                        .append("where a.tsDepart.id=b.tsDepart.id and b.tsRole.id=rf.TSRole.id and rf.TSFunction.id=c.id and a.tsUser.id=?");
                List<TSFunction> list1 = systemService.findHql(hqlsb1.toString(),user.getId());
                List<TSFunction> list2 = systemService.findHql(hqlsb2.toString(),user.getId());
                for(TSFunction function:list1){
                    loginActionlist.put(function.getId(),function);
                }
                for(TSFunction function:list2){
                    loginActionlist.put(function.getId(),function);
                }
                client.setFunctions(loginActionlist);

                //清空变量，降低内存使用
                list2.clear();
                list1.clear();
            }
        }
        return client.getFunctions();
    }

    /**
     * 退出系统
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = ContextHolderUtils.getSession();
        TSUser user = ResourceUtil.getSessionUserName();
        if(user != null) {
            try {
                systemService.addLoginFaildLog("用户" + user.getUserName() + "已退出",Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO,"1",user.getUserName());
            } catch (Exception e) {
                LogUtil.error(e.toString());
            }

            ClientManager.getInstance().removeClinet(session.getId());
            session.invalidate();
            ResourceUtil.onlineUsers.remove(user.getId());
        }

        ModelAndView modelAndView = new ModelAndView(new RedirectView("apiLogin.do?login"));
        return modelAndView;
    }

    private void clearSession(HttpSession session,String passKey,String randomStr,String userName){
        session.removeAttribute(passKey);
        session.removeAttribute(randomStr);
        session.removeAttribute(userName);
    }

    @RequestMapping(params = "register")
    public ModelAndView register(){
        ModelAndView model = new ModelAndView("main/register");
        List<TSRole> list = systemService.loadAll(TSRole.class);
        List<TSRole> roles = new ArrayList<>();
        for(TSRole role:list){
            if(role.getRoleCode().equals(ResourceParams.adminCode)){
                roles.add(role);
            }else if(role.getRoleCode().equals(ResourceParams.ispCode)){
                roles.add(role);
            }else if(role.getRoleCode().equals(ResourceParams.isvCode)){
                roles.add(role);
            }else if(role.getRoleCode().equals(ResourceParams.isp_check)){
                roles.add(role);
            }else if(role.getRoleCode().equals(ResourceParams.audit)){
                roles.add(role);
            }
        }
        model.addObject("roles",roles);
        return model;
    }

    @RequestMapping(params="registerUser")
    @ResponseBody
    public AjaxJson registerUser(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        //获取注册信息并进行非空校验
        String zjy = request.getParameter("zjy");
        if(zjy == null || zjy.isEmpty()){
            j.setMsg("请输入用户名！");
            return j;
        }
        String nyj = request.getParameter("nyj");

        if(nyj == null || nyj.isEmpty()){
            j.setMsg("请输入密码！");
            return j;
        }
        String role = request.getParameter("role");
        if(role == null || role.isEmpty()){
            j.setMsg("请选择角色！");
            return j;
        }
        String routeType = request.getParameter("routeType");
        if(routeType == null || routeType.isEmpty()){
            j.setMsg("请选择授权方！");
            return j;
        }
        //获取秘钥和随机数
        HttpSession session = request.getSession();
        String randomStr = request.getParameter("str");
        String passKey = SM2Key.DBPubKey;
        //判断是否是重放攻击
        String str = (String) session.getAttribute(passKey);
        if (str == null || str.isEmpty()) {
            j.setMsg("服务端缺少参数，登录失败");
            j.setSuccess(false);
            clearSession(session,passKey,randomStr,zjy);
//            systemService.addLoginFaildLog("用户"+user.getUserName()+"登陆时服务端缺少参数，登录失败！",
//                    Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
            return j;
        }
        if(randomStr!=null&&StringUtils.isNotEmpty(randomStr)){
            if (!randomStr.equals(str)) {
                session.removeAttribute(passKey);
                j.setMsg("此次登陆不符合服务端要求，登录失败");
                j.setSuccess(false);
                clearSession(session,passKey,randomStr,zjy);
//            systemService.addLoginFaildLog("用户"+user.getUserName()+"此次登陆不符合服务端要求，登录失败！",
//                    Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
                return j;
            }
        }

        //处理密码中被拦截的+
        zjy = zjy.replaceAll(" ","+");
        nyj = nyj.replaceAll(" ","+");
        String decrypted="";
        String decry="";
        try {
            zjy = SM2Utils.decrypt(SM2Key.DBPrivKey, zjy);
            nyj = SM2Utils.decrypt(SM2Key.DBPrivKey, nyj);
        } catch (Exception e) {
            logger.error("error:",e);
            j.setMsg("用户名或密码解析错误！");
            j.setSuccess(false);
            //lf 账号或密码错误，登录失败日志记录
//            systemService.addLoginFaildLog("用户"+user.getUserName()+"密码错误，登录失败",
//                    Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO, "0",user.getUserName());
            clearSession(session,passKey,randomStr,zjy);
            return j;
        }
        //判断注册渠道
        if(routeType.equals(RegisterRouteEnum.chelianwang.getCode())){//车联网账号授权
            return chelianwangRegister(zjy,nyj,role);
        }/*else if(routeType.equals(RegisterRouteEnum.SESS.getCode())){//智慧能源账号授权
            //TODO
        }*/
        return j;
    }

    /**
     * 车联网账号授权
     * @param userName 用户名
     * @param p 密码
     * @param roleId 角色
     * @return
     */
    private AjaxJson chelianwangRegister(String userName,String p,String roleId){
        AjaxJson j = new AjaxJson();
        //首先判断在车联网渠道下，该账号是否已经被授权
        //boolean onlyAddRole = false;//判断是否已经存在用户信息，只需要添加角色关联
        TSUser hadUser = null;//保存已经存在过的用户对象，用来关联角色信息
        List<TSUser> users = systemService.findByProperty(TSUser.class,"userName",userName);
      /*  if(users != null && users.size() > 0){
            for(TSUser user:users){
                if(user.getRegisterType() != null && user.getRegisterType().equals(RegisterRouteEnum.chelianwang.getCode())){
                    //目前限定用户只能有一个角色
//                    onlyAddRole = true;
//                    hadUser = user;
//                    List<TSRoleUser> rus = systemService.findByProperty(TSRoleUser.class,"TSUser.id",user.getId());
//                    if(rus != null && rus.size() > 0){
//                        for(TSRoleUser ru:rus){
//                            if(ru.getTSRole() != null && ru.getTSRole().getId().equals(roleId)){
//                                j.setMsg("该用户已经授权过，不能重复授权！");
//                                j.setSuccess(false);
//                                return j;
//                            }
//                        }
//                    }
//                    break;
                    //如果账号已经被授权过了，就不能被授权其他角色
//                    j.setMsg("该用户已经被授权！");
//                    j.setSuccess(false);
//                    return j;
                }
            }
        }*/
        //获取中台的accessToken
//        AccessTokenModel accessTokenModel =new AccessTokenModel();
//        accessTokenModel.setUserName(accessTokenUser);
//        accessTokenModel.setKey(Encrypt.md5Capital(asdf));
//        String accessToken= "";//平台认证
        String token = "";//操作员认证
        /*try {
            AccessTokenModel vo = (AccessTokenModel)sysTokenApiService.getAccessToken(accessTokenModel).getData();
            accessToken = vo.getAccessToken();
        }catch (Exception e){
            logger.error("连接车联网失败：" + e.getMessage());
            j.setMsg("连接车联网系统失败，请联系管理员！");
            j.setSuccess(false);
            return j;
        }
        if(accessToken == null || accessToken.isEmpty()){
            j.setMsg("获取车联网的连接参数失败，请联系管理员！");
            j.setSuccess(false);
            return j;
        }*/
        //校验车联网的账号
        try {
            //校验用户名和密码
            OpenAPIResponse vo = sysManageService.login(null,userName,Encrypt.encryptLow(p),null);
            if(vo.getCode() == 1001 || vo.getCode() == 1002){
                j.setMsg("用户名或密码错误！");
                j.setSuccess(false);
                return j;
            }
            if(vo.getCode() == -1){
                j.setMsg("车联网用户信息校验失败，请联系管理员！");
                j.setSuccess(false);
                return j;
            }
            OperatorItem operatorItem = (OperatorItem) vo.getData();
            if(operatorItem == null){
                j.setMsg("该账号在车联网不存在！");
                j.setSuccess(false);
                return j;
            }
            //如果只是添加角色，就不需要从车联网那里获取数据
            //if(!onlyAddRole){
                //将用户信息保存进平台数据库，等待审核
            //如果尚未添加过用户信息，就将新创建的用户信息赋给它

            hadUser = (TSUser) CommonUtils.getTSBuser(operatorItem, systemService);
            //}
            //处理完用户和部门关联信息之后，添加角色关联信息
            if(hadUser != null){
                TSRoleUser ru = new TSRoleUser();
                ru.setTSUser(hadUser);
                TSRole role = systemService.getEntity(TSRole.class,roleId);
                ru.setTSRole(role);
                systemService.save(ru);
                hadUser.setUserKey(role.getRoleName());
                systemService.saveOrUpdate(hadUser);
            }

        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg("授权车联网用户失败：" + e.getMessage());
            logger.error("查询车联网账号失败：" + e.getMessage());
        }
        return j;
    }

    /**
     * 车联网账号登陆
     * @param userName 用户名
     * @param p
     * @return
     */
    public AjaxJson clwLogin(String userName,String p,HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        //先判断账号是否被授权过
        TSUser clwUser = null;
        List<TSUser> users = systemService.findByProperty(TSUser.class, "userName", userName);
        if(users != null){
            for(TSUser u:users){
                if(u.getRegisterType() != null && u.getRegisterType().equals(RegisterRouteEnum.chelianwang.getCode())){
                    clwUser = u;
                    break;
                }
            }
        }
        if(clwUser == null){
            j.setMsg("当前账号没有被授权！");
            j.setSuccess(false);
            j.setObj("1");//用来标记登陆失败，然后刷新验证码的标识
            return j;
        }
        if (clwUser.getStatus() != 1) {
            j.setMsg("当前授权账号尚未激活！");
            j.setSuccess(false);
            j.setObj("1");//用来标记登陆失败，然后刷新验证码的标识
            return j;
        }
        //获取中台的accessToken
        String accessToken= RtdbCommon.loadAccessToken();//平台认证
        logger.info("连接参数：" + accessToken);
        if(accessToken == null || accessToken.isEmpty()){
            j.setMsg("获取车联网的连接参数失败！");
            j.setSuccess(false);
            j.setObj("1");
            return j;
        }
        //校验车联网的账号
        OperatorItemModel operator = new OperatorItemModel();
        operator.setUserName(clwUser.getUserName());
        operator.setPassword(Encrypt.encryptLow(p));
        operator.setAccessToken(accessToken);
        operator.setBusinessLabel(ResourceParams.businessLabel);
//        operator.setToken(client.getToken());
        try {
            logger.info(operator.getPassword());
            ResponseVo vo = sysSupportApiService.login(operator);
            logger.info(">:" + JSONObject.toJSONString(vo));
            if(vo.getErrorCode() !=null){
                if(vo.getErrorCode() == 900000){
                    j.setMsg("接入单位accessToken验证失败！");
                    j.setSuccess(false);
                    return j;
                }
                if(vo.getErrorCode() == 100001 || vo.getErrorCode() == 100201){
                    j.setMsg("用户名或密码不正确！");
                    j.setSuccess(false);
                    return j;
                }
            }
            OperatorRespModel operatorItem = (OperatorRespModel) vo.getData();
            if(operatorItem == null){
                j.setMsg("该账号在车联网不存在！");
                j.setSuccess(false);
                return j;
            }
            //登陆成功缓存用户信息
            saveLoginSuccessInfo(request, clwUser,response,operatorItem.getToken());
        }catch (Exception e){
//            e.printStackTrace();
            logger.error("查询车联网账号失败：" + e.getMessage());
        }
        return j;
    }

}
