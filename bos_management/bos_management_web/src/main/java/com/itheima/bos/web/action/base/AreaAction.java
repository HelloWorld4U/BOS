package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
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

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.AreaService;
import com.itheima.bos.web.action.BaseAction;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{

    public AreaAction() {
        super(Area.class);  
    }

    @Autowired
    private AreaService areaService;


    // 上传xls文件
    // 接收从页面上传过来的文件，属性名称必须和前端定义的一致
    private File file;
    private String fileFileName;

    public void setFile(File file) {
        this.file = file;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    @Action(value = "areaAction_importXLS",
            results = {@Result(name="success",location = "/pages/base/area.html", type = "redirect")})
    public String importXLS() {

        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));

            // 指定读取文档中的哪一个工作簿
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Area> list = new ArrayList<>();
            // 循环读取工作簿中的每一行
            for (Row row : sheet) {

                int rowNum = row.getRowNum();
                // 跳过第一行的无效数据信息
                if (rowNum == 0) {
                    continue;
                }
                // 读取每行每列的信息（列的索引从0开始）
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();

                String province01 = province.substring(0, province.length() - 1);
                String city01 = city.substring(0, city.length() - 1);
                String district01 = district.substring(0, district.length() - 1);
                
                String citycode = PinYin4jUtils.hanziToPinyin(city01, "").toUpperCase();
                
                String[] heads = PinYin4jUtils.getHeadByString(province01 + city01 + district01, true);
                String shortcode = PinYin4jUtils.stringArrayToString(heads);

                Area area = new Area(province, city, district, postcode, citycode, shortcode);
                list.add(area);
            }
            
            areaService.save(list);
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(fileFileName);
        return SUCCESS;
    }
    
    //分页展示
    @Action(value="areaAction_showByPage")
    public String showByPage(){
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> page = areaService.pageQuery(pageable);
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas"});
        page2Json(page, config);
        return NONE;
    }
    
    //获取前台下拉框动态传过来的q（筛选条件）
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
  
    //查询所有的地区信息返回字符串
    @Action(value="areaAction_findAll")
    public String findAll(){
        List<Area> list = null;
        
       if(StringUtils.isNotEmpty(q)){
           list = areaService.findByQ(q);
       }else{
           list = areaService.findAll();
       }
       JsonConfig config = new JsonConfig();
       config.setExcludes(new String[]{"subareas"});
       list2Json(list, config);
       return NONE;
    }

}
