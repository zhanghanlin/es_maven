es_maven
========
1.	测试该框架需要有依赖服务
	
	1.1 elasticsearch-rtf
		
		GitHub:https://github.com/medcl/elasticsearch-rtf
		
		启动elasticsearch后进入http://localhost:9200/_plugin/rtf/
		
		点击New Index ,名称为es_search
	1.2 Oracle
		
		存在表p_product[id,name]
		
		存在表p_brand[id,name]
2.	ojdbc14 Jar包
		
		由于Oracle限制ojdbc14需要自行下载
		
		ojdbc14包路径/lib/ojdbc14-10.2.0.3.0.jar
3.	启动脚本
		
		在项目根目录执行：jettyTest.bat
4.	初始化数据
		
		访问URL:http://localhost:8081/es_maven/init/init.jsp?ids=2006&type=brand
5.	查看数据
		
		访问URL:http://localhost:8081/es_maven/interface/get.jsp?id=2006&type=brand
6.	自动完成示例

		访问URL:http://localhost:8081/es_maven/index.jsp
7.	未解决问题
		
		注入bean：esNode
		```xml
		<bean id="esNode" class="com.search.es.ElasticsearchNodeFactoryBean">
			<property name="configLocation" value="classpath:conf/elasticsearch.properties" />
		</bean>
		```
		
		Java Bean使用该Bean
		```java
		@Autowired
		ElasticsearchNodeFactoryBean esNode;
		```
		
		JSP使用该Bean
		```java
		InternalNode node = (InternalNode)context.getBean("esNode");
		Client esclient = node.client();
		```