package com.ababa.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class User {
    @TableId( value = "uid" ,type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Long uid;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("是否具有管理员权限, 1具有, 0不具有")
    private Integer isAdmin;

    @ApiModelProperty("公司名称")
    private String userGroup;

    @Version
    @ApiModelProperty("乐观锁")
    @JsonIgnore
    private Integer version;

    @TableLogic
    @ApiModelProperty("逻辑删除")
    @JsonIgnore
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    @JsonIgnore
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改时间")
    @JsonIgnore
    private Date gmtModified;

}
