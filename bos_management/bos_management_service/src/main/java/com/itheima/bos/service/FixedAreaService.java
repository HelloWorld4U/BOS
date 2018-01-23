package com.itheima.bos.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     Jan 20, 2018 5:03:47 PM <br/>       
 */

public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);

    void associationCourierToFixedArea(Long courierId, Long takeTimeId, Long id);

    List<SubArea> findUnAssociated();

    List<SubArea> findAssociated(Long id);

    void assignSubAreas2FixedArea(Long[] subAreaIds, Long id);

}
  
