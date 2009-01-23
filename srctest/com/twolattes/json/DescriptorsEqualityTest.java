package com.twolattes.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.junit.Test;

import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;

public class DescriptorsEqualityTest {
  @Test
  public void testEqualityNoFormalParams() throws IOException {
    EntityDescriptor<?> d1 = create(BaseTypeEntity.class);
    EntityDescriptor<?> d2 = create(BaseTypeEntity.class);
    assertEquals(d1, d2);
  }

  private EntityDescriptor<?> create(Class<BaseTypeEntity> clazz) throws IOException {
    return new DescriptorFactory().create(
        clazz, new EntityDescriptorStore(), new HashMap<Type, Class<?>>());
  }
}
