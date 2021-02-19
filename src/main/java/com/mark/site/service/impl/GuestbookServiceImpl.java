package com.mark.site.service.impl;

import com.mark.site.entity.Guestbook;
import com.mark.site.mapper.GuestbookMapper;
import com.mark.site.service.GuestbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站点留言表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-02-06
 */
@Service
public class GuestbookServiceImpl extends ServiceImpl<GuestbookMapper, Guestbook> implements GuestbookService {

}
