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

#ifndef __INCLUDE_SCOPE_FRONT_LINKS_H
#define __INCLUDE_SCOPE_FRONT_LINKS_H

#include <string>
#include "Front/Reporter.h"
#include "Xml/Node.h"

namespace front {
    
/**
 * All links between objects in scope
 */
class Links : public Reporter {

public:

    /**
     * Public constructor
     */
    Links(const Params& p) : Reporter(p) { /* that's it */ }

    /**
     * Virtual method called from Reporter::append().
     * XML element <report> is passed here and we shall fill it with our
     * own specific data.
     */
    void fill(Xml::Node&) const;

private:

    template<typename T> void addLocations(Xml::Node&, const std::string&) const;

};
    
}

#endif