package googleApi.search;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
public class DocumentGenTest {

/*
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        helper.setUp();

    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
*/

/*

    @Test
    public void createDocument2_mock(){


        String email = "tmatsuo@example.com";
        String authDomain = "example.com";
        helper.setEnvEmail(email);
        helper.setEnvAuthDomain(authDomain);
        helper.setEnvIsLoggedIn(true);



        User currentUser = UserServiceFactory.getUserService().getCurrentUser();
        String userEmail = currentUser == null ? "" : currentUser.getEmail();
        String userDomain = currentUser == null ? "" : currentUser.getAuthDomain();
        String myDocId = "PA6-5000";


        Document doc = Document.newBuilder()
                               // Setting the document identifer is optional.
                               // If omitted, the search service will create an identifier.
                               .setId(myDocId)
                               .addField(Field.newBuilder().setName("content").setText("the rain in spain"))
                               .addField(Field.newBuilder().setName("email").setText(userEmail))
                               .addField(Field.newBuilder().setName("domain").setAtom(userDomain))
                               .addField(Field.newBuilder().setName("published").setDate(new Date()))
                               .build();

        assertThat(doc.getOnlyField("content").getText()).contains("the rain in spain");
        assertThat(doc.getOnlyField("email").getText()).isEqualTo(email);

        log.debug("###############");
        log.debug(doc.toString());

    }

*/



    @Test
    public void createDocument_normal(){

        User currentUser = UserServiceFactory.getUserService().getCurrentUser();
        String userEmail = currentUser == null ? "userEmail" : currentUser.getEmail();
        String userDomain = currentUser == null ? "userDomain" : currentUser.getAuthDomain();
        String myDocId = "PA6-5000";


        Document doc = Document.newBuilder()
                               // Setting the document identifer is optional.
                               // If omitted, the search service will create an identifier.
                               .setId(myDocId)
                               .addField(Field.newBuilder().setName("content").setText("the rain in spain1"))
                               .addField(Field.newBuilder().setName("email").setText(userEmail))
                               .addField(Field.newBuilder().setName("domain").setAtom(userDomain))
                               .addField(Field.newBuilder().setName("published").setDate(new Date()))
                               .build();
        log.debug("###############");
        log.debug(doc.toString());

    }



    @Test
    public void createDocument_thread() throws InterruptedException {
        // [START create_document]

       /* Thread thread = ThreadManager.createBackgroundThread(() -> {
            User currentUser = UserServiceFactory.getUserService().getCurrentUser();
            String userEmail = currentUser == null ? "" : currentUser.getEmail();
            String userDomain = currentUser == null ? "" : currentUser.getAuthDomain();
            String myDocId = "PA6-5000";


            Document doc = Document.newBuilder()
                                   // Setting the document identifer is optional.
                                   // If omitted, the search service will create an identifier.
                                   .setId(myDocId)
                                   .addField(Field.newBuilder().setName("content").setText("the rain in spain"))
                                   .addField(Field.newBuilder().setName("email").setText(userEmail))
                                   .addField(Field.newBuilder().setName("domain").setAtom(userDomain))
                                   .addField(Field.newBuilder().setName("published").setDate(new Date()))
                                   .build();


            log.debug(doc.toString());
        });
        thread.start();*/
//        ThreadFactory threadFactory = ThreadManager.backgroundThreadFactory();
//        threadFactory.newThread(() -> { log.debug("###############");}).start();
        List<Integer> list = Arrays.asList(1,2);
        long count = list.parallelStream().map((d) -> {
            User currentUser = UserServiceFactory.getUserService().getCurrentUser();
            String userEmail = currentUser == null ? "userEmail" : currentUser.getEmail();
            String userDomain = currentUser == null ? "userDomain" : currentUser.getAuthDomain();
            String myDocId = "PA6-5000";
            Document doc = Document.newBuilder()
                                   // Setting the document identifer is optional.
                                   // If omitted, the search service will create an identifier.
                                   .setId(myDocId)
                                   .addField(Field.newBuilder().setName("content").setText("the rain in spain1"))
                                   .addField(Field.newBuilder().setName("email").setText(userEmail))
                                   .addField(Field.newBuilder().setName("domain").setAtom(userDomain))
                                   .addField(Field.newBuilder().setName("published").setDate(new Date()))
                                   .build();
            log.debug("###############");
            log.debug(doc.toString());
            return true;
        }).count();
//        ThreadManager.createBackgroundThread(() -> { log.debug("###############");}).start();





    }

}
