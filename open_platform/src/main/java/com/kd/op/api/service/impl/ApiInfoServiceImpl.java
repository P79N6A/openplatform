package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiInfoServiceI;
import com.kd.op.api.service.InnerCommonServiceI;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.ExcelReaderUtil;
import com.kd.op.util.ExcelUtils;
import com.kd.op.util.HibernateConfigurationHelper;
import com.kd.op.util.rtdb.RtdbCommon;
import com.kd.openplatform.activeAPI.OpenplatformAPI;
import com.kd.openplatform.activeAPI.OpenplatformMqApi;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;


@Service("apiInfoService")
@Transactional
public class ApiInfoServiceImpl extends CommonServiceImpl implements ApiInfoServiceI {

	private final static Logger logger = Logger.getLogger(ApiInfoServiceImpl.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private InnerCommonServiceI innerCommonService;

	@Autowired
    private OpenplatformMqApi openplatformMqApi;

	// 用于批量插入
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	@Transactional
	public void addMain(ApiInfoEntity apiInfo, String headers, String requests, String returns) {
		apiInfo.setApiAuditStatus(0);
		apiInfo.setApiPublishStatus(0);
		apiInfo.setApiStatus(0);
		apiInfo.setApiVisibleStatus(0);
		apiInfo.setApiRunStatus(0);
		TSUser currentUser = ResourceUtil.getSessionUserName();

		/**保存-接口参数*/
		JSONArray headesArray = JSONArray.parseArray(headers);
		for (int i = 0; i < headesArray.size(); i++) {
			JSONObject json = headesArray.getJSONObject(i);
			if (StringUtil.isEmpty(json.get("isSource").toString())) {
				continue;
			}
			String isSource = json.get("isSource").toString();
			if (Integer.parseInt(isSource.isEmpty() ? "0" : isSource) == 1) {
				apiInfo.setResourceCtrl(Integer.parseInt(isSource.isEmpty() ? "0" : isSource));
				break;
			}
		}
		saveParams(apiInfo, headesArray, 2);
		JSONArray requestArray = JSONArray.parseArray(requests);
		for (int i = 0; i < requestArray.size(); i++) {
			JSONObject j = requestArray.getJSONObject(i);
			if (StringUtil.isEmpty(j.get("isSource").toString())) {
				continue;
			}
			String isSource = j.get("isSource").toString();
			if (Integer.parseInt(isSource.isEmpty() ? "0" : isSource) == 1) {
				apiInfo.setResourceCtrl(Integer.parseInt(isSource.isEmpty() ? "0" : isSource));
				break;
			}
		}
		//保存主信息
		systemService.save(apiInfo);
		saveParams(apiInfo, requestArray, 0);

		JSONArray returnArray = JSONArray.parseArray(returns);
		saveParams(apiInfo, returnArray, 1);
	}


	@Override
	//根据类名称自动生成HTTP、HSF请求地址
	public ApiInfoEntity generateAddr(ApiInfoEntity apiInfo) {
		String addrHTTP = null;
		String addrHSF = null;
		String apiClassNameGet = apiInfo.getApiClassName();
		String apiMethodNameGet = apiInfo.getApiMethodName();
		String genAddr = apiClassNameGet + "_" + apiMethodNameGet;
		genAddr = genAddr.replaceAll("\\.", "_");
		CriteriaQuery cqHTTP = new CriteriaQuery(ApiInfoEntity.class);
		cqHTTP.like("reqAddrHttp", genAddr); //模糊匹配类名称
		cqHTTP.add();
		List<ApiInfoEntity> reqAddrHttpList = systemService.getListByCriteriaQuery(cqHTTP, false);
		CriteriaQuery cqHSF = new CriteriaQuery(ApiInfoEntity.class);
		cqHSF.like("reqAddrHsf", genAddr); //模糊匹配类名称
		cqHSF.add();
		List<ApiInfoEntity> reqAddrHsfList = systemService.getListByCriteriaQuery(cqHSF, false);
		if (reqAddrHttpList.size() == 0) {
			addrHTTP = genAddr;
		} else {
			addrHTTP = genAddr + reqAddrHttpList.size();
		}
		if (reqAddrHsfList.size() == 0) {
			addrHSF = genAddr;
		} else {
			addrHSF = genAddr + reqAddrHsfList.size();
		}
		apiInfo.setReqAddrHttp(addrHTTP);
		apiInfo.setReqAddrHsf(addrHSF);
		return apiInfo;
	}

	@Override
	public ApiInfoEntity generateAddrWs(ApiInfoEntity apiInfo) {
	    String addrHsf;
        String reqAddrHttp = apiInfo.getReqAddrHttp();
        String apiName = apiInfo.getApiMethodName();
        String wsMark=reqAddrHttp+"/"+apiName;
        CriteriaQuery cqHSF = new CriteriaQuery(ApiInfoEntity.class);
        cqHSF.like("reqAddrHsf", wsMark); //模糊匹配类名称
        cqHSF.add();
        List<ApiInfoEntity> wsMarkList = systemService.getListByCriteriaQuery(cqHSF, false);
        if (wsMarkList.size() == 0) {
            addrHsf = wsMark;
        } else {
            addrHsf = wsMark + wsMarkList.size();
        }
        apiInfo.setReqAddrHsf(addrHsf);
        return apiInfo;
	}

	@Override
	//保存api信息
	@Transactional(rollbackFor = Exception.class)
	public void addApi(ApiInfoTotalEntity infoTotal, ApiInfoEntity apiInfo, String headers, String requests, String returns) throws Exception {
		//保存服务信息
		addMain(apiInfo, headers, requests, returns);
		//保存服务与计费方式的信息
		String chargeModeIds = infoTotal.getChargeModeIds();
		if (chargeModeIds != null && !chargeModeIds.isEmpty()) {
			String[] ids = chargeModeIds.split(",");
			for (String id : ids) {
				ApiChargeModeRela rela = new ApiChargeModeRela();
				rela.setApiId(apiInfo.getId());
				rela.setChargeModeId(id);
				systemService.save(rela);
			}
		}

		//保存服务与流量方式的信息
		String flowModeIds = infoTotal.getFlowModeIds();
		if (flowModeIds != null && !flowModeIds.isEmpty()) {

			TSUser currentUser = ResourceUtil.getSessionUserName();
			ApiFlowModeRelaEntity rela = new ApiFlowModeRelaEntity();
			rela.setApiId(apiInfo.getId());
			rela.setUserId(currentUser.getId());
			rela.setFlowCtrlModes(infoTotal.getFlowModeIds());
			systemService.save(rela);

		}

		//保存数据资源信息
		String recourceAccessIds = infoTotal.getResourceAccessIds();
		if (recourceAccessIds != null && !recourceAccessIds.isEmpty()) {
			String[] ids = recourceAccessIds.split(",");
			for (String id : ids) {
				ApiResourceAccessRelaEntity rela = new ApiResourceAccessRelaEntity();
				rela.setApiId(apiInfo.getId());
				rela.setResourceId(id);
				systemService.save(rela);
			}
		}
	}

	@Override
	//保存api信息
	public void updateApi(ApiInfoTotalEntity infoTotal, ApiInfoEntity apiInfo, String headers, String requests, String returns) throws Exception {
		//保存服务信息
		updateMain(apiInfo, headers, requests, returns);
		//保存服务与计费方式的信息
		String sql = "delete from api_charge_mode_rela where api_id='%s'";
		sql = String.format(sql, infoTotal.getApiInfo().getId());
		systemService.executeSql(sql);
		String chargeModeIds = infoTotal.getChargeModeIds();
		if (chargeModeIds != null && !chargeModeIds.isEmpty()) {
			String[] ids = chargeModeIds.split(",");
			for (String id : ids) {
				ApiChargeModeRela rela = new ApiChargeModeRela();
				rela.setApiId(apiInfo.getId());
				rela.setChargeModeId(id);
				systemService.save(rela);
			}
		}

		//保存服务与流量方式的信息
		String sqlFlow = "delete from api_flow_mode_rela where api_id='%s'";
		sqlFlow = String.format(sqlFlow, infoTotal.getApiInfo().getId());
		systemService.executeSql(sqlFlow);
		String flowModeIds = infoTotal.getFlowModeIds();
		if (flowModeIds != null && !flowModeIds.isEmpty()) {

			TSUser currentUser = ResourceUtil.getSessionUserName();
			ApiFlowModeRelaEntity rela = new ApiFlowModeRelaEntity();
			rela.setApiId(apiInfo.getId());
			rela.setUserId(currentUser.getId());
			rela.setFlowCtrlModes(infoTotal.getFlowModeIds());
			systemService.save(rela);

		}

		//保存数据资源信息
		String sqlResource = "delete from api_resource_rela where api_id='%s'";
		sqlResource = String.format(sqlResource, infoTotal.getApiInfo().getId());
		systemService.executeSql(sqlResource);
		String recourceAccessIds = infoTotal.getResourceAccessIds();
		if (recourceAccessIds != null && !recourceAccessIds.isEmpty()) {
			String[] ids = recourceAccessIds.split(",");
			for (String id : ids) {
				ApiResourceAccessRelaEntity rela = new ApiResourceAccessRelaEntity();
				rela.setApiId(apiInfo.getId());
				rela.setResourceId(id);
				systemService.save(rela);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ApiInfoEntity apiInfo, String headers, String requests, String returns) {
		//保存主表信息
		if (StringUtil.isNotEmpty(apiInfo.getId())) {
			try {
				ApiInfoEntity temp = findUniqueByProperty(ApiInfoEntity.class, "id", apiInfo.getId());
				MyBeanUtils.copyBeanNotNull2Bean(apiInfo, temp);
				temp.setApiVisibleStatus(0);
				temp.setApiAuditStatus(0);
				temp.setApiRunStatus(0);
				temp.setApiPublishStatus(0);
				temp.setApiStatus(0);
				systemService.saveOrUpdate(temp);
			} catch (Exception e) {
				logger.error("updateMain update apiInfo error");
			}
		} else {
			systemService.saveOrUpdate(apiInfo);
		}
		//===================================================================================
		//更新参数表
		//首先删除原来的参数信息
		String sql = "delete from api_param where api_id='%s'";
		sql = String.format(sql, apiInfo.getId());
		systemService.executeSql(sql, new HashMap<>());
		//然后将新的参数信息插入表中
		JSONArray headesArray = JSONArray.parseArray(headers);
		saveParams(apiInfo, headesArray, 2);

		JSONArray requestArray = JSONArray.parseArray(requests);
		saveParams(apiInfo, requestArray, 0);

		JSONArray returnArray = JSONArray.parseArray(returns);
		saveParams(apiInfo, returnArray, 1);
	}

	public void saveParams(ApiInfoEntity apiInfo, JSONArray paramArray, Integer type) {
		if (paramArray != null) {
			//保存前台中的id对应的真实id
			Map<String, String> idMap = new HashMap<>();
			for (int i = 0; i < paramArray.size(); i++) {
				JSONObject json = paramArray.getJSONObject(i);
				if (StringUtil.isEmpty(json.get("paramName").toString())) {
					continue;
				}
				ApiParamEntity param = new ApiParamEntity();
				param.setApiId(apiInfo.getId());
				String dataType = json.get("dataType").toString();
				param.setDataType(Integer.parseInt(dataType.isEmpty() ? "0" : dataType));
				param.setDefaultValue(json.get("defaultValue") + "");
				param.setParamDesc(json.get("paramDesc") + "");
				param.setParamName(json.get("paramName") + "");
				param.setParamType(type);
				String paramVisible = json.get("paramVisible").toString();
				param.setparamVisible(Integer.parseInt(paramVisible.isEmpty() ? "0" : paramVisible));
				String paramEncrypt = json.get("paramEncrypt").toString();
				param.setParamEncrypt(Integer.parseInt(paramEncrypt.isEmpty() ? "0" : paramEncrypt));
				String isSource = json.get("isSource").toString();
				param.setIsSource(Integer.parseInt(isSource.isEmpty() ? "0" : isSource));
				param.setSort(i + 1);
				//获取对应的parentId
				//首先获取前台封装数据时的id和parentId
				String id = json.getString("id");
				String parentId = json.getString("parentId");
				//再从缓存中获取到parentId对应的真实parentId
				parentId = idMap.get(parentId);
				param.setParentId(parentId);
				systemService.save(param);
				//当前参数入库之后生成的id放入缓存中，留待子参数使用
				idMap.put(id, param.getId());
			}
		}
	}


	public void delMain(ApiInfoEntity apiInfo) {
		//删除主表信息
		systemService.delete(apiInfo);
		//===================================================================================
		//获取参数
		Object id0 = apiInfo.getId();
		//===================================================================================
		//删除-接口参数
		String hql0 = "from ApiParamEntity where 1 = 1 AND aPI_ID = ? ";
		List<ApiParamEntity> apiParamOldList = this.findHql(hql0, id0);
		systemService.deleteAllEntitie(apiParamOldList);
	}


	@Override
	public Object[] getCurrentUserApiids() {
		List<String> apiIds = new ArrayList<>();
		apiIds.add("xxxxxx");

		TSUser user = ResourceUtil.getSessionUser();
		String hql = "FROM ApiInfoEntity aa WHERE aa.createBy = ? ";
		List<ApiInfoEntity> apiEntities = systemService.findHql(hql, user.getUserName());
		apiEntities.forEach(p -> {
			apiIds.add(p.getId());
		});

		return apiIds.toArray();
	}

	@Override
	public void exportExcel(ApiInfoEntity apiInfo, HttpServletResponse response) throws Exception {
		//全部数据导出到一个excel中，不同的sheet标识不同的表
		//准备excel对象
		ExcelEntity excelEntity = new ExcelEntity();
		//设置文件名
		excelEntity.setFileName(CustomConstant.EXPORT_FILENAME + DateUtils.date2Str(new Date(), DateUtils.yyyyMMdd) + CustomConstant.EXPORT_FILENAME_SUFFIX);
		//查询当前使用的数据库名称
		String currDbName = findOneForJdbc(CustomConstant.queryCurrDbName).get("currDbName").toString();

		//接收返回结果
		List<Map<String, Object>> resultList = null;
		List<String> apiIds = new ArrayList<>();
		//准备sheet列表
		List<ExcelEntity.SheetEntity> sheetEntities = new ArrayList<>();
		//遍历所有需要导出的实体
		for (Class entity : CustomConstant.GETEXPORTENTITiES()) {
			Table anno = (Table) entity.getAnnotation(Table.class);
			//获取类注解的value值
			String tableName = anno.name();
			//组装sheet对象
			ExcelEntity.SheetEntity sheet = excelEntity.new SheetEntity(tableName);
			//获取表字段名称
			List<Map<String, Object>> colMapList = findForJdbc(CustomConstant.queryTableColumns, tableName, currDbName);
			//填充表头
			String[] colList = new String[colMapList.size()];
			for (int i = 0; i < colList.length; i++) {
				colList[i] = colMapList.get(i).get("COLUMN_NAME").toString();
			}
			sheet.setTitles(colList);
			if (entity.equals(ApiInfoEntity.class)) {
				resultList = queryApiInfo(apiInfo);
				//填充apiId列表
				if (resultList != null && resultList.size() > 0) {
					for (Map<String, Object> apiMap : resultList) {
						apiIds.add(apiMap.get("id").toString());
					}
				}
			} else if (Arrays.asList(CustomConstant.GETEXPORTRELAENTITiES()).contains(entity)) {
				resultList = queryRelaTables(tableName, apiIds);
			} else if (Arrays.asList(CustomConstant.GETEXPORTNORELAENTITiES()).contains(entity)) {
				resultList = queryNoRelaTables(tableName);
			}

			//如果有值再做值的查询和填充否则不做
			if (resultList != null && resultList.size() > 0) {
				//准备内容
				String[][] contents = new String[resultList.size()][colList.length];
				for (int i = 0; i < resultList.size(); i++) {
					for (int j = 0; j < colList.length; j++) {
						contents[i][j] = resultList.get(i).get(colList[j]) == null ? null : resultList.get(i).get(colList[j]).toString();
					}
				}
				sheet.setContents(contents);
			}
			sheetEntities.add(sheet);
		}
		excelEntity.setSheets(sheetEntities);
		ExcelUtils.exportExcel(excelEntity, response);
	}

	private List<Map<String, Object>> queryApiInfo(ApiInfoEntity apiInfo) {
		Table anno = (Table) apiInfo.getClass().getAnnotation(Table.class);
		//获取类注解的value值
		String tableName = anno.name();
		String queryApiInfo = "SELECT * FROM " + tableName;
		List<Map<String, Object>> apiInfos = null;
		String groupId = apiInfo.getGroupId();
		Integer apiAuditStatus = apiInfo.getApiAuditStatus();
		if (StringUtil.isNotEmpty(groupId) && StringUtil.isNotEmpty(apiAuditStatus)) {
			queryApiInfo += " WHERE group_id = ? AND api_audit_status = ? ";
			apiInfos = findForJdbc(queryApiInfo, groupId, apiAuditStatus);
		} else if (StringUtil.isNotEmpty(groupId)) {
			queryApiInfo += " WHERE group_id = ? ";
			apiInfos = findForJdbc(queryApiInfo, groupId);
		} else if (StringUtil.isNotEmpty(apiAuditStatus)) {
			queryApiInfo += " WHERE api_audit_status = ? ";
			apiInfos = findForJdbc(queryApiInfo, apiAuditStatus);
		} else {
			apiInfos = findForJdbc(queryApiInfo);
		}
		return apiInfos;
	}

	private List<Map<String, Object>> queryRelaTables(String tableName, List<String> apiIds) {
		List<Map<String, Object>> resultList = null;
		String sql = "SELECT * FROM " + tableName + " WHERE api_id in ( :apiIds ) ";
		resultList = getSession().createSQLQuery(sql)
				.setParameterList("apiIds", apiIds)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
				.list();
		return resultList;
	}

	private List<Map<String, Object>> queryNoRelaTables(String tableName) {
		List<Map<String, Object>> resultList = null;
		String sql = "SELECT * FROM " + tableName;
		resultList = findForJdbc(sql);
		return resultList;
	}

	@Override
	public void importExcel(MultipartFile[] files) throws Exception {

		int len = files.length;
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				MultipartFile file = files[i];
				if (file.isEmpty()) {
					// TODO文件为空时处理
					logger.info("文件为空");
				} else {
					//file.transferTo(new File("服务器文件上传目录" + file.getName()));
					Workbook excel = ExcelReaderUtil.transFileToExcel(file);
					if (excel != null) {
						if (excel.getNumberOfSheets() > 0) {
							for (int j = 0; j < excel.getNumberOfSheets(); j++) {
								dealSheet(excel.getSheetAt(j));
							}
						}
					}
				}
			}
		}

	}

	@Override
	public List<TSUser> getAllISV() {
		ResourceBundle rb = ResourceBundle.getBundle("sysConfig");
		String developer = rb.getString("isv");
		String sql = "select user.* from t_s_base_user user " +
				" left join t_s_role_user ru on user.id = ru.userid" +
				" left join t_s_role role on role.id = ru.roleid" +
				" where role.rolecode=:roleCode";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("roleCode", developer);
		List<Map<String, Object>> list = query.list();
		List<TSUser> users = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				TSUser user = new TSUser();
				user.setId((String) map.get("ID"));
				user.setUserName((String) map.get("username"));
				users.add(user);
			}
		}
		return users;
	}

	@Override
	public List<ApiInfoEntity> getApiInfoByGroupId(String groupId) {
		String hql = "From ApiInfoEntity where groupId=?";
		List<ApiInfoEntity> apiInfoList = systemService.findHql(hql, groupId);
		return apiInfoList;
	}

    @Override
    public void startMQListener(ApiInfoEntity apiInfo) {
		CriteriaQuery cq = new CriteriaQuery(ApiInfoEntity.class);
		cq.eq("apiClassName",apiInfo.getApiClassName());
		cq.eq("pubMode",apiInfo.getPubMode());
		cq.notEq("id",apiInfo.getId());
		cq.add();
        List<Object> list = systemService.getListByCriteriaQuery(cq, false);
        if (list==null || (list != null && list.size() == 0)) {
            //将信息存入redis
            RtdbCommon.saveTopic();
            //启动监听
            openplatformMqApi.receive();
        }
    }

    @Override
	public List<ApiParamEntity> getByAPiAndType(String apiId, Integer type) {
		String hql = "from ApiParamEntity where apiId=:apiId and paramType=:paramType";
		Query query = getSession().createQuery(hql);
		query.setParameter("apiId", apiId);
		query.setParameter("paramType", type);
		return query.list();
	}

	private void dealSheet(Sheet sheet) throws Exception {
		if (sheet != null) {
			List<List<String>> data = ExcelReaderUtil.readSheet(sheet);
			batchInsert(data, sheet.getSheetName());
		}
	}


	private int[] batchInsert(List<List<String>> data, String tableName) throws Exception {

		if (data == null || data.size() <= 0)
			return null;

		// 准备SQL插入语句
		StringBuilder sql = new StringBuilder(CustomConstant.INSERT_OPER);

		// 填充value中的占位符
		StringBuilder sqlValues = new StringBuilder(CustomConstant.VALUES);

		// 拼接表名和左括号
		sql.append(tableName + CustomConstant.LEFT_BRACKET);

		List<String> colList = data.get(0);
		//取第一行数据时 字段名
		for (int i = 0; i < colList.size(); i++) {
			if (i == colList.size() - 1) {
				//组装字段名
				sql.append(colList.get(i));
				//组装占位符
				sqlValues.append(CustomConstant.PLACEHOLDE);
			} else {
				sql.append(colList.get(i) + CustomConstant.SPLIT);
				sqlValues.append(CustomConstant.PLACEHOLDE + CustomConstant.SPLIT);
			}
		}

		// 添加右括号
		sql.append(CustomConstant.RIGHT_BRACKET + sqlValues + CustomConstant.RIGHT_BRACKET);

		//去掉第一行
		data.remove(0);
		int[] insertRes = jdbcTemplate.batchUpdate(sql.toString(), new MyBatchPreparedStatementSetter(data));

		return insertRes;
	}


	private class MyBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

		final List<List<String>> list;

		public MyBatchPreparedStatementSetter(List<List<String>> list) {
			this.list = list;
		}

		@Override
		public int getBatchSize() {
			return list.size();
		}

		@Override
		public void setValues(PreparedStatement ps, int index) throws SQLException {
			List<String> singleData = list.get(index);
			for (int i = 0; i < singleData.size(); i++) {
				ps.setString(i + 1, StringUtil.isEmpty(singleData.get(i)) ? null : singleData.get(i));
			}
		}

	}
}