package com.joao.scorecouple.core;

import java.io.Serializable;

public interface DatabaseEntity<PK> extends Serializable {
    PK getId();
}
