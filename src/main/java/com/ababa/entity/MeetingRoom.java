package com.ababa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class MeetingRoom {
    @TableId( value = "mrid" ,type = IdType.AUTO)
    @ApiModelProperty("会议室id")
    private Integer mrid;

    @ApiModelProperty("公司名称")
    private String userGroup;

    @ApiModelProperty("会议室名称")
    private String roomName;

    @ApiModelProperty("会议室位置")
    private String location;

    @ApiModelProperty("会议室楼层")
    private String floor;

    @ApiModelProperty("会议室门牌号")
    private String plateNum;

    @ApiModelProperty("会议室设备")
    private String equipment;

    @ApiModelProperty("会议室设备状态")
    private Integer equipmentState;

    @ApiModelProperty("会议室容量")
    private Integer capacity;

    @ApiModelProperty("会议室状态")
    private Integer state;

    @ApiModelProperty("会议室描述")
    private String description;

    @TableField(exist = false)
    @ApiModelProperty("会议室文件")
    private List<MeetingRoomFile> meetingRoomFiles;

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
