package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierDAO <br/>  
 * Function:  <br/>  
 * Date:     Jan 15, 2018 4:44:21 PM <br/>       
 */
public interface CourierDAO extends JpaRepository<Courier,Long>,JpaSpecificationExecutor<Courier>{

    @Query("update Courier set deltag = '1' where id=?")
    @Modifying
    void updateById(Long id);

    @Query(value="update t_courier set c_deltag=null where c_id=?",nativeQuery=true)
    @Modifying
    void restoreById(long id);

    List<Courier> findByDeltagIsNull();

}
  
