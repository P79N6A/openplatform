package com.kd.op.util.dwr;

import org.directwebremoting.impl.DefaultScriptSessionManager;

public class DWRScriptSessionManager extends DefaultScriptSessionManager {
     public DWRScriptSessionManager(){
            //绑定一个ScriptSession增加销毁事件的监听器
            this.addScriptSessionListener( new DWRScriptSessionListener());
     }
}
