/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tck.java.time.calendar;

import static java.time.temporal.ChronoField.ERA;

import java.util.Locale;

import java.time.DateTimeException;
import java.time.temporal.ChronoField;
import java.time.temporal.Queries;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.time.temporal.ChronoLocalDate;
import java.time.temporal.Era;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

/**
 * An era in the Coptic calendar system.
 * <p>
 * The Coptic calendar system uses the 'Era of the Martyrs'.
 * The start of the Coptic epoch {@code 0001-01-01 (Coptic)} is {@code 0284-08-29 (ISO)}.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code CopticEra}.
 * Use {@code getValue()} instead.</b>
 *
 * <h4>Implementation notes</h4>
 * This is an immutable and thread-safe enum.
 */
enum CopticEra implements Era<CopticChrono> {

    /**
     * The singleton instance for the era BEFORE_AM, 'Before Era of the Martyrs'.
     * This has the numeric value of {@code 0}.
     */
    BEFORE_AM,
    /**
     * The singleton instance for the era AM, 'Era of the Martyrs'.
     * This has the numeric value of {@code 1}.
     */
    AM;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code CopticEra} from an {@code int} value.
     * <p>
     * {@code CopticEra} is an enum representing the Coptic eras of BEFORE_AM/AM.
     * This factory allows the enum to be obtained from the {@code int} value.
     *
     * @param era  the BEFORE_AM/AM value to represent, from 0 (BEFORE_AM) to 1 (AM)
     * @return the era singleton, not null
     * @throws DateTimeException if the value is invalid
     */
    public static CopticEra of(int era) {
        switch (era) {
            case 0:
                return BEFORE_AM;
            case 1:
                return AM;
            default:
                throw new DateTimeException("Invalid era: " + era);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the numeric era {@code int} value.
     * <p>
     * The era BEFORE_AM has the value 0, while the era AM has the value 1.
     *
     * @return the era value, from 0 (BEFORE_AM) to 1 (AM)
     */
    public int getValue() {
        return ordinal();
    }

    @Override
    public CopticChrono getChrono() {
        return CopticChrono.INSTANCE;
    }

    // JDK8 default methods:
    //-----------------------------------------------------------------------
    @Override
    public ChronoLocalDate<CopticChrono> date(int year, int month, int day) {
        return getChrono().date(this, year, month, day);
    }

    @Override
    public ChronoLocalDate<CopticChrono> dateYearDay(int year, int dayOfYear) {
        return getChrono().dateYearDay(this, year, dayOfYear);
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == ERA;
        }
        return field != null && field.doIsSupported(this);
    }

    @Override
    public ValueRange range(TemporalField field) {
        if (field == ERA) {
            return field.range();
        } else if (field instanceof ChronoField) {
            throw new DateTimeException("Unsupported field: " + field.getName());
        }
        return field.doRange(this);
    }

    @Override
    public int get(TemporalField field) {
        if (field == ERA) {
            return getValue();
        }
        return range(field).checkValidIntValue(getLong(field), field);
    }

    @Override
    public long getLong(TemporalField field) {
        if (field == ERA) {
            return getValue();
        } else if (field instanceof ChronoField) {
            throw new DateTimeException("Unsupported field: " + field.getName());
        }
        return field.doGet(this);
    }

    //-------------------------------------------------------------------------
    @Override
    public Temporal adjustInto(Temporal dateTime) {
        return dateTime.with(ERA, getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(TemporalQuery<R> query) {
        if (query == Queries.zoneId()) {
            return null;
        } else if (query == Queries.chrono()) {
            return (R) getChrono();
        }
        return query.queryFrom(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public String getText(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(ERA, style).toFormatter(locale).print(this);
    }

}
