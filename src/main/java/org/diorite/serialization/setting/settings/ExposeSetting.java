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

package org.diorite.serialization.setting.settings;

import org.diorite.serialization.annotations.Expose;
import org.diorite.serialization.setting.Setting;

/**
 * Representation of {@link Expose}
 */
public final class ExposeSetting implements Setting, Expose
{
    private final boolean serialize;
    private final boolean deserialize;

    public ExposeSetting(boolean serialize, boolean deserialize)
    {
        this.serialize = serialize;
        this.deserialize = deserialize;
    }

    @Override
    public boolean serialize()
    {
        return serialize;
    }

    @Override
    public boolean deserialize()
    {
        return deserialize;
    }

    @Override
    public Class<Expose> annotationType()
    {
        return Expose.class;
    }

}
