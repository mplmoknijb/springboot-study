package cn.leon.controller;

import cn.leon.bo.MenuBo;
import cn.leon.command.MenuCommand;
import cn.leon.entity.Menu;
import com.panhai.common.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.leon.service.MenuService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.util.List;


@Api("MenuController")
@Validated
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired private MenuService menuService;

    @GetMapping
    @ApiOperation("查询列表")
    public Result<List<MenuBo>> query (
            @ApiParam("查询列表参数") @ModelAttribute MenuCommand condition,
            @ApiParam("页数") @RequestParam(required = false, defaultValue = "1") int page,
            @ApiParam("条数") @RequestParam(required = false, defaultValue = "10") int limit) {
        return menuService.query(condition, page, limit);
    }

    @GetMapping("{id}")
    @ApiOperation("通过ID获取详情")
    public Result<Menu> detail (
            @ApiParam("申报单ID") @PathVariable String id) {
        return menuService.detail(id);
    }

    @PostMapping
    @ApiOperation("新增")
    public Result<Menu> save (
            @ApiParam("申报单业务对象") @Valid @RequestBody MenuBo menuBo) {
        return menuService.save(menuBo);
    }

    @PutMapping("{id}")
    @ApiOperation("更新")
    public Result<Menu> update (
            @ApiParam("申报单ID") @PathVariable String id,
            @ApiParam("申报单业务对象") @Valid @RequestBody MenuBo menuBo) {
        return menuService.update(id, menuBo);
    }

    @DeleteMapping("{id}")
    @ApiOperation("通过ID删除")
    public Result<String> remove (
            @ApiParam("申报单ID") @PathVariable String id) {
        return menuService.remove(id);
    }

    @GetMapping("export")
    @ApiOperation("导出Excel")
    public void export (
            @ApiParam("查询列表参数") @ModelAttribute MenuCommand condition, ServletWebRequest request) {
        menuService.export(condition, request);
    }

}