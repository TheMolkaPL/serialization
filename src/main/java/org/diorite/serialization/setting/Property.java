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

import javax.annotation.Nullable;
import java.lang.reflect.Modifier;

/**
 * Represent single property from any place, normal class with field, pair of getter/setters, or special configuration class with own
 * get/set implementation.
 *
 * @param <D>
 *     type of declaration.
 * @param <T>
 *     type of property.
 */
public interface Property<D, T>
{
    /**
     * Returns raw name of property, raw name isn't affected by any naming policy or annotations/settings.
     *
     * @return raw name of property.
     */
    String getRawName();

    /**
     * Returns name that will be used while serializing.
     *
     * @return name that will be used while serializing.
     */
    String getSerializationName();

    /**
     * Returns true if given name represent this property, multiple names might return true due to defined settings.
     *
     * @param name
     *     name of property to check.
     *
     * @return true if given name represent this property.
     */
    boolean isDeserializationName(String name);

    /**
     * @return true if this is static property.
     */
    boolean isStatic(Accessor accessor);

    /**
     * @return true if this is transient property.
     */
    boolean isTransient(Accessor accessor);

    /**
     * @return true if this is synthetic property.
     */
    boolean isSynthetic(Accessor accessor);

    /**
     * @return true if this is volatile property.
     */
    boolean isVolatile(Accessor accessor);

    /**
     * @return true if this is native property.
     */
    boolean isNative(Accessor accessor);

    /**
     * Note that final/read-only property can be still deserialized using constructor of object.
     *
     * @return true if this is final/read-only property.
     */
    boolean isFinal();

    /**
     * @return visibility of getter or setter.
     */
    Visibility getVisibility(Accessor accessor);

    /**
     * @return class that declares this property.
     */
    Class<D> getDeclaringClass();

    /**
     * @return raw type of this property.
     */
    @SuppressWarnings("unchecked")
    default Class<? extends T> getRawType()
    {
        return (Class<? extends T>) getSettings().getRealType().getRawType();
    }

    /**
     * @return settings of this property.
     */
    PropertySettings getSettings();

    /**
     * Returns single setting from property settings.
     *
     * @param type
     *     type of setting to get.
     * @param <S>
     *     type of setting to get.
     *
     * @return single setting from property settings.
     *
     * @see PropertySettings#getSetting(Class)
     */
    @Nullable
    default <S extends Setting> S getSetting(Class<S> type)
    {
        return this.getSettings().getSetting(type);
    }

    /**
     * Set value of this property in given declaring object instance.
     *
     * @param instance
     *     declaring object instance.
     * @param value
     *     value of property.
     */
    void setValue(D instance, @Nullable T value);

    /**
     * Get value of this property from given declaring object instance.
     *
     * @param instance
     *     declaring object instance.
     *
     * @return value of property.
     */
    @Nullable
    T getValue(D instance);

    enum Visibility
    {
        PRIVATE
            {
                @Override
                public boolean is(int modifiers) { return Modifier.isPrivate(modifiers); }
            },
        PROTECTED
            {
                @Override
                public boolean is(int modifiers) { return Modifier.isProtected(modifiers); }
            },
        PUBLIC
            {
                @Override
                public boolean is(int modifiers) { return Modifier.isPublic(modifiers); }
            },
        DEFAULT
            {
                @Override
                public boolean is(int modifiers)
                {
                    return ! PRIVATE.is(modifiers) && ! PROTECTED.is(modifiers) && ! PUBLIC.is(modifiers);
                }
            },;

        public abstract boolean is(int modifiers);
    }

    enum Accessor
    {
        GETTER,
        SETTER
    }
}
