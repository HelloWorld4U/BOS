package com.itheima.bos.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     Jan 15, 2018 1:12:33 PM <br/>       
 */

public interface StandardService {

    void save(Standard standard);

    Page<Standard> pageQuery(Pageable pageable);

    void updateById(long parseLong);

    void restoreById(long parseLong);

    List<Standard> findAll();


    
}
  
