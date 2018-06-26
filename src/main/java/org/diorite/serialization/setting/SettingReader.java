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

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Optional;

/**
 * Reads setting from annotated type.
 *
 * @param <T>
 *     type of setting object.
 */
public interface SettingReader<T extends Setting>
{
    /**
     * Reads setting from annotated type. <br>
     * If setting can not be found method should return empty nullable.
     *
     * @param annotatedElement
     *     annotated type to read setting from it.
     *
     * @return read setting object or empty nullable.
     */
    Optional<? extends T> read(AnnotatedElement annotatedElement);

    /**
     * Create single setting reader from multiple setting readers that will be run in loop until result is found, or nothing will be
     * returned if all of them will fail.
     *
     * @param type
     *     type of setting to read.
     * @param readers
     *     collection of readers.
     * @param <T>
     *     type of setting.
     *
     * @return setting reader.
     */
    static <T extends Setting> SettingReader<? extends T> of(Class<T> type, Collection<? extends SettingReader<? extends T>> readers)
    {
        return new MultiSettingReader<>(readers);
    }
}
