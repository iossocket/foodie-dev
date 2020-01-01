package com.iossocket.bo;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class UserInfoRequest {
    @NotEmpty
    private String nickName;
    @NotEmpty
    private String realName;
    @NotEmpty
    @Pattern(regexp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$")
    private String mobile;
    @NotEmpty
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;
    @NotNull
    @Min(0)
    @Max(2)
    private Integer gender;
    @NotNull
    private Date birthday;
}
