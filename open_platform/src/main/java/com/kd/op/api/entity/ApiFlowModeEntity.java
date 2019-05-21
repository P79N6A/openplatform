package com.kd.op.api.entity;

import java.lang.Integer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;


@Entity
@Table(name = "api_flow_mode", catalog = "")
public class ApiFlowModeEntity implements java.io.Serializable{

    private java.lang.String id;
    /**应用名称*/
    @Excel(name="应用名称",width=15)
    private Integer type;
    /**应用ID*/
    @Excel(name="应用ID",width=15)
    private Integer maxNum;
    /**应用描述*/
    @Excel(name="应用描述",width=15)
    private Integer unit;


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

    @Column(name ="TYPE",nullable=true,length=100)
    public Integer getType(){
        return this.type;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用名称
     */
    public void setType(Integer type){
        this.type = type;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用ID
     */

    @Column(name ="MAX_NUM",nullable=true,length=32)
    public Integer getMaxNum(){
        return this.maxNum;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用ID
     */
    public void setMaxNum(Integer maxNum){
        this.maxNum = maxNum;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  应用描述
     */

    @Column(name ="UNIT",nullable=true,length=200)
    public Integer getUnit(){
        return this.unit;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  应用描述
     */
    public void setUnit(Integer unit){
        this.unit = unit;
    }

}
