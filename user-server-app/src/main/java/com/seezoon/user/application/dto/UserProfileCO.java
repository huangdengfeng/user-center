package com.seezoon.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dfenghuang
 * @date 2023/6/22 00:21
 */
@Getter
@Setter
public class UserProfileCO {

    private String name;
    private String mobile;
    private String avatar;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
