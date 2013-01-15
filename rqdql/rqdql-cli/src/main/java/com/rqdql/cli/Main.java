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
package com.rqdql.cli;

import com.jcabi.manifests.Manifests;
import com.rqdql.semantic.Model;
import com.rqdql.syntax.SRS;
import com.rqdql.uml.UML;
import java.util.Map;
import joptsimple.HelpFormatter;
import joptsimple.OptionDescriptor;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.commons.io.IOUtils;

/**
 * Entry point of the JAR.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class Main {

    /**
     * Formatter of options.
     */
    private static final HelpFormatter FORMATTER = new HelpFormatter() {
        @Override
        public String format(final
            Map<String, ? extends OptionDescriptor> map) {
            final StringBuilder text = new StringBuilder();
            text.append("Usage: java -jar ")
                .append("rqdql-cli.jar")
                .append(" [-options] < input > output\n")
                .append("where options include:\n");
            for (Map.Entry<String, ? extends OptionDescriptor> entry
                : map.entrySet()) {
                text.append("    -")
                    .append(entry.getKey())
                    .append(entry.getValue().description())
                    .append('\n');
            }
            return text.toString();
        }
    };

    /**
     * Private ctor, to avoid instantiation of this class.
     */
    private Main() {
        // intentionally empty
    }

    /**
     * Entry point of the entire JAR.
     * @param args List of command-line arguments
     * @throws Exception If something goes wrong inside
     */
    public static void main(final String[] args) throws Exception {
        final OptionParser parser = new OptionParser("vh");
        final OptionSet options = parser.parse(args);
        String out;
        if (options.has("v")) {
            IOUtils.write(
                String.format(
                    "%s/%s",
                    Manifests.read("RQDQL-Version"),
                    Manifests.read("RQDQL-Build")
                ),
                System.out
            );
        } else if (options.has("h")) {
            parser.formatHelpWith(Main.FORMATTER);
            parser.printHelpOn(System.out);
        } else {
            IOUtils.write(
                new UML(
                    new Model(
                        new SRS(IOUtils.toString(System.in)).clauses()
                    ).sud()
                ).xmi(),
                System.out
            );
        }
    }

}
