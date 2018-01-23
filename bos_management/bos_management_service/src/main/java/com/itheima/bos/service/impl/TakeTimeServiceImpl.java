package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.TakeTimeDao;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Jan 21, 2018 3:48:35 PM <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService{

    @Autowired
    private TakeTimeDao takeTimeDao;
    @Override
    public List<TakeTime> findAll() {
          
        return takeTimeDao.findAll();
    }

}
  
