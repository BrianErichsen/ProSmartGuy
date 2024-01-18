// #define CATCH_CONFIG_RUNNER
#include "catch.h"
#include "expr.h"

TEST_CASE("Equals method tests") {
    SECTION("Expr equals") {
        CHECK((new VarExpr("x"))->equals(new VarExpr("x")) == true);
    }
}