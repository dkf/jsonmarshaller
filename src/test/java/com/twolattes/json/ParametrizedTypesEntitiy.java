package com.twolattes.json;

@Entity
public class ParametrizedTypesEntitiy<T> {

  @Value T value;

}
