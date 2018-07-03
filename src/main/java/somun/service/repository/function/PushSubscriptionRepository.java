package somun.service.repository.function;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import somun.service.repository.vo.function.PushSubscription;

public interface PushSubscriptionRepository extends CrudRepository<PushSubscription,Integer>{

    public PushSubscription findByUserNoAndUseYn(Integer userNo ,String useYn );
    public List<PushSubscription> findByUserNoInAndUseYn(int[] userNo , String useYn );

}
