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
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.StandardService;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

@Scope("prototype")
@Controller
@Namespace("/")
@ParentPackage("struts-default")
public class SubareaAction extends BaseAction<SubArea> {

    public SubareaAction() {
        super(SubArea.class);  
    }
    
    @Autowired
    private SubareaService subareaService;
    
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String save(){
        subareaService.save(getModel());
        return SUCCESS;
    }
    

    @Action(value="subareaAction_showByPage")
    public String showByPage(){
        
        //构造查询条件
        Specification<SubArea> specification = new Specification<SubArea>() {

            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                //获取查询请求参数
                Area area = getModel().getArea();
                String keywords = getModel().getKeyWords();
                FixedArea fixedArea = getModel().getFixedArea();
               
                
                List<Predicate> list = new ArrayList<>();
                
                if(area!=null){
                    if(StringUtils.isNotEmpty(area.getProvince())){
                        Join<Object, Object> join = root.join("area");
                        Predicate p1 = cb.equal(join.get("province").as(String.class), area.getProvince());
                        list.add(p1);
                    }
                    
                    
                    if(StringUtils.isNotEmpty(area.getCity())){
                        Join<Object, Object> join = root.join("area");
                        Predicate p2 = cb.equal(join.get("city").as(String.class), area.getCity());
                        list.add(p2);
                    }
                    
                    if(StringUtils.isNotEmpty(area.getDistrict())){
                        Join<Object, Object> join = root.join("area");
                        Predicate p3 = cb.equal(join.get("district").as(String.class), area.getDistrict());
                        list.add(p3);
                    }
                }
                
                if(fixedArea!=null&&fixedArea.getId()!=null){
                    Join<Object, Object> join = root.join("fixedArea");
                    Predicate p4 = cb.equal(join.get("id").as(Long.class), fixedArea.getId());
                    list.add(p4);
                }
                
                if(StringUtils.isNotEmpty(keywords)){
                    Predicate p5 = cb.like(root.get("keyWords").as(String.class), "%"+keywords+"%");
                    list.add(p5);
                }
                
                
                //如果用户没有输入条件
                if(list.size()==0){
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }};
        
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page = subareaService.pageQuery(specification,pageable);
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas","couriers"});
        page2Json(page,config);
        return NONE;
    }
    
}
  
