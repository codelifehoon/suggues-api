package somun.service.repository.provider;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import somun.common.biz.Codes;

public interface ContentProviderRepository extends CrudRepository<ContentProvider,Integer>{

    ContentProvider findByProviderKeyAndStatIn(String providerKey, Codes.CONTPROV_STAT[] stat);
    List<ContentProvider> findByStatIn(Codes.CONTPROV_STAT[] stat);

}
