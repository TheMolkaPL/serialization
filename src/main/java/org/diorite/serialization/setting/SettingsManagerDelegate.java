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
import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public final class SettingsManagerDelegate extends SettingsManager
{
    private SettingsManager delegate;

    public SettingsManagerDelegate(SettingsManager delegate)
    {
        super(null);
        this.delegate = delegate;
    }

    public SettingsManager getDelegate()
    {
        return delegate;
    }

    public void setDelegate(SettingsManager delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public <T extends Setting> void register(Class<T> type, SettingReader<T> settingReader)
    {
        delegate.register(type, settingReader);
    }

    @Override
    @Nullable
    public <T extends Setting> SettingReader<T> getSettingsReader(Class<T> type)
    {
        return delegate.getSettingsReader(type);
    }

    @Override
    @Nullable
    public <T extends Setting> T readSetting(AnnotatedElement annotatedElement, Class<T> type)
    {
        return delegate.readSetting(annotatedElement, type);
    }

    @Override
    public Map<? extends Class<? extends Setting>, ? extends Setting> readSettings(AnnotatedElement annotatedElement)
    {
        return delegate.readSettings(annotatedElement);
    }
}
