package com.lzx.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;

public class BaseMyBatisTest {
    static SqlSessionFactory sqlSessionFactory;
    static SqlSession sqlSession;

    @BeforeClass
    public static void initSqlSessionFactory() throws IOException {
        String resources = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resources);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = sqlSessionFactory.openSession();
    }

    @AfterClass
    public static void destroySqlSession(){
        sqlSession.close();
    }
}
