package com.itheima.bos.web.action.base;


import java.util.List;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import com.itheima.bos.web.action.BaseAction;

/**  
      
 */
@Controller
@Scope("prototype") // 等价于applicationContext.xml中scope属性
@Namespace("/") // 等价于struts.xml中package节点中namespace属性
@ParentPackage("json-default") // 等价于struts.xml中package节点中extends属性
public class StandardAction extends BaseAction<Standard>{

    public StandardAction() {
        super(Standard.class);  
    }
    @Autowired
    private StandardService standardService;
    
    @Action(value="standardAction_save",results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
    public String save(){
        standardService.save(getModel());
        return SUCCESS;
    }
  
    @Action(value="standardAction_showByPage")
    public String showByPage(){
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);
        page2Json(page, null);
        
        return NONE;
    }
    
    /*private Map<String,Object> map = new HashMap<>();
    public Map<String, Object> getMap() {
        return map;
    }
    @Action(value="standardAction_showByPage",results={@Result(type="json",params={"root","map"})})
    public String showByPage(){
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> page = standardService.pageQuery(pageable);
        long totalElements = page.getTotalElements();
        List<Standard>list = page.getContent();
        map.put("rows",list);
        map.put("total",totalElements);
        return SUCCESS;
    }*/
    
    
    @Action(value="standardAction_findAll")
    public String findAll(){
        List<Standard> list = standardService.findAll();
        list2Json(list,null);
        return NONE;
    }

    
    
    //批量删除
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    @Action(value="standardAction_batchDel",results={@Result(name="success",location="/pages/base/standard.html",type="redirect"),@Result
            (name="error",location="/pages/base/error.html",type="redirect")})
    
    public String batchDel(){
        if(!StringUtils.isEmpty(ids)) {
            String[] idstrs = ids.split(",");
            for (String id : idstrs) {
                standardService.updateById(Long.parseLong(id));
            }
        }
       return SUCCESS;
    }
    
  //批量还原
    @Action(value="standardAction_batchRestore",results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
    public String batchRestore(){
        if(!StringUtils.isEmpty(ids)) {
            String[] idstrs = ids.split(",");
            for (String id : idstrs) {
                standardService.restoreById(Long.parseLong(id));
            }
        }
       return SUCCESS;
    }

}
  
