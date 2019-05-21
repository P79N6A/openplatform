package com.kd.openplatform.resource.serivce.impl;

import com.kd.openplatform.common.entity.ApiAppRelaEntity;
import com.kd.openplatform.common.service.CommonAccessService;
import com.kd.openplatform.resource.serivce.ResourceServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * create by shinerio
 * 2018-11-23 13:19
 */
@Service("resourceService")
public class ResourceServiceImpl extends CommonAccessService implements ResourceServiceI {

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean isResourceValid(String id, String resourceId) {
        ApiAppRelaEntity apiAppRelaEntity =
                (ApiAppRelaEntity) commonAccessDao.queryUniqueByProperty(ApiAppRelaEntity.class, "id", id);
        String resources = apiAppRelaEntity.getResourceId();
        if (resources != null) {
            List<String> resourceArray = Arrays.asList(resources.split(","));
            if (resourceArray.contains(resourceId)) {
                return true;
            }
        }
        return false;
    }
}
