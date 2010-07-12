/**
 * RQDQL.com, Requirements Definition and Query Language
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are PROHIBITED without prior written 
 * permission from the author. This product may NOT be used 
 * anywhere and on any computer except the server platform of 
 * rqdql.com. located at www.rqdql.com. If you received this 
 * code occasionally and without intent to use it, please report 
 * this incident to the author by email: team@rqdql.com
 *
 * @author Yegor Bugayenko <egor@tpc2.com>
 * @copyright Copyright (c) rqdql.com, 2010
 * @version $Id$
 */

#include <boost/test/unit_test.hpp>
#include "Xml/Document.h"
#include "Xml/Node.h"
#include "Xml/Attribute.h"
using namespace Xml;

BOOST_AUTO_TEST_SUITE(NodeTest)

BOOST_AUTO_TEST_CASE(testNodesCanBeSetFast) {
    Document doc;
    Node root = doc.root("test");
    for (int i = 0; i < 50; i++) {
        root + "employee" = i;
    }
}

BOOST_AUTO_TEST_SUITE_END()
