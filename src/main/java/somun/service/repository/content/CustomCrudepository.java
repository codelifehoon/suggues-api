package somun.service.repository.content;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomCrudepository<T,ID extends Serializable>  extends CrudRepository<T , ID>{
         void doSomeMethod(ID id);
}
