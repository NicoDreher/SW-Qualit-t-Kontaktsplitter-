package de.dhbw.kontaktsplitter.test;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class StringArrayConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if(source != null) {
            if(source instanceof String && String[].class.isAssignableFrom(targetType)) {
                if(!((String) source).isEmpty()) {
                    return ((String) source).split("\\s*;\\s*");
                }
                else {
                    return new String[0];
                }
            }
            else {
                throw new ArgumentConversionException("Conversion from " + source.getClass() + " to "
                        + targetType + " not supported.");
            }
        }
        else {
            return null;
        }

    }
}
