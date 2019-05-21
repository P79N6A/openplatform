package com.kd.op.util;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import java.util.Iterator;


/**
 * @Auther:张健云
 * @Description：
 * @DATE：2018/12/19 8:35
 */
public class HibernateConfigurationHelper {

    private static Configuration hibernateConf;

    private static Configuration getHibernateConf() {
        if (hibernateConf == null) {
            return new Configuration();
        }
        return hibernateConf;
    }

    private static PersistentClass getPersistentClass(Class clazz) {
        synchronized (HibernateConfigurationHelper.class) {
            PersistentClass pc = getHibernateConf().getClassMapping(
                    clazz.getName());
            if (pc == null) {
                hibernateConf = getHibernateConf().addClass(clazz);
                pc = getHibernateConf().getClassMapping(clazz.getName());

            }
            return pc;
        }
    }

    /**
     * 功能描述：获取实体对应的表名
     *
     * @param clazz 实体类
     * @return 表名
     */
    public static String getTableName(Class clazz) {
        return getPersistentClass(clazz).getTable().getName();
    }

    /**
     * 功能描述：获取实体对应表的主键字段名称
     *
     * @param clazz 实体类
     * @return 主键字段名称
     */
    public static String getPkColumnName(Class clazz) {

        return getPersistentClass(clazz).getTable().getPrimaryKey()
                .getColumn(0).getName();

    }

    /**
     * 功能描述：通过实体类和属性，获取实体类属性对应的表字段名称
     *
     * @param clazz        实体类
     * @param propertyName 属性名称
     * @return 字段名称
     */
    public static String getColumnName(Class clazz, String propertyName) {
        PersistentClass persistentClass = getPersistentClass(clazz);
        Property property = persistentClass.getProperty(propertyName);
        Iterator it = property.getColumnIterator();
        if (it.hasNext()) {
            Column column = (Column) it.next();
            return column.getName();
        }
        return null;
    }

    /**
     * 功能描述：通过实体类和属性，获取实体类属性对应的表字段类型
     *
     * @param clazz        实体类
     * @param propertyName 属性名称
     * @return 字段名称
     */
    public static String getColumnType(Class clazz, String propertyName) {
        PersistentClass persistentClass = getPersistentClass(clazz);
        Property property = persistentClass.getProperty(propertyName);
        String type = property.getType().getName();
        if (type.equals("timestamp")) {
            return "java.sql." + type.substring(0, 1).toUpperCase() + type.substring(1);
        } else {
            return "java.lang." + type.substring(0, 1).toUpperCase() + type.substring(1);
        }

    }

    /**
     * 获得实体类的主键属性名称，如果没有返回null
     *
     * @param clazz 实体类的class对象
     * @return 实体类主键属性名称
     */
    public static String getPrimaryKeyName(Class clazz) {
        PersistentClass persistentClass = getPersistentClass(clazz);
        Property property = persistentClass.getIdentifierProperty();
        if (property != null) {
            return property.getName();
        }
        return null;
    }

}
