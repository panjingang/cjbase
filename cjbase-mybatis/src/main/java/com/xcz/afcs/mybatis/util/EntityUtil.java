package com.xcz.afcs.mybatis.util;

import com.xcz.afcs.mybatis.annonation.Column;
import com.xcz.afcs.mybatis.annonation.Table;
import com.xcz.afcs.mybatis.entity.UpdatableEntity;
import com.xcz.afcs.mybatis.model.EntityField;
import com.xcz.afcs.mybatis.model.EntityModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mac on 2017/8/12.
 */
public class EntityUtil {

     private static final Log logger = LogFactory.getLog(EntityUtil.class);

     private static Map<String, EntityModel> modelCache = new ConcurrentHashMap<String, EntityModel>();

     public static <T> EntityModel parseEntity(Class<T> entityCls) {
         if (!UpdatableEntity.class.isAssignableFrom(entityCls)) {
             throw new RuntimeException(entityCls.getName()+"必须继承UpdatableEntity");
         }
         Table table = entityCls.getAnnotation(Table.class);
         if (table == null) {
             throw new RuntimeException(entityCls.getName()+"不是一个实体类，请确认@Table注解是否存在");
         }
         EntityModel model = modelCache.get(entityCls.getName());
         if (model != null) {
             return model;
         }
         model = new EntityModel();
         String tableName = table.name();
         model.setTableName(tableName);
         parseSuperEntity(entityCls, model);
         if (model.getPrimaryField() == null) {
             throw new RuntimeException(entityCls.getName()+"请设置唯一主键");
         }
         modelCache.put(entityCls.getName(), model);
         return model;
     }

     private static void parseSuperEntity(Class cls,  EntityModel model) {
         if (cls == Object.class) {
             return;
         }
         try {
             Field[] fields = cls.getDeclaredFields();
             for (Field field : fields) {
                 String fieldName = field.getName();
                 Column column = field.getAnnotation(Column.class);
                 if (column != null) {
                     PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
                     EntityField entityField = new EntityField();
                     entityField.setFieldName(fieldName);
                     entityField.setField(field);
                     entityField.setGetMethod(pd.getReadMethod());
                     entityField.setSetMethod(pd.getWriteMethod());
                     entityField.setCloumnName(StringUtils.isBlank(column.name()) ? fieldName : column.name());
                     if (column.primary()) {
                         model.putPrimary(entityField);
                     } else {
                         model.put(entityField);
                     }
                 }
             }
         }catch (Exception e) {
            throw new RuntimeException("解析实体Bean出错");
         }
         parseSuperEntity(cls.getSuperclass(), model);
     }



}
