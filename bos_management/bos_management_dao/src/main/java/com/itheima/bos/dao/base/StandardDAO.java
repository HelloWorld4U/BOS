package com.itheima.bos.dao.base;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardDAO <br/>  
 * Function:  <br/>  
 * Date:     Jan 14, 2018 9:21:26 PM <br/>       
 */
@Repository
public interface StandardDAO extends JpaRepository<Standard, Long> {

   
    @Query("update Standard set status=1 where id=?") // JPQL === HQL
    @Modifying
    void updateById(long id);

    @Query("update Standard set status=null where id=?") // JPQL === HQL
    @Modifying
    void restoreById(long id);

    @Query("from Standard where status = null") // JPQL === HQL
    @Modifying
    List<Standard> findById();
}
  
