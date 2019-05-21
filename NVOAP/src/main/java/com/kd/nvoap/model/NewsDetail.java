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
public class NewsDetail implements java.io.Serializable {
	/**id*/
	private String id;

	/**通知标题*/
	@Excel(name="新闻标题",width=15)
	private String newsTitle;

    /**通知内容*/
    @Excel(name="新闻摘要",width=15)
    private String newsAbstract;

    /**通知内容*/
    @Excel(name="新闻内容",width=15)
    private String newsContent;

    /**创建日期*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  id
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
     *@param: java.lang.String  id
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  新闻标题
     */

    @Column(name ="NEWS_TITLE",nullable=true,length=30)
    public String getNewsTitle(){
        return this.newsTitle;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  新闻标题
     */
    public void setNewsTitle(String newsTitle){
        this.newsTitle = newsTitle;
    }


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  新闻摘要
     */

    @Column(name ="NEWS_ABSTRACT",nullable=true,length=30)
    public String getNewsAbstract(){ return this.newsAbstract; }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  新闻摘要
     */
    public void setNewsAbstract(String newsAbstract){
        this.newsAbstract = newsAbstract;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  新闻内容
     */

    @Column(name ="NEWS_CONTENT",nullable=true,length=30)
    public String getNewsContent(){ return this.newsContent; }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  新闻内容
     */
    public void setNewsContent(String newsContent){
        this.newsContent = newsContent;
    }


	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}


}
