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
@ApiModel("用户文件")
public class MeetingRoomFile {
    @ApiModelProperty("文件主键")
    @TableId( value = "mrfid" ,type = IdType.AUTO)
    private Integer mrfid;

    @ApiModelProperty("对应会议室ID")
    private Integer mrid;

    @ApiModelProperty("图片的url")
    private String url;

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
