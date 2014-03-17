/**
 * Copyright (c) 2009-2013, RQDQL.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the RQDQL.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rqdql.syntax;

import com.rexsl.test.XhtmlMatchers;
import org.apache.commons.io.IOUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link Spec}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class SpecTest {

    /**
     * Spec can parse input text and produce clauses.
     * @throws Exception When necessary
     */
    @Test
    public void parsesInputAndProducesTypes() throws Exception {
        MatcherAssert.assertThat(
            new Spec("SuD includes: test.").xml(),
            XhtmlMatchers.hasXPaths("/spec/a")
        );
    }

    /**
     * Spec can compile a complex document.
     * @throws Exception When necessary
     */
    @Test
    public void compilesComplexSpec() throws Exception {
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(
                new Spec(
                    IOUtils.toString(
                        this.getClass().getResourceAsStream("example.rqdql")
                    )
                ).xml()
            ),
            XhtmlMatchers.hasXPaths(
                "/spec/types/type[name='SuD']",
                "/spec/types/type[name='Fraction']/info[informal='a math calculator']"
            )
        );
    }

    /**
     * Main can compile a more complex document(s).
     * @throws Exception When necessary
     */
    @Test
    public void compilesANumberOfSpecifications() throws Exception {
        final String[] files = {
        };
        for (final String file : files) {
            this.parse(file);
        }
    }

    /**
     * Parse resource file.
     * @param file The file name (resource)
     * @throws Exception When necessary
     */
    private void parse(final String file) throws Exception {
        MatcherAssert.assertThat(
            new Spec(
                IOUtils.toString(this.getClass().getResourceAsStream(file))
            ).xml(),
            XhtmlMatchers.hasXPath("/spec")
        );
    }
}