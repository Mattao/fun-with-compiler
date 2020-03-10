package me.matao.craft.lexer;

public enum DfaState {
    Initial,

    If,
    Id_if1, Id_if2,
    Else,
    Id_Else1, Id_else2, Id_else3, Id_else4, // else识别过程的特殊状态，对应e/el/els/else
    Int,
    Id_int1, Id_int2, Id_int3,  // int识别过程的特殊状态，对应i/in/int
    Id,
    GT, GE,

    Plus, Minus, Multiply, Divide,

    Assignment,

    SemiColon, LeftParen, RightParen,

    IntLiteral,
}
