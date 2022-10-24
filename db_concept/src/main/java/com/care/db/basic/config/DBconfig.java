package com.care.db.basic.config;

import java.io.IOException;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//	<!-- 마이바티스 xml파일과 dao빈 연결 -->
//	<mybatis-spring:scan base-package="com.care.db.basic.repository"/>
@MapperScan(basePackages = {"com.care.db.basic.repository", ""})
public class DBconfig {
	
	@Bean
	public HikariDataSource dataSource() {
//		<!-- 히카리 커넥션풀 빈 등록 -->
//		<bean class="com.zaxxer.hikari.HikariConfig" id="hikariConfig">
//			<property name="driverClassName" value="oracle.jdbc.OracleDriver"/>
//			<property name="jdbcUrl" value="jdbc:oracle:thin:@localhost:1521:xe"/>
//			<property name="username" value="oracle"/>
//			<property name="password" value="oracle"/>
//		</bean>	
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
		hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		hikariConfig.setUsername("oracle");
		hikariConfig.setPassword("oracle");
		
//		<!-- 히카리 데이터소스 빈 등록 -->
//		<bean class="com.zaxxer.hikari.HikariDataSource" id="dataSource">
//			<constructor-arg ref="hikariConfig"/>
//		</bean>
		HikariDataSource dataSource = new HikariDataSource(hikariConfig); // DI(의존성 주입)
		return dataSource;
	}

	
	
//	<!-- 데이터소스 마이바티스에 등록 및 xml 위치 설정 -->
//	<bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sessionFactory">
//		<property name="dataSource" ref="dataSource"/>
//		<property name="mapperLocations" value="classpath:/mappers/**/*Mapper.xml" />
//	</bean>
	@Bean
	public SqlSessionFactoryBean sessionFactory() throws IOException {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		
		PathMatchingResourcePatternResolver resolver =
				new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resolver.getResources("classpath:/mappers/**/*Mapper.xml"));
		return sessionFactory;
	}
}
