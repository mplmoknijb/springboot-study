package cn.leon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.leon.bo.RoleUserBo;
import cn.leon.entity.RoleUser;
import cn.leon.entity.RoleUserExample;
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
public class RoleUserService {

    private static final Logger _LOGGER = LoggerFactory.getLogger(RoleUserService.class);

    @Autowired private RoleUserRepository roleUserRepository;

    /**
     * 文件操作
     */
    @Autowired private FilePathBuilder filePathBuilder;

    public Result<List<RoleUserBo>> query (RoleUserCommand condition, int page, int limit) {
        Result<List<RoleUserBo>> result = new Result<>();
        PageHelper.startPage(page, limit);
        List<RoleUserBo> roleUserBos = roleUserRepository.queryList(condition);
        result.setSuccess(true);
        result.setData(roleUserBos);
        result.setCount(new PageInfo<>(roleUserBos).getTotal());
        return result;
    }

    public Result<RoleUser> detail (String id) {
        Result<RoleUser> result = new Result<>();
        RoleUser roleUser = roleUserRepository.selectByPrimaryKey(id);
        result.setSuccess(true);
        result.setData(roleUser);
        return result;
    }

    public Result<RoleUser> save (RoleUserBo roleUserBo) {
        RoleUser roleUser = new RoleUser();
        BeanUtils.copyProperties(roleUserBo, roleUser);

        int ret = roleUserRepository.insert(roleUser);
        if (ret == 0) {
            throw new RuntimeException("新增异常");
        }

        Result<RoleUser> result = new Result<>();
        result.setSuccess(true);
        result.setData(roleUser);
        return result;
    }

    public Result<RoleUser> update (String id, RoleUserBo roleUserBo) {
        RoleUserExample rue = new RoleUserExample();
        rue.createCriteria()
                .andIdEqualTo(id);

        RoleUser roleUser = new RoleUser();
        BeanUtils.copyProperties(roleUserBo, roleUser);
        int ret = roleUserRepository.updateByExampleSelective(roleUser, rue);
        if (ret == 0) {
            throw new RuntimeException("更新异常");
        }

        Result<RoleUser> result = new Result<>();
        result.setSuccess(true);
        result.setData(roleUser);

        return result;
    }

    public Result<String> remove (String id) {
        roleUserRepository.deleteByPrimaryKey(id);
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setData(id);
        return result;
    }

    public void export(RoleUserCommand condition, ServletWebRequest request) {
        List<RoleUserBo> roleUserBos = roleUserRepository.queryList(condition);

        if (CollectionUtils.isNotEmpty(roleUserBos)) {
            //构建数据表格头
            List<String> heads = Arrays.asList(
                            "序号",
            );

            //构建表格数据
            List<List<String>> rows = new ArrayList<>();
                for (int i = 0; i < roleUserBos.size(); i++) {
                    List<String> row = new ArrayList<>();
                    RoleUser roleUser = roleUserBos.get(i);
                    row.add(String.valueOf(i + 1));
                    rows.add(row);
                }

            FilePath excel = filePathBuilder.buildArchivePath("RoleUser.xlsx");
            File dir = new File(FilenameUtils.getFullPath(excel.getLocalPath()));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                ExcelHelper.export(heads, rows, excel.getLocalPath());
            } catch (Exception e) {
                _LOGGER.error("导出异常>>>",e);
            }
            filePathBuilder.download(request.getRequest(), request.getResponse(), excel.getLocalPath(), "RoleUser.xlsx");
        } else {
            throw new RuntimeException("Excel导出异常");
        }

    }
}