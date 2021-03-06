package somun.service.repository.function;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import somun.service.repository.vo.function.PushSubscription;

public interface PushSubscriptionModifyRepository extends CrudRepository<PushSubscription,Integer>{


    @Modifying
    @Query("UPDATE  PushSubscription u SET " +
               " u.endPoint = :#{#sub.endPoint} " +
               ",u.endPointHash = :#{#sub.endPointHash} " +
               ",u.updateNo=:#{#sub.updateNo}" +
               ",u.updateDt=:#{#sub.updateDt}" +
               " WHERE u.pushSubscriptionNo = :#{#sub.pushSubscriptionNo}"
    )
    Integer updateSubscription(@Param("sub") PushSubscription oldSubscription);

}
