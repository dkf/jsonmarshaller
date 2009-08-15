package com.twolattes.json;

abstract class DefaultBoxingFieldDescriptor implements FieldDescriptor {

  public byte getFieldValueByte(Object entity) {
    return (Byte) getFieldValue(entity);
  }

  public char getFieldValueChar(Object entity) {
    return (Character) getFieldValue(entity);
  }

  public boolean getFieldValueBoolean(Object entity) {
    return (Boolean) getFieldValue(entity);
  }

  public short getFieldValueShort(Object entity) {
    return (Short) getFieldValue(entity);
  }

  public int getFieldValueInt(Object entity) {
    return (Integer) getFieldValue(entity);
  }

  public long getFieldValueLong(Object entity) {
    return (Long) getFieldValue(entity);
  }

  public float getFieldValueFloat(Object entity) {
    return (Float) getFieldValue(entity);
  }

  public double getFieldValueDouble(Object entity) {
    return (Double) getFieldValue(entity);
  }

}
