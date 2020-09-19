package com.nzpq.mapper;

import com.nzpq.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpDao {
    /**
     * 查询所有员工
     * @return
     */
    List<Emp> findAll();

    /**
     * 添加员工
     * @param emp
     */
    void save(Emp emp);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 查询一个，用于删除员工信息时，删除图片
     * @param id
     * @return
     */
    Emp findOne(Integer id);

    /**
     * 更新信息
     * @param emp
     */
    void update(Emp emp);
}
