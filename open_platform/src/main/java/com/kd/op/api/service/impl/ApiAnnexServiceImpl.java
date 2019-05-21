package com.kd.op.api.service.impl;

import com.kd.op.api.entity.ApiAnnexEntity;
import com.kd.op.api.service.ApiAnnexServiceI;
import com.kd.op.common.CustomConstant;
import com.kd.op.util.FileSizeTrans;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("apiAnnexService")
@Transactional
public class ApiAnnexServiceImpl extends CommonServiceImpl implements ApiAnnexServiceI {

    @Autowired
    private SystemService systemService;

   /* @Override
    public void doUpload(ApiAnnexEntity apiAnnex,MultipartFile[] files) throws Exception {
        if (files != null && files.length > 0) {
            List<ApiAnnexEntity> apiAnnexEntities = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                String path = ResourceUtil.getConfigByName(CustomConstant.ANNEX_PATH_WINDOWS);
                //获取操作系统名称
                String os = System.getProperty("os.name").toLowerCase();
                if(os.indexOf("linux")>0){
                    path = ResourceUtil.getConfigByName(CustomConstant.ANNEX_PATH_LINUX);
                }
                String originalName = "";
                String annexName = "";
                String annexSuffix = "";
                Long annexSize = 0L;
                String uniqueName = "";
                if(!new File(path).exists()){
                    new File(path).mkdirs();
                }
                if (!files[i].isEmpty()) {
                    //获取文件信息
                    originalName = files[i].getOriginalFilename();
                    annexName = originalName.substring(0,originalName.lastIndexOf("."));
                    annexSuffix = originalName.substring(originalName.lastIndexOf(".")); //获取后缀名
                    annexSize = FileSizeTrans.transByteToKB(files[i].getSize());
                    uniqueName=new Date().getTime() + annexSuffix;
                    File file2 = new File(path,uniqueName); //新建一个文件
                    files[i].transferTo(file2);

                    //保存实体
                    ApiAnnexEntity annex = new ApiAnnexEntity();
                    MyBeanUtils.copyBeanNotNull2Bean(apiAnnex,annex);
                    annex.setAuditStatus(CustomConstant.AUDIT_TEMP);
                    annex.setAnnexStatus(CustomConstant.PUBLIS_TEMP);
                    annex.setAnnexName(annexName);
                    annex.setUniqueName(uniqueName);
                    annex.setAnnexSize(annexSize);
                    annex.setAnnexPath(path);
                    annex.setAnnexSuffix(annexSuffix);
                    apiAnnexEntities.add(annex);
                }

            }
            this.batchSave(apiAnnexEntities);
            apiAnnexEntities.forEach(p->{
                systemService.addLog("上传组件["+p.getId()+"]", Globals.MODULE_ANNEX,Globals.Log_Leavel_INFO,Globals.SUCCESS);
            });
        } else {
            throw new Exception();
        }
    }*/

  /*  @Override
    public void delAnnex(ApiAnnexEntity apiAnnex) throws Exception {
        apiAnnex = this.get(ApiAnnexEntity.class,apiAnnex.getId());
        //移除文件
        File file = new File(apiAnnex.getAnnexPath(),apiAnnex.getUniqueName());
        file.delete();
        this.delete(apiAnnex);
    }*/

    @Override
    public void submitAudit(ApiAnnexEntity apiAnnex) throws Exception {
        apiAnnex = this.get(ApiAnnexEntity.class,apiAnnex.getId());
        apiAnnex.setAuditStatus(CustomConstant.AUDIT_WAIT);
        this.saveOrUpdate(apiAnnex);
    }

    @Override
    public void doAudit(ApiAnnexEntity apiAnnex) throws Exception {
        ApiAnnexEntity apiAnnexOld = this.get(ApiAnnexEntity.class,apiAnnex.getId());
        MyBeanUtils.copyBeanNotNull2Bean(apiAnnex,apiAnnexOld);
        this.saveOrUpdate(apiAnnexOld);
    }
}