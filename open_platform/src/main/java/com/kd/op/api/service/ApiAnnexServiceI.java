package com.kd.op.api.service;
import com.kd.op.api.entity.ApiAnnexEntity;
import org.jeecgframework.core.common.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

public interface ApiAnnexServiceI extends CommonService{

    //多文件上传
   // public void doUpload(ApiAnnexEntity apiAnnex, MultipartFile[] files)throws Exception;

    //删除
  //  public void delAnnex(ApiAnnexEntity apiAnnex)throws Exception;

    //提交审核
    public void submitAudit(ApiAnnexEntity apiAnnex)throws Exception;

    //执行审核
    public void doAudit(ApiAnnexEntity apiAnnex)throws Exception;

}
