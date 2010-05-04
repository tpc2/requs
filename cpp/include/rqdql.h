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

#ifndef __INCLUDE_RQDQL_H
#define __INCLUDE_RQDQL_H

// system libraries
#include <iostream>
#include <vector>
#include <string>
#include <exception>

// boost libraries
#include "boost/format.hpp"

void yySet(std::string*&, boost::format);
void yySet(std::string*&, char*&);

typedef union {
    std::string* name;
    int num;
    void* ptr;
} YYSTYPE;

// bison/flex file
// this define will protect us against BISON default YYSTYPE
#define YYSTYPE_IS_DECLARED 1
#define YYSTYPE_IS_TRIVIAL 1
#include "symbols.h"

extern int yyparse();
extern void yyerror(const char *error, ...);
void lyyerror(YYLTYPE t, const char *error, ...);
extern int yylex(void);
extern int yylineno;

namespace rqdql {
    
    /**
     * Different levels of logging
     */
    enum LogLevel {
        L_DEBUG   = 1,
        L_VERBOSE = 2,
        L_INFO    = 3,
        L_WARNING = 4,
        L_ERROR   = 5
    };
    extern LogLevel level;
    
    /**
     * Simple logger, that filters messages by their types
     * @see LogLevel
     * @see rqdql.l
     */
    void log(LogLevel, const std::string&);
    void log(LogLevel, const boost::format&);
    void log(const std::string&);
    void log(const boost::format&);
    
    /* exception */
    class Exception {
    public:
        Exception(const std::string& s) : msg(s) { /* that's it */ }
        Exception(const boost::format& s) : msg(s.str()) { /* that's it */ }
        std::string getMessage() const { return msg; }
    private:
        std::string msg;
    };

}

#endif
