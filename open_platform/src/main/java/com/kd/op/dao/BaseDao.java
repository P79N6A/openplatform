package com.kd.op.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.kd.op.common.HibernateHandler;
import com.kd.op.common.Pagination;

public interface BaseDao<T> {

    /**
     * 保存实体
     * 
     * @param entity
     *            实体对象
     * @return 实体主键
     */
    Object save(Object entity);

    /**
     * 
     * 删除实体
     * 
     * @param entity
     *            实体对象
     * 
     */
    void delete(Object entity);

    /**
     * 
     * 更新实体
     * 
     * @param entity
     *            实体对象
     * 
     */
    void update(Object entity);

    /**
     * 
     * 保存或更新实体, 实体没有主键时保存，否则更新
     * 
     * @param entity
     *            实体对象
     * 
     */
    void saveOrUpdate(Object entity);

    /**
     * 
     * 批量保存实体
     * 
     * @param entities
     *            实体集合
     */
    void saveAll(Collection<?> entities);

    /**
     * 
     * 批量删除实体
     * 
     * @param entities
     *            实体集合
     * 
     */
    void deleteAll(Collection<?> entities);

    /**
     * 
     * 批量更新实体
     * 
     * @param entities
     *            实体集合
     * 
     */
    void updateAll(Collection<?> entity);

    /**
     * 
     * 批量保存或更新实体, 实体没有主键时保存，否则更新
     * 
     * @param entity
     *            实体集合
     * 
     */
    void saveOrUpdateAll(Collection<?> entities);

    /**
     * 
     * 获取单个实体，根据实体类及实体的主键获取。
     * 
     * @param entityClass
     *            实体类
     * @param id
     *            实体主键
     * @return 实体对象
     */
    @SuppressWarnings("hiding")
    <T> T get(Class<T> entityClass, Serializable id);

    /**
     * 获取单个实体，根据查询语句及参数获取。
     * 
     * @param queryString
     *            查询语句
     * @param params
     *            可选的查询参数
     * @return 单个实体，如果查询结果有多个，则返回第一个实体
     */
    @SuppressWarnings("hiding")
    <T> T get(CharSequence queryString, Map<String, Object> params);

    /**
     * 获取单个实体，根据查询语句及参数获取。
     * 
     * @param queryString
     *            查询语句
     * @param params
     *            可选的查询参数
     * @return 单个实体，如果查询结果有多个，则返回第一个实体
     */
    @SuppressWarnings("hiding")
    <T> T get(CharSequence queryString, Object... params);

    /**
     * 
     * 查询实体列表
     * 
     * @param queryString
     *            查询语句
     * @param params
     *            可选的查询参数
     * @return 实体列表
     */
    @SuppressWarnings("hiding")
    <T> List<T> findListByHql(CharSequence queryString, Object... params);
    
    /**
     * 根据sql查询实体类
     * @param queryString
     * @param params
     * @return
     */
    <T> List<T> findListBySql(CharSequence queryString, Object... params);

    /**
     * 
     * 查询实体列表
     * 
     * @param queryString
     *            查询语句
     * @param params
     *            可选的查询参数
     * @return 实体列表
     */
    @SuppressWarnings("hiding")
    <T> List<T> findListByMap(CharSequence queryString, Map<String, Object> params);

    /**
     * 分页查询实体
     * 
     * @param queryString
     *            查询语句
     * @param pageIndex
     *            当前页码，如果pageIndex<1则不分页，且返回pageSize条记录。
     * @param pageSize
     *            每页记录数，如果pageSize<1则返回所有记录。
     * @param params
     *            可选的查询参数
     * @return 实体分页对象
     */
    @SuppressWarnings("hiding")
    <T> Pagination<T> findPagination(CharSequence queryString, int pageIndex, int pageSize, Object... params);

    /**
     * 分页查询实体
     * 
     * @param queryString
     *            查询语句
     * @param params
     *            可选的查询参数
     * @param pageIndex
     *            当前页码，如果pageIndex<2则不分页，且返回pageSize条记录。
     * @param pageSize
     *            每页记录数，如果pageSize<1则返回所有记录。
     * 
     * @return 实体分页对象
     */
    @SuppressWarnings("hiding")
    <T> Pagination<T> findPagination(CharSequence queryString, Map<String, Object> params, int pageIndex, int pageSize);

    /**
     * 分页查询实体，自定义总条数查询语句，适合复杂的hql分页查询
     * 
     * @param queryString
     *            查询语句
     * @param countString
     *            查询记录总条数语句
     * @param pageIndex
     *            当前页码，如果pageIndex<1则不分页，且返回pageSize条记录。
     * @param pageSize
     *            每页记录数，如果pageSize<1则返回所有记录。
     * @param params
     *            可选的查询参数
     * @return 实体分页对象
     */
    @SuppressWarnings("hiding")
    <T> Pagination<T> findPagination(CharSequence queryString, CharSequence countString, int pageIndex, int pageSize,
            Object... params);

    /**
     * 分页查询实体，自定义总条数查询语句，适合复杂的hql分页查询
     * 
     * @param queryString
     *            查询语句
     * @param countString
     *            查询记录总条数语句
     * @param params
     *            可选的查询参数
     * @param pageIndex
     *            当前页码，如果pageIndex<2则不分页，且返回pageSize条记录。
     * @param pageSize
     *            每页记录数，如果pageSize<1则返回所有记录。
     * 
     * @return 实体分页对象
     */
    @SuppressWarnings("hiding")
    <T> Pagination<T> findPagination(CharSequence queryString, CharSequence countString, Map<String, Object> params,
            int pageIndex, int pageSize);

    /**
     * 分页查询实体，自定义总条数查询语句，适合复杂的sql分页查询
     * 
     * @param queryString
     *            查询语句
     * @param countString
     *            查询记录总条数语句
     * @param params
     *            可选的查询参数
     * @param pageIndex
     *            当前页码，如果pageIndex<2则不分页，且返回pageSize条记录。
     * @param pageSize
     *            每页记录数，如果pageSize<1则返回所有记录。
     * 
     * @return 实体分页对象
     */
    @SuppressWarnings("hiding")
    public <T> Pagination<T> findSqlPagination(final CharSequence queryString, final CharSequence countString,
            final Map<String, Object> params, int pageIndex, int pageSize);

    /**
     * 执行数据库更新操作
     * 
     * @deprecated 用{@link #executeUpdate(String)}替换
     * @param hql
     */
    void execute(String hql);

    /**
     * 执行数据库更新操作
     * 
     * @deprecated 用{@link #executeUpdate(HibernateHandler)}替换
     * @param hql
     */
    void execute(HibernateHandler handler);

    /**
     * 执行数据库更新操作
     * 
     * @deprecated 用{@link #executeSqlUpdate(String)}替换
     * @param sql
     */
    void executeSql(String sql);

    /**
     * 执行数据库查询操作
     * 
     * @param handler
     *            处理器
     * @return
     * @throws Exception
     */
    Object executeQuery(HibernateHandler handler);

    /**
     * 执行数据库更新操作
     * 
     * @param sql
     * @return 更新的记录条数
     */
    int executeSqlUpdate(String sql);

    /**
     * 执行数据库更新操作
     * 
     * @param hql
     * @return 更新的记录条数
     */
    int executeUpdate(String hql);

    /**
     * 执行数据库更新操作
     * 
     * @param handler
     *            处理器
     * @return
     * @throws Exception
     */
    Object executeUpdate(HibernateHandler handler);

    public T getById(Serializable id);

    public T saveEntity(T o);

    public T insert(T o);

    public void save(List<T> list);

    public void insert(List<T> list);

    public void delete(List<T> list);

    public void update(List<T> list);

    public List<T> findByProperty(String name, Object value);
    public List<T> findByUserProperty(String name, Object value);

    public List<T> findByProperty(Map<String, Object> conditionMap);

    /**
     * 
     * 查询实体列表
     * 
     * @param queryString
     *            查询语句
     * @param maxResults
     *            列表最大数
     * @param params
     *            可选的查询参数
     * @return 实体列表
     */
    public <V> List<V> findListByMax(CharSequence queryString, int maxResults, Object... params);

    /**
     * 
     * 查询实体列表
     * 
     * @param queryString
     *            查询语句
     * @param maxResults
     *            列表最大数
     * @param params
     *            可选的查询参数
     * @return 实体列表
     */
    public <V> List<V> findListByMax(CharSequence queryString, int maxResults, Map<String, Object> params);

    /* 根据sql更新
    *
    * @param sql
    * @return
    */
   public int updateBySqlString(String sql);
   
   /**
    * 根据sql查找List
    *
    * @param <T>
    * @param query
    * @return
    */
   public <T> List<T> findListbySql(String query);
   
   /**
    * 通过hql 查询语句查找对象
    *
    * @param <T>
    * @param hql
    * @param param
    * @return
    */
   public <T> List<T> findHql(String hql, Object... param);
   
   /**
    * 比较两个对象具体的属性
    */
   public Map<String,Object> compare(T obj1, T Obj2);
   
   public void compareUpdate(T o);
}