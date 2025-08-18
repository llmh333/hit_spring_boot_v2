package com.hit.jobandlogging.base;

import com.hit.jobandlogging.constant.UrlConstant;
import org.mapstruct.TargetType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RestController
@RequestMapping(UrlConstant.BASE_URL)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestApiV1 {
}
