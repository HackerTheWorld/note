/* ====================================================================================================
 * Copyright© 2020 诚瑞光学  All Rights Reserved
 *
 * ====================================================================================================
 * Change Log
 * ====================================================================================================
 * 2020-11-01     诚瑞光学      [Init] 监听接收返回信息.
 * ==================================================================================================== */
package com.aacoptics.gateway.decorator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;

import com.test.gateway.entity.SystemLog;
import com.test.gateway.repository.SystemLogRepository;

import reactor.core.publisher.Mono;

/**
 * <p>监听接收返回信息</p>
 *
 * @author 诚瑞光学
 * @version 1.0.0
 * @since jdk 1.8
 */
public class ResponseListener extends ServerHttpResponseDecorator {
	
	//jpa
    private final SystemLog systemLog;
    private final SystemLogRepository systemLogRepository;

    public ResponseListener(ServerHttpResponse delegate, SystemLog systemLog, SystemLogRepository systemLogRepository) {
        super(delegate);
		//jpa
        this.systemLog = systemLog;
        this.systemLogRepository = systemLogRepository;
    }

    @Override
    public void beforeCommit(@NonNull Supplier<? extends Mono<Void>> action) {
        ServerHttpResponse serverHttpResponse = getDelegate();
		//jpa
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        systemLog.setHttpStatusCode(String.valueOf(Objects.requireNonNull(serverHttpResponse.getStatusCode()).value()));
        systemLog.setReturnTime(df.format(LocalDateTime.now()));
        systemLogRepository.save(systemLog);
		//网关拦截后返回
        super.beforeCommit(action);
    }

}

