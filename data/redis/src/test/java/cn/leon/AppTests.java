package cn.leon;

import cn.leon.base.RedisOps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {
    @Autowired
    private RedisOps redisOps;

    private ValueOperations<Serializable, Object> valueOperations;

    @Before
    public void before() {
        RedisOps redisOps = new RedisOps();
        this.redisOps = redisOps;
        this.valueOperations = redisOps.getValueOperations();
    }

    @Test
    public void testInit(){
        new RedisOps(null);
    }

    @Test
    public void expireTest() {
        Assert.assertEquals(true, redisOps.expire("1", 1));
    }

    @Test
    public void expireExceptionTest() {
        Assert.assertEquals(true, redisOps.expire("", 1));
        Assert.assertEquals(true, redisOps.expire("test", 0));
        Assert.assertEquals(true, redisOps.expire(null, 1));
    }

    @Test
    public void getVal() {
        Assert.assertNotNull(valueOperations.get("test"));
    }

    @Test
    public void getValExceptionTest() {
        Assert.assertNotNull(redisOps.get(""));
        Assert.assertNotNull(redisOps.get(null));
    }

    @Test
    public void setTest() {
        redisOps.set("test", "");
        redisOps.set("test", null);
        redisOps.set(null, null);
        redisOps.set(null, "test");
        redisOps.set("", "test");
    }


}
