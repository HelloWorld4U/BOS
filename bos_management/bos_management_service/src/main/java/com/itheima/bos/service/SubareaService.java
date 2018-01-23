package com.itheima.bos.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.SubArea;


public interface SubareaService {

    void save(SubArea model);

    Page<SubArea> pageQuery(Specification<SubArea> specification, Pageable pageable);


}
  
