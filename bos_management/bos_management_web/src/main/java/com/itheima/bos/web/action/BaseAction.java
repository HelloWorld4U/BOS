package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     Jan 18, 2018 8:21:21 AM <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

    private T t;
    private Class<T> clazz;
    
    public BaseAction(Class<T> clazz){
        this.clazz = clazz;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
    @Override
    public T getModel() {
       return t;
    }
    
    //分页展示快递员信息
    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    protected void page2Json(Page<T> page,JsonConfig config) {
        long totalElements = page.getTotalElements();
        List<T> list = page.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", totalElements);
        String json = "";
        if(config==null){
            json = JSONObject.fromObject(map).toString();
        }else{
            json = JSONObject.fromObject(map,config).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();  
            
        }
    }
    
    
    public void list2Json(List list,JsonConfig config) {
        String json;
        
        if(config==null){
            json = JSONArray.fromObject(list).toString();
        }else {
            json = JSONArray.fromObject(list,config).toString();
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

}
  
