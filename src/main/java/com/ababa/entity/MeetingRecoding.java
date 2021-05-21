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
@ApiModel("会议记录")
public class MeetingRecoding {
    @ApiModelProperty("乐观锁")
    @TableId( value = "mreid" ,type = IdType.AUTO)
   private Integer  mreid;

    @ApiModelProperty("对应的会议id")
   private Integer meid;

    @ApiModelProperty("会议名称")
   private String meetingName;

    @ApiModelProperty("会议时间")
   private String meetingTime;

    @ApiModelProperty("会议地点")
   private String location;

    @ApiModelProperty("参会人员")
   private String participants;
    @ApiModelProperty("缺席人员")
   private String absencePerson;

    @ApiModelProperty("会议内容")
   private String meetingContent;

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
