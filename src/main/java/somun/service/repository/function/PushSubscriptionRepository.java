package somun.service.repository.function;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PushSubscriptionRepository extends CrudRepository<PushSubscription,Integer>{

    public PushSubscription findByUserNoAndUseYn(Integer userNo ,String useYn );
    public List<PushSubscription> findByUserNoInAndUseYn(int[] userNo , String useYn );

}
