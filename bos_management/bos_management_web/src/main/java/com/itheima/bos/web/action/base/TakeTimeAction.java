package com.itheima.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.TakeTimeService;
import com.itheima.bos.web.action.BaseAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     Jan 21, 2018 3:43:50 PM <br/>       
 */
@Scope("prototype")
@Controller
@Namespace("/")
@ParentPackage("struts-default")
public class TakeTimeAction extends BaseAction<TakeTime>{

    @Autowired
    private TakeTimeService takeTimeService;
    public TakeTimeAction() {
        super(TakeTime.class);  
        
    }
    
    @Action(value="takeTimeAction_findAll")
    public String findAll(){
        List<TakeTime> list = takeTimeService.findAll();
        list2Json(list, null);
        return NONE;
    }

}
  
