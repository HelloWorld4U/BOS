package com.itheima.bos.web.action.base;

import java.util.Collection;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.itheima.bos.domain.base.Customer;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.FixedAreaService;
import com.itheima.bos.web.action.BaseAction;

import aj.org.objectweb.asm.Type;
import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     Jan 20, 2018 4:59:45 PM <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

    public FixedAreaAction() {
        super(FixedArea.class);  
        
    }
    
    @Autowired
    private FixedAreaService fixedAreaService;
  
    //保存新添加的定区信息
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save(){
        
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    
  //分页展示
    @Action(value="fixedAreaAction_showByPage")
    public String showByPage(){
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<FixedArea> page = fixedAreaService.pageQuery(pageable);
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas","couriers"});
        page2Json(page, config);
        return NONE;
    }
    
    
    //通过webservice服务调用crm系统查询关联客户信息
    //
    @SuppressWarnings("unchecked")
    @Action(value="fixedAreaAction_checkUnassociated")
    public String checkUnAssociated(){
        
        List<Customer> list = (List<Customer>) WebClient
            .create("http://localhost:8180/crm/webService/customerService/getUnAssociatedCust")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .getCollection(Customer.class);
        
        list2Json(list, null);
        return NONE;
        
    }
    
    //显示关联客户信息到下拉框右边
    @Action(value="fixedAreaAction_checkAssociated")
    @SuppressWarnings("unchecked")
    public String checkAssociated(){
        List<Customer> list = (List<Customer>)WebClient
                .create("http://localhost:8180/crm/webService/customerService/getAssociatedCust")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", getModel().getId())
                .getCollection(Customer.class);
        
        list2Json(list, null);
        return NONE;
    }
    
    
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    
    //确认最新关联客户
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",
             results={@Result(name = "success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignCustomers2FixedArea(){
        
        //先把该定区绑定的客户全部解绑，再把所有关联的客户绑定到该定区
        WebClient
                .create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", getModel().getId())
                .query("customerIds", customerIds)
                .put(null);
        
        return SUCCESS;
    }
    
    
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    //关联快递员
    @Action(value="fixedAreaAction_associationCourierToFixedArea",
            results={@Result(name = "success",location="/pages/base/fixed_area.html",type="redirect")})
   public String associationCourierToFixedArea(){
        
        
        fixedAreaService.associationCourierToFixedArea(courierId,takeTimeId,getModel().getId());
      
       
       return SUCCESS;
   }
    
    //关联分区-下拉显示未关联的信息
    @Action(value="fixedAreaAction_checkUnassociated2")
    public String checkUnAssociated2(){
       List<SubArea> list = fixedAreaService.findUnAssociated();
       JsonConfig config = new JsonConfig();
       config.setExcludes(new String[]{"subareas","couriers"});
       list2Json(list, config);
        
        return NONE;
    }
    
  //关联分区-下拉显示关联的分区信息
    @Action(value="fixedAreaAction_checkAssociated2")
    public String checkAssociated2(){
        List<SubArea> list = fixedAreaService.findAssociated(getModel().getId());
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas","couriers"});
        list2Json(list, config);
         
         return NONE;
    }
    
    private Long[] subAreaIds;
    public void setSubAreaIds(Long[] subAreaIds) {
        this.subAreaIds = subAreaIds;
    }
    
    //关联分区操作
    @Action(value="fixedAreaAction_assignSubAreas2FixedArea",
            results={@Result(name = "success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignSubAreas2FixedArea(){
        
        fixedAreaService.assignSubAreas2FixedArea(subAreaIds,getModel().getId());
        return SUCCESS;
    }
}
  
