package com.xcz.afcs.mybatis.model;

import com.xcz.afcs.core.model.Pagination;
import com.xcz.afcs.mybatis.util.EntityUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/8/13.
 */

public class EntityCriteria implements Serializable{

    private static final Log logger = LogFactory.getLog(EntityCriteria.class);

    @Getter
    private List<Expression> expressionList = new ArrayList();

    @Getter
    private List<Order> orderList = new ArrayList();

    @Getter
    private List<Join> joinList = new ArrayList<>();

    @Getter
    @Setter
    private Pagination pagination;

    private EntityModel model;

    @Getter
    private Class<?> entityClass;

    private Integer paramExt = 1;

    @Getter
    @Setter
    private QueryType queryType;

    @Getter
    @Setter
    private List<String> selectColumns = new ArrayList<String>();

    @Getter
    @Setter
    private Class<?> resultViewCls;

    @Getter
    @Setter
    private String tableName;

    @Getter
    private Map<String, Object> params = new LinkedHashMap<String, Object>();

    public EntityCriteria(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.queryType = QueryType.MULTI;
        model = EntityUtil.parseEntity(entityClass);
    }

    public void add(Expression expression) {
        if (expression.getPropertyName().contains(".")) {
            expression.setCloumnName(expression.getPropertyName());
            expression.setParamName(expression.getPropertyName().replace(".", "_")+"_"+paramExt);
        }else{
            EntityField entityField = model.getEntityFieldByName(expression.getPropertyName());
            if (entityField == null) {
                logger.warn(expression.getPropertyName() + "在实体Bean " + entityClass.getSimpleName() + "中未定义");
                expression.setCloumnName(expression.getPropertyName());
            }else {
                expression.setCloumnName(entityField.getCloumnName());
            }
            expression.setParamName(expression.getPropertyName()+"_"+paramExt);
        }
        params.put(expression.getParamName(), expression.getValue());
        paramExt ++;
        this.expressionList.add(expression);
    }

    public void add(Order order) {
        if (order.getPropertyName().contains(".")) {
            order.setCloumnName(order.getPropertyName());
        }
        else {
            EntityField entityField = model.getEntityFieldByName(order.getPropertyName());
            if (entityField == null) {
                logger.warn(order.getPropertyName() + "在实体Bean " + entityClass.getSimpleName() + "中未定义");
                order.setCloumnName(order.getPropertyName());
            } else {
                order.setCloumnName(entityField.getCloumnName());
            }
        }
        this.orderList.add(order);
    }

    public void add(Join join) {
        joinList.add(join);
    }

    public enum QueryType {
         ONE,
         MULTI
    }

}
