package com.aws.codestar.projecttemplates.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "instances")
public class Instance {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    public String name;

    @Column(name = "s3_size")
    public String s3Size;

    @Column(name = "s3_etag")
    public String s3Etag;

    @Column(name = "s3_last_modified")
    public String s3LastModified;

    public Instance() {}
}
