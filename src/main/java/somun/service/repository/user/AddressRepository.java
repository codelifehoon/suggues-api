package somun.service.repository.user;

import org.springframework.data.repository.CrudRepository;

import somun.service.repository.vo.user.Address;

public interface AddressRepository extends CrudRepository<Address,Integer>{
}
