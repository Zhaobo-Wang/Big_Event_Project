package com.itheima.pojo;

import com.itheima.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;           // 主键ID
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;         // 文章标题
    @NotEmpty
    private String content;       // 文章内容
    @NotEmpty
    @URL
    private String coverImg;      // 文章封面
    @State
    private String state;         // 文章状态：已发布 已关闭 草稿
    @NotNull
    private Integer categoryId;   // 文章分类ID
    private Integer createUser;   // 创建人ID
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
