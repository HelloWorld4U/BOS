package com.itheima.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.rmi.CodebaseAwareObjectInputStream;
import org.springframework.stereotype.Controller;

import com.itheima.bos.fore.domain.Customer;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     Jan 21, 2018 9:00:48 PM <br/>       
 */
@Scope("prototype")
@Controller
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    private Customer customer;
    @Override
    public Customer getModel() {
       if(customer==null){
           customer = new Customer();
       }
       return customer;
    }
   //发送验证码
    @Action(value="customerAction_sendMessage")
    public String sendMessage(){
        String randomCode = RandomStringUtils.randomNumeric(6);
        ServletActionContext.getRequest().getSession().setAttribute("vcode", randomCode);
        System.out.println(randomCode);
        try {
            SmsUtils.sendMessage(customer.getTelephone(), randomCode);
        } catch (Exception e) {
            e.printStackTrace();  
            System.out.println("短信发送失败");
        }
        return NONE;
    }
    
    private String inputCode;
    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }
    //登录校验验证码
    @Action(value="customerAction_regist",results={@Result(name="success",location="/signup-success.html",type="redirect"),
            @Result(name="error",location="/signup-fail.html",type="dispatcher")})
    public String regist(){
        
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("vcode");
        System.out.println("session中的code:"+code);
        if(StringUtils.isNotEmpty(code)&&StringUtils.isNotEmpty(inputCode)&&code.equals(inputCode)){
            //验证成功，做保存操作
            try {
                WebClient
                    .create("http://localhost:8180/crm/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(customer);
                
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();  
                ActionContext.getContext().getValueStack().set("msg", "注册失败");
                return ERROR;
            }
            
        }else{
            ActionContext.getContext().getValueStack().set("msg", "验证码错误");
            return ERROR;
        }
    }

}
  
