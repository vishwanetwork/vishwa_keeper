package com.scan.keeper.db;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @Author: OTTO
 * @Date: 2020-07-23 18:28
 */
@Component
public class DbCenter {
    public static DbCenter dbCenter;

    @PostConstruct
    public void init() {
        dbCenter = this;
    }

}
