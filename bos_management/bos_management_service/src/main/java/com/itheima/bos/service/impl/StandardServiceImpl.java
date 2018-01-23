package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardDAO;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;

/**  
 * 收派标准逻辑
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardDAO standardDao;
    
    //保存或更新收派标准（区别在于对象是否含有id）
    @Override
    public void save(Standard standard) {

        standardDao.save(standard);

    }

    //分页展示收派标准信息
    @Override
    public Page<Standard> pageQuery(Pageable pageable) {
          
        return standardDao.findAll(pageable);
    }

    //批量删除
    @Override
    public void updateById(long id) {
          
        standardDao.updateById(id); 
        
    }

    //批量还原
    @Override
    public void restoreById(long id) {
        standardDao.restoreById(id);   
    }

    @Override
    public List<Standard> findAll() {
        return standardDao.findById();
    }

    

}
  
