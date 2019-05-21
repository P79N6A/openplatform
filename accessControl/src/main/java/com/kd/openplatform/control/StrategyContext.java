package com.kd.openplatform.control;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.common.exception.ControlException;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @Title 真正调用时持有策略上下文
 * @author shinerio
 * @Description
 */
public class StrategyContext {
	private static final Log log =  LogFactory.getLog(StrategyContext.class);
	
    public List<AccessStrategyI> strategies;

    public List<AccessStrategyI> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<AccessStrategyI> strategies) {
        this.strategies = strategies;
    }

    public void addStrategy(AccessStrategyI strategy) {
        this.strategies.add(strategy);
    }

    public void removeStrategy(AccessStrategyI strategy) {
        this.strategies.remove(strategy);
    }

    /**
     * 具体控制实现
     * @param hsfData 访问参数
     * @return JsonObject, code = 200成功，其他各种失败
     */
    public String strategyControl(JSONObject hsfData) {

        JSONObject resultMsg = new JSONObject();
        resultMsg.put("status", 500);
        if (Integer.parseInt(hsfData.get("status").toString()) == 1) {
            //鉴权（Around策略部分）
            try {
                log.info("鉴权（Around策略部分）");
                aroundAccess(hsfData);
            } catch (ControlException e) {
            	log.info(String.format("具体控制实现ControlException%s", e.getMsg()));
                resultMsg.put("info", e.getMsg());
                resultMsg.put("data", hsfData);
                return resultMsg.toJSONString();
            } catch (Throwable throwable) {
            	log.info(String.format("具体控制实现Throwable%s", throwable.getMessage()));
                resultMsg.put("info", throwable.getMessage());
                resultMsg.put("data", hsfData);
                return resultMsg.toJSONString();
            }
            resultMsg.put("status", 200);
            resultMsg.put("info", "ok");
            resultMsg.put("data", hsfData);
            return resultMsg.toJSONString();
        } else if (Integer.parseInt(hsfData.get("status").toString()) == 2) {
            //计费&流量追查（After策略部分）
            try {
                log.info("计费&流量追查（After策略部分）");
                afterAccess(hsfData);
            } catch (Throwable throwable) {
                resultMsg.put("info", throwable.getMessage());
                resultMsg.put("data", hsfData);
                return resultMsg.toJSONString();
            }
            resultMsg.put("status", 200);
            resultMsg.put("info", "ok");
            resultMsg.put("data", hsfData);
            return resultMsg.toJSONString();
        } else {
            resultMsg.put("data", hsfData);
            return resultMsg.toJSONString();
        }
    }


    public void beforeAccess(String args) {
        for (AccessStrategyI strategy : strategies) {
            strategy.before(args);
        }
    }

    /**
     * 切入方法之中，在hsf方法被调用之前执行，抛出异常可中断hsf调用
     * @param object
     * @throws Throwable
     */
    public void aroundAccess(JSONObject object) throws Throwable {
            for (AccessStrategyI strategy : strategies) {
                log.info(String.format("aroundAccess本次应用的策略是%s",strategy.getClass().getName()));
                strategy.around(object);
            }
    }

    public void afterAccess(JSONObject object) {
        for (AccessStrategyI strategy : strategies) {
        	log.info(String.format("afterAccess本次应用的策略是%s",strategy.getClass().getName()));
            strategy.after(object);
        }
    }

    public void afterReturningAccess(String args) {
        for (AccessStrategyI strategy : strategies) {
        	log.info(String.format("afterReturningAccess本次应用的策略是%s",strategy.getClass().getName()));
            strategy.afterReturning(args);
        }
    }

    public void afterThrowing(String args) {
        for (AccessStrategyI strategy : strategies) {
        	log.info(String.format("afterThrowing本次应用的策略是%s",strategy.getClass().getName()));
        	log.info(args);
            strategy.afterThrowing(args);
        }
    }

}
