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
package org.requs.facet.tbd;

import com.jcabi.matchers.XhtmlMatchers;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.requs.Docs;

/**
 * Test case for {@link TBDs}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.3
 * @checkstyle MultipleStringLiteralsCheck (500 lines)
 */
public final class TBDsTest {

    /**
     * Temporary folder.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @Rule
    public transient TemporaryFolder temp = new TemporaryFolder();

    /**
     * TBDs can find TBDs.
     * @throws IOException If fails
     */
    @Test
    public void findsTbds() throws IOException {
        final Docs docs = new Docs.InDir(this.temp.newFolder());
        docs.get("index.xml").write("<index/>");
        docs.get("main.xml").write(
            IOUtils.toString(
                this.getClass().getResourceAsStream("example.xml")
            )
        );
        new TBDs().touch(docs);
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(docs.get("tbds.xml").read()),
            XhtmlMatchers.hasXPaths(
                "/tbds[count(tbd)=8]",
                "/tbds/tbd[@id='TBD-c0bf5']",
                "/tbds/tbd[@id='TBD-247ba']"
            )
        );
    }

    /**
     * TBDs can produce content-independent identifiers.
     * @throws IOException If fails
     */
    @Test
    public void identifiersDontDependOnContent() throws IOException {
        final Docs docs = new Docs.InDir(this.temp.newFolder());
        docs.get("index.xml").write("<index/>");
        docs.get("main.xml").write(
            StringUtils.join(
                "<spec><methods><method><id>UC1</id>",
                "<info><informal>aa</informal></info></method>",
                "<method><id>UC1</id>",
                "<info><informal>bb</informal></info></method>",
                "</methods></spec>"
            )
        );
        new TBDs().touch(docs);
        MatcherAssert.assertThat(
            XhtmlMatchers.xhtml(docs.get("tbds.xml").read()),
            XhtmlMatchers.hasXPaths(
                "/tbds[count(tbd)=2]",
                "/tbds/tbd[@id = 'TBD-f777a']",
                "/tbds[tbd[1]/@id = tbd[2]/@id]"
            )
        );
    }

}
