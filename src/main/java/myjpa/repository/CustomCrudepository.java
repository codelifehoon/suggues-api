package myjpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomCrudepository<T,ID extends Serializable>  extends CrudRepository<T , ID>{
         void doSomeMethod(ID id);
}
