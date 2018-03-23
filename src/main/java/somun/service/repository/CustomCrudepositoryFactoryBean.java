package somun.service.repository;

;


public class CustomCrudepositoryFactoryBean {}
//
//
//public class CustomCrudepositoryFactoryBean<R extends JpaRepository<T,I>,T,I extends  Serializable>
//            extends JpaRepositoryFactoryBean<R,T,I> {
//
//                protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
//                    return new MyRepositoryFactory(em);
//                }
//
//                private static class MyRepositoryFactory<T, I extends Serializable>
//                        extends JpaRepositoryFactory {
//
//                    private final EntityManager em;
//
//                    public MyRepositoryFactory(EntityManager em) {
//
//                        super(em);
//                        this.em = em;
//                    }
//
//                    protected Object getTargetRepository(RepositoryMetadata metadata) {
//                        return new CustomCrudepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), em);
//                    }
//
//                    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//                        return CustomCrudepositoryImpl.class;
//                    }
//                }
//            }
