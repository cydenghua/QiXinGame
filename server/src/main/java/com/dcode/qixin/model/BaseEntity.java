package com.dcode.qixin.model;

public class BaseEntity {

//
//    @Id
//    @Column(name = "Id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

//    @Transient
    private Integer page;

//    @Transient
    private Integer rows;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

}
