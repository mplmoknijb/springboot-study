package cn.leon.controller;

import cn.leon.bo.RoleUserBo;
import cn.leon.command.RoleUserCommand;
import cn.leon.entity.RoleUser;
import com.panhai.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.leon.service.RoleUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.util.List;


@Api("RoleUserController")
@Validated
@RestController
@RequestMapping("roleUser")
public class RoleUserController {

    @Autowired private RoleUserService roleUserService;

    @GetMapping
    @ApiOperation("查询列表")
    public Result<List<RoleUserBo>> query (
            @ApiParam("查询列表参数") @ModelAttribute RoleUserCommand condition,
            @ApiParam("页数") @RequestParam(required = false, defaultValue = "1") int page,
            @ApiParam("条数") @RequestParam(required = false, defaultValue = "10") int limit) {
        return roleUserService.query(condition, page, limit);
    }

    @GetMapping("{id}")
    @ApiOperation("通过ID获取详情")
    public Result<RoleUser> detail (
            @ApiParam("申报单ID") @PathVariable String id) {
        return roleUserService.detail(id);
    }

    @PostMapping
    @ApiOperation("新增")
    public Result<RoleUser> save (
            @ApiParam("申报单业务对象") @Valid @RequestBody RoleUserBo roleUserBo) {
        return roleUserService.save(roleUserBo);
    }

    @PutMapping("{id}")
    @ApiOperation("更新")
    public Result<RoleUser> update (
            @ApiParam("申报单ID") @PathVariable String id,
            @ApiParam("申报单业务对象") @Valid @RequestBody RoleUserBo roleUserBo) {
        return roleUserService.update(id, roleUserBo);
    }

    @DeleteMapping("{id}")
    @ApiOperation("通过ID删除")
    public Result<String> remove (
            @ApiParam("申报单ID") @PathVariable String id) {
        return roleUserService.remove(id);
    }

    @GetMapping("export")
    @ApiOperation("导出Excel")
    public void export (
            @ApiParam("查询列表参数") @ModelAttribute RoleUserCommand condition, ServletWebRequest request) {
        roleUserService.export(condition, request);
    }

}