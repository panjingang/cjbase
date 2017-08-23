package com.xcz.afcs.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/8/12.
 */
@Getter
@Setter
public class EntityModel {

    private String tableName;

    private EntityField primaryField;

    //不包含主键
    private List<EntityField> fieldList = new ArrayList();

    //全部字段
    private Map<String, EntityField> fieldMap = new HashMap();


    public void put(EntityField entityField) {
        this.fieldList.add(entityField);
        this.fieldMap.put(entityField.getFieldName(), entityField);
    }

    public void putPrimary(EntityField entityField) {
        this.primaryField = entityField;
        this.fieldMap.put(entityField.getFieldName(), entityField);
    }


    public EntityField getEntityFieldByName(String fieldName) {
        return fieldMap.get(fieldName);
    }

}
