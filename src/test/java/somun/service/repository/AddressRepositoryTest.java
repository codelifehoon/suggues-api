package somun.service.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.util.LogUtil;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository;

    @Test
    public void findAll(){
        new LogUtil().printObjectList(addressRepository.findAll());
    }
}

