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

package org.diorite.serialization.naming;

import org.diorite.serialization.setting.Property;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * A mechanism for finding property by its name.
 */
public interface PropertyResolutionStrategy
{

    /**
     * Translates the serialized property name to property instance.
     *
     * @param name
     *     the property name to resolve.
     * @param properties
     *     map of properties where key is the name of property from serialized file. Map will only contain properties that are not
     *     resolved yet and
     *
     * @return property instance, must be from given map or error will be thrown. Null can be returned if resolution should be postponed
     * until other names are resolved.
     */
    @Nullable
    Property<?, ?> resolveProperty(String name, Map<? extends String, ? extends Property<?, ?>> properties);
}
