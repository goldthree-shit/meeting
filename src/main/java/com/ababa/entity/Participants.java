package com.ababa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Participants {
    @TableId( value = "pid" ,type = IdType.AUTO)
    private Integer pid;

    @ApiModelProperty("开的哪场会议")
    private Integer meid;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话号码")
    private String telephone;

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