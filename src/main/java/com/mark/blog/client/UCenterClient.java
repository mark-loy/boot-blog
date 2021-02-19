package com.mark.blog.client;

import com.mark.base.vo.VisitorRpcVO;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/5 10:50
 */
public interface UCenterClient {

    /**
     * 通过访客id获取访客信息
     * @param visitorId 访客id
     * @return VisitorRpcVO
     */
    @GetMapping("api/ucenter/rpc/visitor/{vid}")
    VisitorRpcVO getVisitorId(@ApiParam("访客id") @PathVariable("vid") String visitorId);

}
