/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.serialization.setting;

import org.diorite.commons.reflect.type.TypeToken;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertySettings
{
    private final TypeToken<?>       realType;
    private final TypeToken<?>       serializerType;
    private final PropertySettings[] parameters;
    final Map<Class<?>, Setting> settings = new HashMap<>();

    public PropertySettings(TypeToken<?> realType, TypeToken<?> serializerType, PropertySettings... parameters)
    {
        this.realType = realType;
        this.serializerType = serializerType;
        this.parameters = parameters.clone();
    }

    /**
     * Returns real type of represented field.
     *
     * @return real type of represented field.
     */
    public TypeToken<?> getRealType()
    {
        return realType;
    }

    /**
     * Returns type of field serializer.
     *
     * @return type of field serializer.
     */
    public TypeToken<?> getSerializerType()
    {
        return serializerType;
    }

    /**
     * Returns type to use as generic parameters for given generic type. <br>
     * Like for {@literal Map<String, ? extends Set<? extends String>>}
     * it will return {@literal String} for index 0, and {@literal Set<? extends String>} for index 1.
     *
     * @param index
     *     index of parameter
     *
     * @return type to use as generic parameters.
     */
    public TypeToken<?> getGenericParameterType(int index)
    {
        return parameters[index].serializerType;
    }

    /**
     * Returns field configuration for given generic type index. <br>
     * Like for {@literal Map<String, ? extends Set<? extends String>>}
     * it will return configuration of {@literal String} parameter for index 0,
     * and configuration of {@literal Set<? extends String>} for index 1,
     * {@code mapFieldConfig.getFieldConfiguration(1).getGenericParameterType(0)} will return type of {@literal Set<? extends String>},
     * so it will return String type.
     *
     * @param index
     *     index of parameter
     *
     * @return type to use as generic parameters.
     */
    public PropertySettings getFieldConfiguration(int index)
    {
        return parameters[index];
    }

    /**
     * Returns setting value if exists.
     *
     * @param type
     *     type of setting to get.
     * @param <T>
     *     type of setting to get.
     *
     * @return setting value if exists.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T extends Setting> T getSetting(Class<T> type)
    {
        return (T) this.settings.get(type);
    }

    public static PropertySettings fromType(SettingsManager manager, TypeToken<?> type)
    {
        return null; // TODO
    }

    @SuppressWarnings("unchecked")
    public static PropertySettings fromType(SettingsManager manager, AnnotatedType annotatedType)
    {
        return null; // TODO
    }

    public static PropertySettings fromField(SettingsManager manager, Field field)
    {
        return null; // TODO
    }

}
