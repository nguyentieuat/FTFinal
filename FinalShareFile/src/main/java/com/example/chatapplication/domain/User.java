package com.example.chatapplication.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, length = 24)
    private String username;
    private String password;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user")
    private List<FileDocument> fileDocuments;

    @OneToMany(mappedBy="user")
    private List<UserAccessFile> userAccessFiles;
}
