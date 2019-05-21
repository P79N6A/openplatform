package com.kd.op.common;

import com.kd.op.api.entity.*;

/**
 * @Auther:张健云
 * @Description：自定义常量类 通用常量定义
 * 变量名全部大写,长度过长是用下划线分割
 * @DATE：2018/11/23 10:22
 */
public class CustomConstant {

    //type 类型
    public static final String API = "api";
    public static final String SCENE = "scene";
    public static final String Order = "order";
    //是否进行资源控制
    public static final Integer IS_NOT_RESOURCE = 0;
    public static final Integer IS_RESOURCE = 1;


    //level类型
    //从发布者请求
    public static final String LEVEL_PUB = "1";
    //从订阅者请求
    public static final String LEVEL_ORDER = "2";

    //审核状态
    //暂存
    public static final Integer AUDIT_TEMP = 0;
    //待审核
    public static final Integer AUDIT_WAIT = 1;
    //通过
    public static final Integer AUDIT_PASS = 2;
    //审核失败
    public static final Integer AUDIT_FAILD = 3;

    //支付状态
    //未支付
    public static final Integer PAY_UNPAY = 0;
    //已支付
    public static final Integer PAY_HASPAY = 1;
    //支付失败
    public static final Integer PAY_FAIL = 2;
    //退款中
    public static final Integer PAY_BACK = 3;

    //列表 类型
    public static final String LIST_LIST = "list";
    //订单管理
    public static final String LIST_ORDER = "order";
    //订单记录
    public static final String LIST_RECORD = "record";
    //订单审核
    public static final String LIST_AUDIT = "audit";
    //资源控制
    public static final String LIST_RESOURCE = "resource";
    //导出文件名
    public static final String EXPORT_FILENAME = "服务列表";
    public static final String EXPORT_FILENAME_SUFFIX = ".xls";
    //需要导出的全部实体列表 此处第一个必须为ApiInfoEntity 否则后面的关联表中的数据可能找不到
    private static Class[] EXPORT_ENTITiES = null;
    //需要导出的关联实体列表
    private static Class[] EXPORT_RELA_ENTITiES = null;
    //需要导出的非关联实体列表
    private static Class[] EXPORT_NORELA_ENTITiES = null;
    //查询当前使用的数据库名称
    public static final String queryCurrDbName = "select database() currDbName";
    //查询表字段SQL
    public static final String queryTableColumns = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.Columns " +
            " WHERE table_name = ? and table_schema = ? ";
    //实体类全名
    public static final String API_INFO_E = "com.kd.op.api.entity.ApiInfoEntity";

    public static final String INSERT_OPER = "REPLACE INTO ";

    public static final String VALUES = " VALUES ( ";
    public static final String LEFT_BRACKET = " (";
    public static final String RIGHT_BRACKET = ") ";
    public static final String SPLIT = ",";
    public static final String PLACEHOLDE = " ? ";


    //应用发布状态
    //未发布
    public static final Integer PUBLIS_TEMP = 0;
    //已发布
    public static final Integer PUBLIS_PASS = 1;
    //已作废
    public static final Integer PUBLIS_CANCELLATION = -1;

    //配置文件中文件上传路径的key
    public static final String ANNEX_PATH_WINDOWS = "annexPathWindows";
    public static final String ANNEX_PATH_LINUX = "annexPathLinux";

    //可用状态
    //不可用
    public static final Integer CANNOT_USE = 0;
    //可用
    public static final Integer CAN_USE = 1;

    //sql时间格式化格式
    public static final String DATE_FORMAT_DAY = "%Y-%m-%d";
    public static final String DATE_FORMAT_HOUR = "%Y-%m-%d %H";
    public static final String DATE_FORMAT_MONTH = "%Y-%m";
    public static final String DATE_FORMAT_YEAR = "%Y";

    /**
     * 表名称
     **/
    public static final String query_ApiInfo_Table = "  ,api_info ai ";
    public static final String query_ApiScene_Table = " ,api_scene apiscene ";
    //订购
    public static final String query_Order_Table = " FROM api_order ao,api_order_detail aod ";
    //调用
    public static final String query_Invoke_Table = " FROM api_invoke_log ail ";
    /**
     * 表关联
     **/
    //支持关联能力中心
    public static final String rela_ApiInfo_OrderDetail = " AND ai.id = aod.api_id ";
    //支持关联场景
    public static final String rela_ApiScene_OrderDetail = " AND apiscene.id = aod.scene_id ";
    //支持查能力中心及能力名称
    public static final String rela_ApiInfo_InvokeLog = " AND ai.id = ail.api_id ";
    //支持查场景名称
    public static final String rela_ApiScene_InvokeLog = " AND apiscene.id =  ail.scene_id ";
    /**
     * 要查询的字段
     **/
    public static final String query_ApiGroup = "  AND ai.group_id=:apiGroupId ";
    public static final String query_DataType = " AND ao.order_type =:dataType ";
    public static final String query_ApiId = " AND api_id=:apiId ";
    public static final String query_SceneId = " AND scene_id=:sceneId ";
    //查询创建人
    public static final String query_Api_CreateBy = " AND ai.create_by=:createBy ";
    public static final String query_Scene_CreateBy = " AND apiscene.create_by=:createBy ";
    /**
     * WHERE条件
     **/
    public static final String queryOrder_Public_Where =
            " WHERE ao.id = aod.order_id AND pay_status = 1 ";
    //querySceneInvoke公共部分
    public static final String queryInvoke_Scene_Where = " WHERE ail.scene_id IS NOT NULL  ";
    //queryApiInvoke公共部分
    public static final String queryInvoke_Api_Where = "WHERE ail.api_id IS NOT NULL AND ail.scene_id IS NULL  ";
    /**
     * Select字段
     **/
    public static final String select_Rank_Api = "SELECT COUNT(ai.id) rank,ai.id id, ai.api_name name ";
    public static final String select_Rank_Scene = "SELECT COUNT(apiscene.id) rank,apiscene.id id, apiscene.scene_name name ";
    //时间后缀
    public static final String TIME_SUFFIX = ":00";
    //默认提前天数
    public static final int ADVANCE_DAYS = -7;

    //图表类型
    public static final String CHART_TYPE_COUNT = "count";//统计
    public static final String CHART_TYPE_RANK = "rank";//排名
    //统计类型
    public static final String COUNT_TYPE_ORDER = "order";//订购
    public static final String COUNT_TYPE_INVOKE = "invoke";//调用

    //统计时间字段
    public static final String TIME_COL_ORDER = "pay_time";
    public static final String TIME_COL_INVOKE = "invoke_time";

    //是否过滤数据 只加载当前登录人的
    public static final String FILTER_DATA_TRUE = "true";
    public static final String FILTER_DATA_FALSE = "false";
    //keyName
    public static final String IS_FILTER = "isFilter";
    public static final String USERNAME = "userName";

    public static Class[] GETEXPORTENTITiES() {
        if (EXPORT_ENTITiES == null) {
            EXPORT_ENTITiES = new Class[]{ApiInfoEntity.class, ApiParamEntity.class, ApiGroupEntity.class, ApiChargeModeRela.class, ApiFlowModeRelaEntity.class, ApiChargeMode.class, ApiFlowModeEntity.class};
        }
        return EXPORT_ENTITiES;
    }

    public static Class[] GETEXPORTRELAENTITiES() {
        if (EXPORT_RELA_ENTITiES == null) {
            EXPORT_RELA_ENTITiES = new Class[]{ApiParamEntity.class, ApiChargeModeRela.class, ApiFlowModeRelaEntity.class};
        }
        return EXPORT_RELA_ENTITiES;
    }

    public static Class[] GETEXPORTNORELAENTITiES() {
        if (EXPORT_NORELA_ENTITiES == null) {
            EXPORT_NORELA_ENTITiES = new Class[]{ApiGroupEntity.class, ApiChargeMode.class, ApiFlowModeEntity.class};
        }
        return EXPORT_NORELA_ENTITiES;
    }


}