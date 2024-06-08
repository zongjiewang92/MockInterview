package com.fdu.mockinterview.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String passwd;
    private String createDate;
    private String email;
    private String firstName;
    private String lastName;
    private String updateDate;
}
