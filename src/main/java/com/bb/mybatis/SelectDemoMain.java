package com.bb.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/3/12.
 */
public class SelectDemoMain {


    public static void main(String[] args) throws Exception {
        /*
         * 1.加载mybatis的配置文件，初始化mybatis，创建出SqlSessionFactory，是创建SqlSession的工厂
         * 这里只是为了演示的需要，SqlSessionFactory临时创建出来，在实际的使用中，SqlSessionFactory只需要创建一次，当作单例来使用
         */
        InputStream inputStream = Resources.getResourceAsStream("mybatisConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);

        //2. 从SqlSession工厂 SqlSessionFactory中创建一个SqlSession，进行数据库操作
        SqlSession sqlSession = factory.openSession();

        EmployeesMapper  employeesMapper = sqlSession.getMapper(EmployeesMapper.class);

        try{
            Page<Employee> page = new Page<Employee>();
            page.setPageNo(2);
            List<Employee> users = employeesMapper.findPage(page);
            page.setResults(users);
            System.out.println(page);
        } finally {
            sqlSession.close();
        }


    //3.使用SqlSession查询
        Map<String,Object> params = new HashMap<String,Object>();

        params.put("min_salary",10000);
        params.put("employeeId",10000);

        Employee result = sqlSession.selectOne("com.bb.mybatis.EmployeesMapper.selectByPrimaryKey",params);

        System.out.println("result====");


        //a.查询工资低于10000的员工
//        List<Employee> result = sqlSession.selectList("com.bb.mybatis.EmployeesMapper.selectByMinSalary",params);
//        //b.未传最低工资，查所有员工
//        List<Employee> result1 = sqlSession.selectList("com.bb.mybatis.EmployeesMapper.selectByMinSalary");
//        System.out.println("薪资低于10000的员工数："+result.size());
//        //~output :   查询到的数据总数：5
//        System.out.println("所有员工数: "+result1.size());
        //~output :  所有员工数: 8
    }


}
