package com.yy.cloud.common.data.otd.file;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/12/17 10:15 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleFileInfo implements Serializable {
    private static final long serialVersionUID = -8445156301233271328L;
    private String id;
    private String name; // 文件名称
    private String contentType; // 文件类型
    private long size;
    private Date uploadDate;
    private String md5;
    private String path; // 文件路径
}
