package tech.intellispaces.ixora.data.dictionary;

import org.junit.jupiter.api.Test;
import tech.intellispaces.commons.base.type.Types;
import tech.intellispaces.ixora.data.association.PropertiesHandle;
import tech.intellispaces.ixora.data.association.PropertiesToDataGuide;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for guide {@link PropertiesToDataGuide}.
 */
public abstract class AbstractDictionaryToDataGuideTest {

  public abstract PropertiesToDataGuide getGuide();

  @Test
  public void testPrimitiveData_whenEmptyProperties() {
    // Given
    PropertiesHandle dictionary = mock(PropertiesHandle.class);

    // When
    PrimitiveDataHandle data = getGuide().propertiesToData(dictionary, Types.get(PrimitiveDataHandle.class));

    // Then
    assertThat(data).isNotNull();
    assertThat(data.intValue()).isNull();
    assertThat(data.doubleValue()).isNull();
  }

  @Test
  public void testSimpleData_whenEmptyProperties() {
    // Given
    PropertiesHandle dictionary = mock(PropertiesHandle.class);

    // When
    SimpleDataHandle data = getGuide().propertiesToData(dictionary, Types.get(SimpleDataHandle.class));

    // Then
    assertThat(data).isNotNull();
    assertThat(data.intValue()).isNull();
    assertThat(data.doubleValue()).isNull();
    assertThat(data.stringValue()).isNull();
  }

  @Test
  public void testNestedData_whenEmptyProperties() {
    // Given
    PropertiesHandle dictionary = mock(PropertiesHandle.class);

    // When
    NestedDataHandle data = getGuide().propertiesToData(dictionary, Types.get(NestedDataHandle.class));

    // Then
    assertThat(data).isNotNull();
    assertThat(data.stringValue()).isNull();
    assertThat(data.nestedValue()).isNull();
  }

  @Test
  public void testPrimitiveData_whenNotEmptyProperties() {
    // Given
    PropertiesHandle dictionary = mock(PropertiesHandle.class);
    when(dictionary.value("intValue")).thenReturn(1);
    when(dictionary.value("doubleValue")).thenReturn(2.2);

    // When
    PrimitiveDataHandle data = getGuide().propertiesToData(dictionary, Types.get(PrimitiveDataHandle.class));

    // Then
    assertThat(data).isNotNull();
    assertThat(data.intValue()).isEqualTo(1);
    assertThat(data.doubleValue()).isEqualTo(2.2);
  }

  @Test
  public void testSimpleData_whenNotEmptyProperties() {
    // Given
    PropertiesHandle dictionary = mock(PropertiesHandle.class);
    when(dictionary.value("intValue")).thenReturn(1);
    when(dictionary.value("doubleValue")).thenReturn(2.2);
    when(dictionary.value("stringValue")).thenReturn("abc");

    // When
    SimpleDataHandle data = getGuide().propertiesToData(dictionary, Types.get(SimpleDataHandle.class));

    // Then
    assertThat(data).isNotNull();
    assertThat(data.intValue()).isEqualTo(1);
    assertThat(data.doubleValue()).isEqualTo(2.2);
    assertThat(data.stringValue()).isEqualTo("abc");
  }

  @Test
  public void testNestedData_whenNotEmptyProperties() {
    // Given
    PropertiesHandle dictionary = mock(PropertiesHandle.class);
    PropertiesHandle nestedDictionary = mock(PropertiesHandle.class);
    when(dictionary.value("stringValue")).thenReturn("abc");
    when(dictionary.value("nestedValue")).thenReturn(nestedDictionary);
    when(nestedDictionary.value("stringValue")).thenReturn("def");

    // When
    NestedDataHandle data = getGuide().propertiesToData(dictionary, Types.get(NestedDataHandle.class));

    // Then
    assertThat(data).isNotNull();
    assertThat(data.stringValue()).isEqualTo("abc");
    assertThat(data.nestedValue()).isNotNull();
    assertThat(data.nestedValue().stringValue()).isEqualTo("def");
    assertThat(data.nestedValue().nestedValue()).isNull();
  }
}
