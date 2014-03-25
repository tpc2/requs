/**
 * Copyright (c) 2009-2014, requs.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the requs.org nor
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
package org.requs;

import com.rexsl.test.XhtmlMatchers;
import java.io.IOException;
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
     * Spec.Ultimate can parse input text and produce XML.
     * @throws IOException When necessary
     */
    @Test
    public void parsesInputAndProducesXml() throws IOException {
        MatcherAssert.assertThat(
            new Spec.Ultimate(
                IOUtils.toString(
                    this.getClass().getResourceAsStream("syntax/all-cases.req")
                )
            ).xml(),
            XhtmlMatchers.hasXPaths(
                "/spec/types",
                "/spec/requs[version and revision and date]",
                "/spec/build[duration and time]",
                "/spec/metrics/ambiguity.overall[. > 0]",
                "//method/attributes"
            )
        );
    }

    /**
     * Spec.Ultimate can parse all possible errors.
     * @throws Exception When necessary
     */
    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void parsesAllPossibleErrors() throws Exception {
        final String[] specs = {
            "\"alpha",
            "User is a",
            "User needs: a as",
            "UC1 where",
        };
        for (final String spec : specs) {
            MatcherAssert.assertThat(
                new Spec.Ultimate(spec).xml(),
                XhtmlMatchers.hasXPath("/spec/errors/error")
            );
        }
    }

}
