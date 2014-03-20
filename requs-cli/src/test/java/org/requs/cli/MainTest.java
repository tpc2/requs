/**
 * Copyright (c) 2009-2013, requs.org
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
package org.regus.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link Main}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class MainTest {

    /**
     * Temporary folder.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @Rule
    public transient TemporaryFolder temp = new TemporaryFolder();

    /**
     * Output stream for tests.
     */
    private transient ByteArrayOutputStream out;

    /**
     * Change system output stream.
     * @throws Exception When necessary
     */
    @Before
    public void changeSystemOutputSteam() throws Exception {
        this.out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out, true));
    }

    /**
     * Change system output stream back.
     * @throws Exception When necessary
     */
    @After
    public void revertChangedSystemOutputSteam() throws Exception {
        System.setOut(null);
    }

    /**
     * Main can work.
     * @throws Exception When necessary
     */
    @Test(expected = IllegalAccessException.class)
    public void testMakesAnIncorrectAttemptToInstantiateClass()
        throws Exception {
        final String name = "org.regus.cli.Main";
        final Class<?> cls = Class.forName(name);
        MatcherAssert.assertThat(cls, Matchers.notNullValue());
        cls.newInstance();
    }

    /**
     * Main can show the version of the application.
     * @throws Exception When necessary
     */
    @Test
    @Ignore
    public void displaysVersionNumber() throws Exception {
        Main.main(new String[] {"-v"});
        MatcherAssert.assertThat(
            this.out.toString(),
            Matchers.containsString("-SNAPSHOT")
        );
    }

    /**
     * Main can show a help message.
     * @throws Exception When necessary
     */
    @Test
    @Ignore
    public void rendersHelpMessage() throws Exception {
        Main.main(new String[] {"-h"});
        MatcherAssert.assertThat(
            this.out.toString(),
            Matchers.containsString("Usage:")
        );
    }

}
