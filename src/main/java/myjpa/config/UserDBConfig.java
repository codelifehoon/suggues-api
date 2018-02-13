package myjpa.config;


//@Slf4j
//@Configuration
//@EnableTransactionManagement
class UserDBConfig {
//
////    @Bean
////    public DataSource dataSource() {
////
////        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
////        return builder.setType(EmbeddedDatabaseType.HSQL).build();
////    }
//
//
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource dataSource() {
//
//        return DataSourceBuilder
//                .create()
//                .build();
//    }
//
//
//    @Bean(name = "entityManagerFactoryUser")
//    public EntityManagerFactory entityManagerFactoryUser() {
//
//        log.debug("##### entityManagerFactory run #####");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("myjpa.repository");
//        factory.setDataSource(dataSource());
//        factory.afterPropertiesSet();
//
//
//
//        return factory.getObject();
//    }
//
//    @Bean(name = "transactionManagerUser")
//    public PlatformTransactionManager transactionManagerUser() {
//
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactoryUser());
//        return txManager;
//    }
}