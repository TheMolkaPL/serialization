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

import org.diorite.serialization.annotations.Expose;
import org.diorite.serialization.annotations.SerializedName;
import org.diorite.serialization.annotations.Since;
import org.diorite.serialization.annotations.Until;
import org.diorite.serialization.setting.settings.ExposeSetting;
import org.diorite.serialization.setting.settings.SerializedNameSetting;
import org.diorite.serialization.setting.settings.SinceSetting;
import org.diorite.serialization.setting.settings.UntilSetting;

import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manager of settings readers, allows to register new readers to provide own settings.
 */
public class SettingsManager
{
    public static final SettingsManager INSTANCE = new SettingsManager();

    private final Map<Class<? extends Setting>, MultiSettingReader<? extends Setting>> settings;

    SettingsManager(@Nullable Void v)
    {
        settings = Collections.emptyMap();
        // do nothing.
    }

    // default to prevent subtyping.
    SettingsManager()
    {
        settings = new HashMap<>();
        register(ExposeSetting.class, annotatedElement ->
        {
            Expose annotation = annotatedElement.getAnnotation(Expose.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new ExposeSetting(annotation.serialize(), annotation.deserialize()));
        });
        register(UntilSetting.class, annotatedElement ->
        {
            Until annotation = annotatedElement.getAnnotation(Until.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new UntilSetting(annotation.value()));
        });
        register(SinceSetting.class, annotatedElement ->
        {
            Since annotation = annotatedElement.getAnnotation(Since.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new SinceSetting(annotation.value()));
        });
        register(SerializedNameSetting.class, annotatedElement ->
        {
            SerializedName annotation = annotatedElement.getAnnotation(SerializedName.class);
            if (annotation == null)
            { return Optional.empty(); }
            return Optional.of(new SerializedNameSetting(annotation.value(), annotation.alternate()));
        });
        GsonSettingReaders.add(this);
    }

    /**
     * Returns new instance of settings manager.
     *
     * @return new instance of settings manager.
     */
    public static SettingsManager create()
    {
        return new SettingsManager();
    }

    /**
     * Register new setting reader for given type.
     *
     * @param type
     *     type of setting object.
     * @param settingReader
     *     reader instance.
     * @param <T>
     *     type of setting object.
     */
    @SuppressWarnings("unchecked")
    public <T extends Setting> void register(Class<T> type, SettingReader<T> settingReader)
    {
        MultiSettingReader<T> readers = (MultiSettingReader<T>) settings
            .computeIfAbsent(type, k -> new MultiSettingReader<>(Collections.emptyList()));
        readers.addReader(settingReader);
    }

    /**
     * Returns settings reader for given setting type.
     *
     * @param type
     *     type of setting object.
     * @param <T>
     *     type of setting object.
     *
     * @return settings reader for given setting type.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Setting> SettingReader<T> getSettingsReader(Class<T> type)
    {
        return (SettingReader<T>) settings.get(type);
    }

    /**
     * Tries to read setting from given annotated type, returns null if there is no such setting or given annotated type does not contain
     * it.
     *
     * @param annotatedElement
     *     annotated element instance to read setting from it.
     * @param type
     *     type of setting object.
     * @param <T>
     *     type of setting object.
     *
     * @return read setting object or null.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Setting> T readSetting(AnnotatedElement annotatedElement, Class<T> type)
    {
        SettingReader<T> settingsReader = getSettingsReader(type);
        if (settingsReader == null)
        {
            return null;
        }
        return settingsReader.read(annotatedElement).orElse(null);
    }

    /**
     * Tries to read all registered settings from given annotated type.
     *
     * @param annotatedElement
     *     annotated element instance to read setting from it.
     *
     * @return map of all read settings.
     */
    @SuppressWarnings("unchecked")
    public Map<? extends Class<? extends Setting>, ? extends Setting> readSettings(AnnotatedElement annotatedElement)
    {
        synchronized (settings)
        {
            Map<Class<? extends Setting>, Setting> result = new HashMap<>();
            for (Class<? extends Setting> type : this.settings.keySet())
            {
                result.put(type, readSetting(annotatedElement, type));
            }
            return result;
        }
    }
}
