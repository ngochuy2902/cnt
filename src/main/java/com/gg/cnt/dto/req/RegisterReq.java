package com.gg.cnt.dto.req;

import com.gg.cnt.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReq {
    @NotBlank
    private String username;

    @NotBlank
    @Min(6)
    private String password;

    @NotBlank
    private String name;

    @NotNull
    private Long roleId;
}
