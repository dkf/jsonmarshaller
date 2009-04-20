package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class OptionalNull {

  @Value(optional = true) Embedded embedded;

}
