package com.mockneat.random.unit.time;

/**
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.mockneat.random.Rand;
import com.mockneat.random.interfaces.RandUnitMonth;

import java.time.Month;
import java.util.function.Supplier;

import static com.mockneat.random.utils.ValidationUtils.*;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Months implements RandUnitMonth {

    private Rand rand;

    public Months(Rand rand) {
        this.rand = rand;
    }

    @Override
    public Supplier<Month> supplier() {
        return rand.from(Month.class)::val;
    }

    public RandUnitMonth rangeClosed(Month lower, Month upper) {
        notNull(lower, INPUT_PARAMETER_NOT_NULL, "lower");
        notNull(upper, INPUT_PARAMETER_NOT_NULL, "upper");
        isTrue(lower.getValue() < upper.getValue(), UPPER_MONTH_BIGGER_THAN_LOWER);
        Supplier<Month> supp = () -> {
            int idx = rand.ints().range(lower.getValue()-1, upper.getValue()).val();
            return Month.values()[idx];
        };
        return () -> supp;
    }

    public RandUnitMonth range(Month lower, Month upper) {
        notNull(lower, INPUT_PARAMETER_NOT_NULL, "lower");
        notNull(upper, INPUT_PARAMETER_NOT_NULL, "upper");
        isTrue(lower.getValue() < upper.getValue(), UPPER_MONTH_BIGGER_THAN_LOWER);
        Supplier<Month> supp = () -> {
            int idx = rand.ints().range(lower.getValue()-1, upper.getValue()-1).val();
            return Month.values()[idx];
        };
        return () -> supp;
    }

    public RandUnitMonth before(Month before) {
        notNull(before, INPUT_PARAMETER_NOT_NULL, "before");
        isTrue(before.getValue()-1>0, BEFORE_MONTH_DIFFERENT_THAN_JANUARY);
        return range(Month.values()[0], before);
    }

    public RandUnitMonth after(Month after) {
        notNull(after, INPUT_PARAMETER_NOT_NULL, "after");
        isTrue(after.getValue()<Month.values().length-1, AFTER_MONTH_DIFFERENT_TNAN_DECEMBER);
        Supplier<Month> supp = () -> {
            int idx = rand.ints().range(after.getValue(), Month.values().length).val();
            return Month.values()[idx];
        };
        return () -> supp;
    }
}
