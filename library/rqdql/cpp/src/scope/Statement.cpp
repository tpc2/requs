/**
 *
 * FaZend.com, Fully Automated Zend Framework
 * RQDQL.com, Requirements Definition and Query Language
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are PROHIBITED without prior written 
 * permission from the author. This product may NOT be used 
 * anywhere and on any computer except the server platform of 
 * FaZend.com. located at www.fazend.com. If you received this 
 * code occasionally and without intent to use it, please report 
 * this incident to the author by email: team@rqdql.com
 *
 * @author Yegor Bugayenko <egor@tpc2.com>
 * @copyright Copyright (c) rqdql.com, 2010
 * @version $Id$
 */

#include <vector>
#include <string>
#include "pugixml/pugixml.hpp"
#include "rqdql.h"
#include "scope/Statement.h"

void rqdql::scope::Statement::setStartLineNo(int lineNo) {
    this->startLineNo = lineNo;
}

void rqdql::scope::Statement::setEndLineNo(int lineNo) {
    this->endLineNo = lineNo;
}
