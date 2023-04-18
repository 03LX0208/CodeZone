package com.zone.backend;

import com.zone.backend.consumer.utils.JwtAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodeBackendApplicationTests {

    @Test
    void contextLoads() {
        Integer id = JwtAuthentication.getUserId("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MDYzMTM0YTQzZDc0MmE4OWE0MDVlYjRkYThiMmVmOSIsInN1YiI6IjkiLCJpc3MiOiJzZyIsImlhdCI6MTY4MTc4NjA0MywiZXhwIjoxNjgyOTk1NjQzfQ.1-Sx2gJnk746gkQY4p6YjS475RsL60Z9CCNogJgeisU");
        System.out.println(id);
    }

}
