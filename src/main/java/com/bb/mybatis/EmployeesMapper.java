package com.bb.mybatis;

import java.util.List;

/**
 * Created by admin on 2018/3/12.
 */
public interface EmployeesMapper {


    public Employee selectByPrimaryKey(Integer employeeId );


    public List<Employee> selectByMinSalary(Integer employeeId );

    public List<Employee> findPage( Page page );

}
