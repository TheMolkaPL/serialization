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

package org.diorite.serialization.version;

/**
 * Version comparator is used by serialization to decide if given version is after or before given one.
 */
public interface VersionComparator
{
    /**
     * Simple version comparator that supports only simple numeric versions like "1.10.4".
     */
    VersionComparator SIMPLE_NUMERIC = new SimpleNumericVersionComparator();

    /**
     * Default version comparator that support default version format, currently it supports only simple numeric versions like "1.10.4",
     * but later support for additional version formats might be added.
     */
    VersionComparator DEFAULT = new DefaultVersionComparator(VersionComparator.SIMPLE_NUMERIC);

    /**
     * Returns true if given version to compare is after to base version.
     *
     * @param baseVersion
     *     base version.
     * @param toCompareIfAfter
     *     version that will be compared with base version.
     *
     * @return true if given version to compare is after to base version.
     */
    default boolean isAfter(String baseVersion, String toCompareIfAfter)
    {
        return compare(baseVersion, toCompareIfAfter) == CompareResult.AFTER;
    }

    /**
     * Returns true if given version to compare is before to base version.
     *
     * @param baseVersion
     *     base version.
     * @param toCompareIfBefore
     *     version that will be compared with base version.
     *
     * @return true if given version to compare is before to base version.
     */
    default boolean isBefore(String baseVersion, String toCompareIfBefore)
    {
        return compare(baseVersion, toCompareIfBefore) == CompareResult.BEFORE;
    }

    /**
     * Returns true if given version to compare is equals to base version.
     *
     * @param baseVersion
     *     base version.
     * @param toCompareIfEquals
     *     version that will be compared with base version.
     *
     * @return true if given version to compare is equals to base version.
     */
    default boolean isEquals(String baseVersion, String toCompareIfEquals)
    {
        return compare(baseVersion, toCompareIfEquals) == CompareResult.EQUALS;
    }

    /**
     * Returns if given version to compare is after, before or equals to base version.
     *
     * @param baseVersion
     *     base version.
     * @param toCompare
     *     version that will be compared with base version.
     *
     * @return if given version to compare is after, before or equals to base version.
     */
    CompareResult compare(String baseVersion, String toCompare);

    /**
     * Represent version compare result.
     */
    enum CompareResult
    {
        AFTER,
        BEFORE,
        EQUALS
    }
}
