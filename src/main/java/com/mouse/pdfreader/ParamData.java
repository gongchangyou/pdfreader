package com.mouse.pdfreader;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/13 21:41
 */
@Data
@Builder
public class ParamData {
    @Range(min=1,max=10, message = "1-10")
    int age;

    @NotNull(message = "null name")
    String name;

}
