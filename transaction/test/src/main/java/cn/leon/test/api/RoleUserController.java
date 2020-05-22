package cn.leon.test.api;


import cn.leon.test.base.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
public class RoleUserController {


    @Autowired
    private TransactionTestService service;


    @GetMapping
    public void test(){
        service.test1();
    }
}