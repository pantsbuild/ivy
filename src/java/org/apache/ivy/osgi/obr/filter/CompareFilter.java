/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivy.osgi.obr.filter;

import org.apache.ivy.osgi.obr.xml.RequirementFilter;

public class CompareFilter extends RequirementFilter {

    // enum 1.5 wrote in java 1.4
    public static class Operator {

        public static Operator EQUALS = new Operator();

        public static Operator LOWER_THAN = new Operator();

        public static Operator LOWER_OR_EQUAL = new Operator();

        public static Operator GREATER_THAN = new Operator();

        public static Operator GREATER_OR_EQUAL = new Operator();

        public String toString() {
            if (this == EQUALS)
                return "=";
            if (this == GREATER_THAN)
                return ">";
            if (this == GREATER_OR_EQUAL)
                return ">=";
            if (this == LOWER_THAN)
                return "<";
            if (this == LOWER_OR_EQUAL)
                return "<=";
            return super.toString();
        }
    }

    private Operator operator;

    private final String rightValue;

    private final String leftValue;

    public CompareFilter(String leftValue, Operator operator, String rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.operator = operator;
    }

    public String getLeftValue() {
        return leftValue;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getRightValue() {
        return rightValue;
    }

    public void append(StringBuffer builder) {
        builder.append("(");
        builder.append(leftValue);
        builder.append(operator.toString());
        builder.append(rightValue);
        builder.append(")");
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leftValue == null) ? 0 : leftValue.hashCode());
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + ((rightValue == null) ? 0 : rightValue.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompareFilter)) {
            return false;
        }
        CompareFilter other = (CompareFilter) obj;
        if (leftValue == null) {
            if (other.leftValue != null) {
                return false;
            }
        } else if (!leftValue.equals(other.leftValue)) {
            return false;
        }
        if (operator == null) {
            if (other.operator != null) {
                return false;
            }
        } else if (!operator.equals(other.operator)) {
            return false;
        }
        if (rightValue == null) {
            if (other.rightValue != null) {
                return false;
            }
        } else if (!rightValue.equals(other.rightValue)) {
            return false;
        }
        return true;
    }

}
