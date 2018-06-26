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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import org.diorite.serialization.setting.settings.ExposeSetting;
import org.diorite.serialization.setting.settings.SerializedNameSetting;
import org.diorite.serialization.setting.settings.SinceSetting;
import org.diorite.serialization.setting.settings.UntilSetting;

import java.util.Optional;

class GsonSettingReaders
{
    static void add(SettingsManager manager)
    {
        manager.register(ExposeSetting.class, annotatedElement ->
        {
            Expose annotation = annotatedElement.getAnnotation(Expose.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new ExposeSetting(annotation.serialize(), annotation.deserialize()));
        });
        manager.register(UntilSetting.class, annotatedElement ->
        {
            Until annotation = annotatedElement.getAnnotation(Until.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new UntilSetting(Double.toString(annotation.value())));
        });
        manager.register(SinceSetting.class, annotatedElement ->
        {
            Since annotation = annotatedElement.getAnnotation(Since.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new SinceSetting(Double.toString(annotation.value())));
        });
        manager.register(SerializedNameSetting.class, annotatedElement ->
        {
            SerializedName annotation = annotatedElement.getAnnotation(SerializedName.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new SerializedNameSetting(annotation.value(), annotation.alternate()));
        });
    }
}
