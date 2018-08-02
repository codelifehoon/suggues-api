package somun.service.repository.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import somun.service.repository.vo.content.ContentStorage;

public interface ContentStorageModifyRepository extends CrudRepository<ContentStorage,Integer>{


    @Modifying
    @Query("UPDATE  ContentStorage u SET " +
               "u.stat = :#{#contentStorage.stat} " +
               ",u.updateNo=:#{#contentStorage.updateNo}" +
               ",u.updateDt=:#{#contentStorage.updateDt}" +
               " WHERE u.activityRefNo = :#{#contentStorage.activityRefNo}" +
               " and u.activityCode = :#{#contentStorage.activityCode}" +
               " and u.stat not in ('S4')" +
               " and u.storageCode = :#{#contentStorage.storageCode}" +
               " and u.createNo = :#{#contentStorage.createNo}"
    )
    Integer updateContentStorageStat(@Param("contentStorage") ContentStorage contentStorage);

}
