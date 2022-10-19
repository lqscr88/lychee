package org.lychee.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lychee.system.api.entity.LycheeUser;
import org.lychee.system.mapper.LycheeUserMapper;
import org.lychee.system.service.ILycheeUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lychee
 * @since 2022-01-05
 */
@Service
public class LycheeUserServiceImpl extends ServiceImpl<LycheeUserMapper, LycheeUser> implements ILycheeUserService {

}
