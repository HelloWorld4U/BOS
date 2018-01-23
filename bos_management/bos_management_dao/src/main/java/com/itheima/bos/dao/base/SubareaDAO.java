package com.itheima.bos.dao.base;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaDAO <br/>  
 * Function:  <br/>  
 * Date:     Jan 18, 2018 4:22:26 PM <br/>       
 */
public interface SubareaDAO extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea>{

    List<SubArea> findByFixedAreaIsNull();

    List<SubArea> findByFixedArea_Id(Long id);

    @Query("update SubArea set fixedArea = null where fixedArea.id=?")
    @Modifying
    void unbind(Long fixedAreaId);

    @Query("update SubArea set fixedArea.id = ?2 where id =?1")
    @Modifying
    void bind(Long subAreaId, Long fixedAreaId);

}
  
