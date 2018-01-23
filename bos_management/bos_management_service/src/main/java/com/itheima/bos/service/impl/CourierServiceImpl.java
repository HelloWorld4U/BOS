package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierDAO;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Jan 15, 2018 4:42:28 PM <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierDAO courierDAO;
    @Override
    public void save(Courier courier) {
        
        courierDAO.save(courier);

    }
    @Override
    public Page<Courier> pageQuery(Pageable pageable) {
          
        return courierDAO.findAll(pageable);
    }
    
    //批量删除
    @Override
    public void updateById(Long id) {
          
        courierDAO.updateById(id);
        
    }
    //批量恢复
    @Override
    public void restoreById(long id) {
          
        courierDAO.restoreById(id);  
        
    }
    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
          
       return courierDAO.findAll(specification, pageable);
    }
    @Override
    public List<Courier> findAvailable() {
          
        return courierDAO.findByDeltagIsNull();
    }

}
  
