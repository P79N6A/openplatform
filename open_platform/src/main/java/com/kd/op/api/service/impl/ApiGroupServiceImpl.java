package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.entity.ApiGroupEntity;
import com.kd.op.api.service.ApiGroupServiceI;
import org.hibernate.Query;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("apiGroupService")
@Transactional
public class ApiGroupServiceImpl extends CommonServiceImpl implements ApiGroupServiceI {


    @Autowired
    private SystemService systemService;

    public void delete(ApiGroupEntity entity) throws Exception {
        super.delete(entity);
    }

    public void save(ApiGroupEntity entity) throws Exception {
        systemService.save(entity);
    }

    public void saveOrUpdate(ApiGroupEntity entity) throws Exception {
        super.saveOrUpdate(entity);
    }

    @Override
    public JSONArray loadAllGroup(ApiGroupEntity apiGroup) throws Exception {
        JSONArray result = new JSONArray();
        JSONObject rootNode = new JSONObject();
        rootNode.put("id", "0");
        rootNode.put("name", "能力中心");
        rootNode.put("pId", null);
        rootNode.put("open", true);

        result.add(rootNode);
        List<ApiGroupEntity> apiGroupEntities = null;
        if (StringUtil.isEmpty(apiGroup.getId())) {
            //分appType 0 1 进行查询
            String hql = "from ApiGroupEntity  order by createDate asc";
            Query query = getSession().createQuery(hql);
            apiGroupEntities = query.list();
        } else {
            apiGroupEntities = findByProperty(ApiGroupEntity.class, "parentId", apiGroup.getId());
        }
        apiGroupEntities.forEach(p -> {
            JSONObject node = new JSONObject();
            node.put("id", p.getId());
            node.put("name", p.getGroupName());
            node.put("pId", p.getParentId() == null || p.getParentId().length()==0 ? "0" : p.getParentId());
            if(result.size() == 1){
                node.put("state.selected", true);
            }else{
                node.put("state.selected", false);
            }
            result.add(node);
        });
        return result;
    }

}