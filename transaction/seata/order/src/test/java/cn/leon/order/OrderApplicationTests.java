package cn.leon.order;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderApplicationTests {

    private MockMvc mockMvc;
    @Autowired
    private OrderController orderController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    /**
     * 下单扣款
     */
    @Test
    @SneakyThrows
    public void createOrderTest() {
        mockMvc.perform(post("/order/create")
                .param("userId", "1")
                .param("productId", "1")
                .param("price", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("4")));
    }

    /**
     * 余额不足模拟
     */
    @Test
    @SneakyThrows
    public void createOrderExceptionTest() {
        mockMvc.perform(post("/order/create")
                .param("userId", "1")
                .param("productId", "1")
                .param("price", "1000"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("4")));
    }

}
