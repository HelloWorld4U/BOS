package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierDAO;
import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.base.SubareaDAO;
import com.itheima.bos.dao.base.TakeTimeDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Jan 20, 2018 5:06:15 PM <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private SubareaDAO subareaDAO;
    @Autowired
    private FixedAreaDao fixedAreaDao;
    
    @Autowired
    private CourierDAO courierDao;
    
    @Autowired
    private TakeTimeDao takeTimeDao;
    
    @Override
    public void save(FixedArea model) {

        fixedAreaDao.save(model);

    }

    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
          
       return fixedAreaDao.findAll(pageable);
    }

    @Override
    public void associationCourierToFixedArea(Long courierId, Long takeTimeId, Long fixedAreaId) {
          
        //根据定区ID找到需要被关联的定区对象
        FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
        Courier courier = courierDao.findOne(courierId);
        TakeTime takeTime = takeTimeDao.findOne(takeTimeId);
        
        fixedArea.getCouriers().add(courier);
        courier.setTakeTime(takeTime);
        
    }

    @Override
    public List<SubArea> findUnAssociated() {
          
        return subareaDAO.findByFixedAreaIsNull();
    }

    @Override
    public List<SubArea> findAssociated(Long id) {
          
        return subareaDAO.findByFixedArea_Id(id);
    }

    @Override
    public void assignSubAreas2FixedArea(Long[] subAreaIds, Long fixedAreaId) {
          
        //解绑已经绑定的分区
        subareaDAO.unbind(fixedAreaId);
        
        if(subAreaIds!=null && subAreaIds.length>0){
            for (Long id : subAreaIds) {
                subareaDAO.bind(id,fixedAreaId);
            }
        }
        
    }

   
}
  
