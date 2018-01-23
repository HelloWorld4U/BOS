package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaDAO;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Jan 17, 2018 8:17:00 PM <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDAO areaDAO;
    @Override
    public void save(List<Area> list) {
        areaDAO.save(list);
    }
    @Override
    public Page<Area> pageQuery(Pageable pageable) {
       return areaDAO.findAll(pageable);
    }
    @Override
    public List<Area> findAll() {
        return areaDAO.findAll();
    }
    @Override
    public List<Area> findByQ(String q) {
          
        q="%"+q.toUpperCase()+"%";
        return areaDAO.findByQ(q);
    }

}
  
