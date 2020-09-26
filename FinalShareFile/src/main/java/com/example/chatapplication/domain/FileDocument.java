package com.example.chatapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FileDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String filePath;
    private String fileName;
    @ManyToOne
    @JoinColumn(name = "uploader")
    private User user;

}
