package com.ababa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Meetings {
    @TableId( value = "meid" ,type = IdType.AUTO)
    @ApiModelProperty("会议ID")
    private Integer meid;

    @ApiModelProperty("所用的会议室的id")
    private Integer mrid;

    @ApiModelProperty("会议名字")
    private String meetingName;

    @ApiModelProperty("预计人数")
    private Integer personNum;

    @ApiModelProperty("所用的会议室名称")
    private String roomName;

    @ApiModelProperty("所用的会议室详细位置")
    private String detailedLocation;

    @ApiModelProperty("公司名称")
    private String userGroup;

    @ApiModelProperty("会议发起人")
    private String username;

    @ApiModelProperty("会议开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("会议结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("会议描述")
    private String meetingDescription;

    @ApiModelProperty("参会成员")
    @TableField(exist = false)
    private List<Participants> participants;

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
