//package com.cityseason.content.client;
//
//
//import com.cityseason.common.domain.vo.Result;
//import com.cityseason.content.domain.vo.UserVO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "user-service")
//public interface UserClient {
//    @GetMapping("/user/{id}")
//    Result<UserVO> getUserById(@PathVariable Long id);
//}
