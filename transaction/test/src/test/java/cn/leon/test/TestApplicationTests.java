package cn.leon.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class TestApplicationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;


    @Test
    void contextLoads() throws Exception {

        MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build().perform(
                get("http://52.83.254.207:6643/v3-public")    //请求的url,请求的方法是get
//                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization","Bearer token-nvk65:pd8hrqm2xnw7jb7cl99v5xw5vtj2rr9mnrdj5nsk9d5dd5mv7jm6wx")).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();
    }

}
