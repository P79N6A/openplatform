package com.kd.service;

import javax.jws.WebService;

@WebService
public interface OpWebserviceI {
//    @WebMethod
    public String openService(String param);

    public String chargeArchives(String param);
//    @WebMethod
//    public String login(String param);
}
