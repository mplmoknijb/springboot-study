package com.leon.axon.web;

import com.leon.axon.applicaiton.cmd.CreateProductCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.ConcurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
@Slf4j
@RestController
public class ProductController {
    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void create(@PathVariable(value = "id") String id,
                       @RequestParam(value = "name", required = true) String name,
                       @RequestParam(value = "price", required = true) long price,
                       @RequestParam(value = "stock",required = true) int stock,
                       HttpServletResponse response) {

        log.debug("Adding Product [{}] '{}' {}x{}", id, name, price, stock);
        try {
            // multiply 100 on the price to avoid float number
            CreateProductCommand command = new CreateProductCommand(id,name,price*100,stock);
            commandGateway.sendAndWait(command);
            response.setStatus(HttpServletResponse.SC_CREATED);// Set up the 201 CREATED response
            return;
        } catch (CommandExecutionException cex) {
            log.warn("Add Command FAILED with Message: {}", cex.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            if (null != cex.getCause()) {
                log.warn("Caused by: {} {}", cex.getCause().getClass().getName(), cex.getCause().getMessage());
                if (cex.getCause() instanceof ConcurrencyException) {
                    log.warn("A duplicate product with the same ID [{}] already exists.", id);
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                }
            }
        }
    }

}
