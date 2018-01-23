package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaDao <br/>  
 * Function:  <br/>  
 * Date:     Jan 20, 2018 5:07:48 PM <br/>       
 */
public interface FixedAreaDao extends JpaRepository<FixedArea, Long>,JpaSpecificationExecutor<FixedArea>{

}
  
