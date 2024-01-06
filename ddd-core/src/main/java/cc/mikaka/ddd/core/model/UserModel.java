package cc.mikaka.ddd.core.model;

import lombok.Data;

import javax.annotation.processing.SupportedAnnotationTypes;

@Data
public class UserModel {
    private String userId;
    private String userName;
    private Integer age;
    private String status;
}
