package com.kd.op.api.entity;

import java.util.List;

public class GraphNode {

    //name保持唯一，用户为user_userId,应用为app_appId,能力中心为group_appId_groupId,服务为api_appId_groupId_apiId
    private String name;

    private Integer value;

    //为上一级的name，用户节点为null
    private String target;

    private String alias;

    private String temp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public GraphNode(String name, Integer value, String target, String alias) {
        this.name = name;
        this.value = value;
        this.target = target;
        this.alias = alias;
    }

    public boolean isContains(List<GraphNode> nodes){
        for(GraphNode node:nodes){
            if(node.getName().equals(this.name) && node.getTarget().equals(this.target)){
//                if(this.temp != null && node.getTemp() != null){
//                    if(this.temp.equals(node.getTemp())){
//                        return true;
//                    }
//                }else if(this.temp == null && node.getTemp() == null){
//                    return true;
//                }
                return true;
            }
        }
        return false;
    }
}
