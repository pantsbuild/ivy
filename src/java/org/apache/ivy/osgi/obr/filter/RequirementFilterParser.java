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

import java.text.ParseException;

import org.apache.ivy.osgi.obr.filter.CompareFilter.Operator;
import org.apache.ivy.osgi.obr.xml.RequirementFilter;

public class RequirementFilterParser {

    public static RequirementFilter parse(String text) throws ParseException {
        return new Parser(text).parse();
    }

    static class Parser {

        /**
         * text to parse
         */
        private final String text;

        /**
         * the length of the source
         */
        private int length;

        /**
         * position in the source
         */
        private int pos = 0;

        /**
         * last read character
         */
        private char c;

        /**
         * Default constructor
         * 
         * @param header
         *            the header to parse
         */
        Parser(String text) {
            this.text = text;
            this.length = text.length();
        }

        /**
         * Do the parsing
         * 
         * @return
         * 
         * @throws ParseException
         */
        RequirementFilter parse() throws ParseException {
            return parseFilter();
        }

        private char readNext() {
            if (pos == length) {
                c = '\0';
            } else {
                c = text.charAt(pos++);
            }
            return c;
        }

        private void unread() {
            if (pos > 0) {
                pos--;
            }
        }

        private RequirementFilter parseFilter() throws ParseException {
            skipWhiteSpace();
            readNext();
            if (c != '(') {
                throw new ParseException("Expecting '(' as the start of the filter", pos);
            }
            RequirementFilter filter;
            switch (readNext()) {
                case '&':
                    filter = parseAnd();
                    break;
                case '|':
                    filter = parseOr();
                    break;
                case '!':
                    filter = parseNot();
                    break;
                default:
                    unread();
                    filter = parseCompare();
                    break;
            }
            readNext();
            if (c != ')') {
                throw new ParseException("Expecting ')' as the end of the filter", pos);
            }
            return filter;
        }

        private RequirementFilter parseCompare() throws ParseException {
            String leftValue = parseCompareValue();
            Operator operator = parseCompareOperator();
            String rightValue = parseCompareValue();
            return new CompareFilter(leftValue, operator, rightValue);
        }

        private String parseCompareValue() {
            StringBuffer builder = new StringBuffer();
            do {
                readNext();
                if (!isOperator(c) && c != ')' && c != '(') {
                    builder.append(c);
                } else {
                    unread();
                    break;
                }
            } while (pos < length);
            return builder.toString();
        }

        private boolean isOperator(char ch) {
            return ch == '=' || ch == '<' || ch == '>';
        }

        private Operator parseCompareOperator() throws ParseException {
            switch (readNext()) {
                case '=':
                    return Operator.EQUALS;
                case '>':
                    if (readNext() == '=') {
                        return Operator.GREATER_OR_EQUAL;
                    }
                    unread();
                    return Operator.GREATER_THAN;
                case '<':
                    if (readNext() == '=') {
                        return Operator.LOWER_OR_EQUAL;
                    }
                    unread();
                    return Operator.LOWER_THAN;
                default:
                    break;
            }
            throw new ParseException("Expecting an operator: =, <, <=, > or >=", pos);
        }

        private RequirementFilter parseAnd() throws ParseException {
            AndFilter filter = new AndFilter();
            parseMultiOperator(filter);
            return filter;
        }

        private RequirementFilter parseOr() throws ParseException {
            OrFilter filter = new OrFilter();
            parseMultiOperator(filter);
            return filter;
        }

        private void parseMultiOperator(MultiOperatorFilter filter) throws ParseException {
            do {
                skipWhiteSpace();
                readNext();
                if (c == '(') {
                    unread();
                    filter.add(parseFilter());
                } else {
                    unread();
                    break;
                }
            } while (pos < length);
            if (filter.getSubFilters().size() == 0) {
                throw new ParseException("Expecting at least one sub filter", pos);
            }
        }

        private RequirementFilter parseNot() throws ParseException {
            readNext();
            if (c != '(') {
                throw new ParseException("The ! operator is expecting a filter", pos);
            }
            unread();
            return new NotFilter(parseFilter());
        }

        private void skipWhiteSpace() {
            do {
                switch (readNext()) {
                    case ' ':
                        continue;
                    default:
                        unread();
                        return;
                }
            } while (pos < length);
        }

    }

}
