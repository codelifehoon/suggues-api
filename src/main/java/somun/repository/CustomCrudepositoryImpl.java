package somun.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

@NoRepositoryBean
public class CustomCrudepositoryImpl<T,ID extends Serializable>
            extends SimpleJpaRepository<T , ID>
            implements CustomCrudepository<T, ID> {

    private final  EntityManager  entityManager;

    public CustomCrudepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        entityManager = null;
    }

    @Override
    public void doSomeMethod(ID id) {


    }
}
