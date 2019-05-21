package com.kd.op.api.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

@Entity
@Table(name = "api_flow_mode_rela", catalog = "")
public class ApiFlowModeRelaEntity implements java.io.Serializable{

    private String id;
    /**应用名称*/
    @Excel(name="应用名称",width=15)
    private String userId;
    /**应用ID*/
    @Excel(name="应用ID",width=15)
    private String apiId;
    /**应用描述*/
    @Excel(name="应用描述",width=15)
    private String flowCtrlModes;


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name ="ID",nullable=false,length=36)
    public String getId(){
        return this.id;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  主键
     */
    public void setId(String id){
        this.id = id;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用名称
     */

    @Column(name ="USER_ID",nullable=true,length=100)
    public String getUserId(){
        return this.userId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用名称
     */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用ID
     */

    @Column(name ="API_ID",nullable=true,length=32)
    public String getApiId(){
        return this.apiId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用ID
     */
    public void setApiId(String apiId){
        this.apiId = apiId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="FLOW_CTRL_MODES",nullable=true,length=200)
    public String getFlowCtrlModes(){
        return this.flowCtrlModes;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setFlowCtrlModes(String flowCtrlModes){
        this.flowCtrlModes = flowCtrlModes;
    }

}
