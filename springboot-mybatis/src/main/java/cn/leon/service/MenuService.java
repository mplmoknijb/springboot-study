package cn.leon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.leon.bo.MenuBo;
import cn.leon.command.MenuCommand;
import cn.leon.entity.Menu;
import cn.leon.entity.MenuExample;
import cn.leon.repository.MenuRepository;
import com.panhai.common.model.Result;
import com.panhai.sys.builder.FilePathBuilder;
import com.panhai.sys.utils.ExcelHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import com.panhai.sys.bo.FilePath;
import org.springframework.web.context.request.ServletWebRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.*;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MenuService {

    private static final Logger _LOGGER = LoggerFactory.getLogger(MenuService.class);

    @Autowired private MenuRepository menuRepository;

    /**
     * 文件操作
     */
    @Autowired private FilePathBuilder filePathBuilder;

    public Result<List<MenuBo>> query (MenuCommand condition, int page, int limit) {
        Result<List<MenuBo>> result = new Result<>();
        PageHelper.startPage(page, limit);
        List<MenuBo> menuBos = menuRepository.queryList(condition);
        result.setSuccess(true);
        result.setData(menuBos);
        result.setCount(new PageInfo<>(menuBos).getTotal());
        return result;
    }

    public Result<Menu> detail (String id) {
        Result<Menu> result = new Result<>();
        Menu menu = menuRepository.selectByPrimaryKey(id);
        result.setSuccess(true);
        result.setData(menu);
        return result;
    }

    public Result<Menu> save (MenuBo menuBo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuBo, menu);

        int ret = menuRepository.insert(menu);
        if (ret == 0) {
            throw new RuntimeException("新增异常");
        }

        Result<Menu> result = new Result<>();
        result.setSuccess(true);
        result.setData(menu);
        return result;
    }

    public Result<Menu> update (String id, MenuBo menuBo) {
        MenuExample me = new MenuExample();
        me.createCriteria()
                .andIdEqualTo(id);

        Menu menu = new Menu();
        BeanUtils.copyProperties(menuBo, menu);
        int ret = menuRepository.updateByExampleSelective(menu, me);
        if (ret == 0) {
            throw new RuntimeException("更新异常");
        }

        Result<Menu> result = new Result<>();
        result.setSuccess(true);
        result.setData(menu);

        return result;
    }

    public Result<String> remove (String id) {
        menuRepository.deleteByPrimaryKey(id);
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setData(id);
        return result;
    }

    public void export(MenuCommand condition, ServletWebRequest request) {
        List<MenuBo> menuBos = menuRepository.queryList(condition);

        if (CollectionUtils.isNotEmpty(menuBos)) {
            //构建数据表格头
            List<String> heads = Arrays.asList(
                            "序号",
                    "id",
                    "菜单名",
                    "url",
                    "父类id",
                    "排序",
                    "描述",
                    "图标"
            );

            //构建表格数据
            List<List<String>> rows = new ArrayList<>();
                for (int i = 0; i < menuBos.size(); i++) {
                    List<String> row = new ArrayList<>();
                    Menu menu = menuBos.get(i);
                    row.add(String.valueOf(i + 1));
                    row.add(ExcelHelper.getStringValue(menu.getId()));
                    row.add(ExcelHelper.getStringValue(menu.getName()));
                    row.add(ExcelHelper.getStringValue(menu.getUrl()));
                    row.add(ExcelHelper.getStringValue(menu.getParentId()));
                    row.add(ExcelHelper.getStringValue(menu.getSort()));
                    row.add(ExcelHelper.getStringValue(menu.getRemark()));
                    row.add(ExcelHelper.getStringValue(menu.getIcon()));
                    rows.add(row);
                }

            FilePath excel = filePathBuilder.buildArchivePath("Menu.xlsx");
            File dir = new File(FilenameUtils.getFullPath(excel.getLocalPath()));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                ExcelHelper.export(heads, rows, excel.getLocalPath());
            } catch (Exception e) {
                _LOGGER.error("导出异常>>>",e);
            }
            filePathBuilder.download(request.getRequest(), request.getResponse(), excel.getLocalPath(), "Menu.xlsx");
        } else {
            throw new RuntimeException("Excel导出异常");
        }

    }
}