
package com.reforgedsrc.app.vue2demo.boot.data.entity;

import lombok.Data;

@Data
public class Authority {

    private Long id = 0L;

    private AuthorityName name = null;

    public Authority() {

    }
}