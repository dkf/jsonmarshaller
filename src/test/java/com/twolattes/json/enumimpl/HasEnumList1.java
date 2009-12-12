package com.twolattes.json.enumimpl;

import java.util.List;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class HasEnumList1 {
  @Value List<Abc> abcs;
}
