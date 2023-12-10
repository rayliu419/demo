package com.example.dto.domain;

import lombok.Data;

/**
 * BO对象作为领域对象，跟VO层的对象和DAO层的Entity对象要做区分
 *
 */
@Data
public class DepartmentBO {

    public String name;

    public int id;
}
