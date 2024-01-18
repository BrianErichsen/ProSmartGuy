#include "expr.h"
#include<string>
//Initialization list - member value with arg val
Num::Num(int val) : value(val) {}

bool Num::equals(const Expr* other) const {
    //dynamic cast converts the pointer from other to Num type
    //if successful meaning that class is Num or derived from it means
    //that otherNum will have a valid pointer hence if --then true
    if (const Num* otherNum = dynamic_cast<const Num*>(other)) {
        //if current instace of num val == other val then true
        return value == otherNum->value;
    }
    return false;
}
int Num::eval() const {
        return value;
}

VarExpr::VarExpr(const std::string& name) : varName(name) {}

int VarExpr::eval() const {
    //for now returning 0
    return 0;
}

bool VarExpr::equals(const Expr* other) const {
    if (const VarExpr* otherVarExpr = dynamic_cast
    <const VarExpr*>(other)) {
        return varName == otherVarExpr->varName;
    }
    return false;
}


Add::Add(Expr* l, Expr* r) : left(l), right(r) {}

int Add::eval() const {
    return left->eval() + right->eval();
}
bool Add::equals(const Expr* other) const {
    //first check for a valid pointer to see if classes match
    if (const Add* otherAdd = dynamic_cast<const Add*>(other)) {
        //recursively compares other left with current left and same for right
        return left->equals(otherAdd->left) && right->equals(otherAdd->right);
    }
    return false;
}

Add::~Add() {
    delete left;
    delete right;
}
int Expr::interpret(Expr* expr) {
    return expr->eval();
}