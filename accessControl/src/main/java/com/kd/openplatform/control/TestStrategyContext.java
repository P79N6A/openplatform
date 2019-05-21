package com.kd.openplatform.control;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Title 测试时持有策略上下文
 * @author shinerio
 * @Description
 */
public class TestStrategyContext {
    public List<AccessStrategyI> testStrategies;

    public List<AccessStrategyI> getTestStrategies() {
        return testStrategies;
    }

    public void setTestStrategies(List<AccessStrategyI> testStrategies) {
        this.testStrategies = testStrategies;
    }

    public void addStrategy(AccessStrategyI strategy) {
        this.testStrategies.add(strategy);
    }

    public void removeStrategy(AccessStrategyI strategy) {
        this.testStrategies.remove(strategy);
    }

    //控制测试策略 status=1为around方法 status=2为after方法
    public String strategyControl(JSONObject hsfData) {
        JSONObject resultMsg = new JSONObject();
        resultMsg.put("status",500);
        if (Integer.parseInt(hsfData.get("status").toString()) == 1) {
            try {
                aroundAccess(hsfData);
            } catch (Throwable throwable) {
                resultMsg.put("info",throwable.getMessage());
                return resultMsg.toJSONString();
            }
            resultMsg.put("status",200);
            resultMsg.put("info","ok");
            return resultMsg.toJSONString();
        }else if (Integer.parseInt(hsfData.get("status").toString()) == 2){
            afterAccess(hsfData);
            resultMsg.put("status",200);
            resultMsg.put("info","ok");
            return resultMsg.toJSONString();
        }
        return resultMsg.toJSONString();
    }


    public void beforeAccess(String args) {
        for (AccessStrategyI strategy : testStrategies) {
            strategy.before(args);
        }
    }

    public void aroundAccess(JSONObject object) throws Throwable {
        for (AccessStrategyI strategy : testStrategies) {
            strategy.around(object);
        }
    }

    public void afterAccess(JSONObject object) {
        for (AccessStrategyI strategy : testStrategies) {
            strategy.after(object);
        }
    }

    public void afterReturningAccess(String args) {
        for (AccessStrategyI strategy : testStrategies) {
            strategy.afterReturning(args);
        }
    }

    public void afterThrowing(String args) {
        for (AccessStrategyI strategy : testStrategies) {
            strategy.afterThrowing(args);
        }
    }

}
