package com.cityseason.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cityseason.api.client.UserClient;
import com.cityseason.api.domain.enums.UserRole;
import com.cityseason.api.domain.vo.Result;
import com.cityseason.api.domain.vo.UserVO;
import com.cityseason.common.util.RequestContext;
import com.cityseason.content.domain.dto.TagDTO;
import com.cityseason.content.domain.po.Tag;
import com.cityseason.content.domain.vo.TagVO;
import com.cityseason.content.mapper.TagMapper;
import com.cityseason.content.service.ITagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务实现
 *
 * @author author
 * @since 2025-04-26
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    private final UserClient userClient;

    public TagServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public TagVO addTag(TagDTO tagDTO) {
        IsSuperAdmin();
        Tag tag = new Tag();
        String tagName = tagDTO.getName();
        // 判断 tagName 是否为 null
        if (tagName == null) {
            throw new RuntimeException("标签名不能为空");
        }
        if (tagName != null) {
            // 使用链式调用和条件判断简化代码
            if (this.getOne(new QueryWrapper<Tag>().eq("name", tagName)) != null) {
                throw new RuntimeException("标签名已存在，不能重复添加");
            }
        }
        // 保存新标签到数据库
        BeanUtils.copyProperties(tagDTO, tag);
        this.save(tag);

        TagVO tagVO = new TagVO();
        BeanUtils.copyProperties(tag, tagVO);
        return tagVO;
    }

    @Override
    public List<Tag> listTag() {
        IsSuperAdmin();
        List<Tag> list = this.list();
        return list;
    }

    private void IsSuperAdmin() {
        Result<UserVO> user = userClient.getUserById(RequestContext.getCurrentUserId());
        if (user.getCode() != 200) {
            throw new RuntimeException("用户不存在");
        }
        if (!user.getData().getRole().equals(UserRole.SUPER_ADMIN)) {
            throw new RuntimeException("只有超级管理员可以添加标签");
        }
    }
}
