package org.lychee.service.impl;

import org.lychee.entity.LycheeUser;
import org.lychee.mapper.LycheeUserMapper;
import org.lychee.service.ILycheeUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
