package com.kd.openplatform.flow.entity;

import java.lang.Integer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
//import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "api_flow_mode_rela", catalog = "")
public class ApiFlowModeRelaEntity implements java.io.Serializable{

    private java.lang.String id;
    /**应用名称*/
    //@Excel(name="应用名称",width=15)
    private java.lang.String userId;
    /**应用ID*/
    //@Excel(name="应用ID",width=15)
    private java.lang.String apiId;
    /**应用描述*/
    //@Excel(name="应用描述",width=15)
    private java.lang.String flowCtrlModes;


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

    @Column(name ="ID",nullable=false,length=36)
    public java.lang.String getId(){
        return this.id;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  主键
     */
    public void setId(java.lang.String id){
        this.id = id;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用名称
     */

    @Column(name ="USER_ID",nullable=true,length=100)
    public java.lang.String getUserId(){
        return this.userId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用名称
     */
    public void setUserId(java.lang.String userId){
        this.userId = userId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用ID
     */

    @Column(name ="API_ID",nullable=true,length=32)
    public java.lang.String getApiId(){
        return this.apiId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用ID
     */
    public void setApiId(java.lang.String apiId){
        this.apiId = apiId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="FLOW_CTRL_MODES",nullable=true,length=200)
    public java.lang.String getFlowCtrlModes(){
        return this.flowCtrlModes;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setFlowCtrlModes(java.lang.String flowCtrlModes){
        this.flowCtrlModes = flowCtrlModes;
    }

}
