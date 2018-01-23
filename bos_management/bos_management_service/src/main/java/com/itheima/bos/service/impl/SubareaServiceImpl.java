package com.itheima.bos.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubareaDAO;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubareaService;


@Service
@Transactional
public class SubareaServiceImpl implements SubareaService{

    @Autowired
    private SubareaDAO subareaDAO;

    @Override
    public void save(SubArea model) {
          
        subareaDAO.save(model);
        
    }

    @Override
    public Page<SubArea> pageQuery(Specification<SubArea> specification,Pageable pageable) {
          
       return subareaDAO.findAll(specification,pageable);
    }
   
}
  
