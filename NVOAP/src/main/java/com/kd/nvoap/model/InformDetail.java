package com.kd.nvoap.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 通知信息表
 * @author onlineGenerator
 * @date 2018-09-30 16:43:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "inform_detail", schema = "")
@SuppressWarnings("serial")
public class InformDetail implements java.io.Serializable {
	/**id*/
	private String id;
	/**通知分组Id*/
    @Excel(name="通知分组Id",width=15)
    private String groupId;
	/**通知标题*/
	@Excel(name="通知标题",width=15)
	private String informTitle;
    /**通知内容*/
    @Excel(name="通知内容",width=15)
    private String informContent;

    /**创建人名称*/
    private String createName;
    /**创建人登录名称*/
    private String createBy;
    /**创建日期*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createDate;
    /**更新人名称*/
    private String updateName;
    /**更新人登录名称*/
    private String updateBy;
    /**更新日期*/
    private Date updateDate;


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  id
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
     *@param: java.lang.String  id
     */
    public void setId(java.lang.String id){
        this.id = id;
    }


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知分组Id
	 */

	@Column(name ="GROUP_ID",nullable=true,length=30)
	public String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知分组Id
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  通知标题
     */

    @Column(name ="INFORM_TITLE",nullable=true,length=30)
    public String getInformTitle(){
        return this.informTitle;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  通知标题
     */
    public void setInformTitle(String informTitle){
        this.informTitle = informTitle;
    }


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  通知内容
     */

    @Column(name ="INFORM_CONTENT",nullable=true,length=30)
    public String getInformContent(){ return this.informContent; }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  通知内容
     */
    public void setInformContent(String informContent){
        this.informContent = informContent;
    }


	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}


}
