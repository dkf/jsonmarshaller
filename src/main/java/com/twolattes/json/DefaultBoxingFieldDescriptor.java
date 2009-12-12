package com.twolattes.json;

abstract class DefaultBoxingFieldDescriptor implements FieldDescriptor {

  public byte getFieldValueByte(Object entity) {
    return (Byte) getFieldValue(entity);
  }

  public void setFieldValueByte(Object entity, byte value) {
    setFieldValue(entity, value);
  }

  public char getFieldValueChar(Object entity) {
    return (Character) getFieldValue(entity);
  }

  public void setFieldValueChar(Object entity, char value) {
    setFieldValue(entity, value);
  }

  public boolean getFieldValueBoolean(Object entity) {
    return (Boolean) getFieldValue(entity);
  }

  public void setFieldValueBoolean(Object entity, boolean value) {
    setFieldValue(entity, value);
  }

  public short getFieldValueShort(Object entity) {
    return (Short) getFieldValue(entity);
  }

  public void setFieldValueShort(Object entity, short value) {
    setFieldValue(entity, value);
  }

  public int getFieldValueInt(Object entity) {
    return (Integer) getFieldValue(entity);
  }

  public void setFieldValueInt(Object entity, int value) {
    setFieldValue(entity, value);
  }

  public long getFieldValueLong(Object entity) {
    return (Long) getFieldValue(entity);
  }

  public void setFieldValueLong(Object entity, long value) {
    setFieldValue(entity, value);
  }

  public float getFieldValueFloat(Object entity) {
    return (Float) getFieldValue(entity);
  }

  public void setFieldValueFloat(Object entity, float value) {
    setFieldValue(entity, value);
  }

  public double getFieldValueDouble(Object entity) {
    return (Double) getFieldValue(entity);
  }

  public void setFieldValueDouble(Object entity, double value) {
    setFieldValue(entity, value);
  }

}
