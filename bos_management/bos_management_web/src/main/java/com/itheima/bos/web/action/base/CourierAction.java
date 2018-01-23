package com.itheima.bos.web.action.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.CourierService;
import com.itheima.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

/**  
 快递员逻辑 
 */
@Scope("prototype")
@Controller
@Namespace("/")
@ParentPackage("struts-default")
public class CourierAction extends BaseAction<Courier> {

    public CourierAction() {
        super(Courier.class);  
    }

    @Autowired
    private CourierService courierService;
  
    //保存快递员信息
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        
        courierService.save(getModel());
        return SUCCESS;
    }
    
  
    @Action(value="courierAction_showByPage")
    public String showByPage(){
        
        //构造查询条件
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                //获取查询请求参数
                String company = getModel().getCompany();
                String type = getModel().getType();
                String courierNum = getModel().getCourierNum();
                Standard standard = getModel().getStandard();
                
                List<Predicate> list = new ArrayList<>();
                
                if(StringUtils.isNotEmpty(type)){
                    Predicate p1 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p1);
                }
                
                if(StringUtils.isNotEmpty(courierNum)){
                    Predicate p2 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p2);
                }
                
                if(StringUtils.isNotEmpty(company)){
                    Predicate p3 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p3);
                }
                
                if(standard!=null){
                    String name = standard.getName();
                    if(StringUtils.isNotEmpty(name)){
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                    }
                }
                //如果用户没有输入条件
                if(list.size()==0){
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                System.out.println(123);
                return cb.and(arr);
            }};
        
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page = courierService.pageQuery(specification,pageable);
        
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"fixedAreas"});
        page2Json(page,config);
        return NONE;
    }

    
    //属性驱动从页面获取需要删除项的id拼接字符串
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    //批量删除(作废)
    @Action(value="courierAction_batchDel",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchDel(){
        String[] idArray = ids.split(",");
        if(idArray.length>0){
            for (String id : idArray) {
                courierService.updateById(Long.parseLong(id));
            }
        }
        return SUCCESS;
    }
    
    //批量恢复
    @Action(value="courierAction_batchRestore",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchRestore(){
        String[] idArray = ids.split(",");
        if(idArray.length>0){  
            for (String id : idArray) {
                courierService.restoreById(Long.parseLong(id));
            }
        }
        return SUCCESS;
    }
    
    //查询所有正常在职状态的快递员 即deltag=null
    //../../courierAction_findAvailableCourier.action
    @Action(value="courierAction_findAvailableCourier")
    public String findAvailableCourier(){
        List<Courier> list = courierService.findAvailable();
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"fixedAreas"});
        list2Json(list, config);
        return NONE;
    }
    
   

}
  
