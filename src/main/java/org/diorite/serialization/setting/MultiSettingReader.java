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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

class MultiSettingReader<T extends Setting> implements SettingReader<T>
{
    private final ArrayList<SettingReader<? extends T>> readers;

    public MultiSettingReader(Collection<? extends SettingReader<? extends T>> readers) {this.readers = new ArrayList<>(readers);}

    void addReader(SettingReader<? extends T> reader)
    {
        synchronized (this.readers)
        {
            this.readers.add(reader);
        }
    }

    @Override
    public Optional<? extends T> read(AnnotatedElement annotatedElement)
    {
        Optional<? extends T> result = Optional.empty();
        synchronized (this.readers)
        {
            for (SettingReader<? extends T> reader : readers)
            {
                result = reader.read(annotatedElement);
                if (result.isPresent())
                { return result; }
            }
        }
        return result;
    }
}
