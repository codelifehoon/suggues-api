package somun.service.repository.content;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import somun.common.biz.Codes;
import somun.service.repository.vo.content.ContentStorage;

public interface ContentStorageRepository extends CrudRepository<ContentStorage,Integer>{

    //activityRefNo , activityCode ,storageCode,storageStat
    List<ContentStorage> findByActivityRefNoAndActivityCodeAndStorageCodeAndStatNot(Integer activityRefNo
        , Codes.ACTIVITY_CODE activityCode, Codes.STORAGE_CODE storageCode, Codes.STORAGE_STAT stat);


}
