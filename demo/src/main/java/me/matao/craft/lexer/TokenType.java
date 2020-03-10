package me.matao.craft.lexer;

public enum TokenType {
    Plus,       // +
    Minus,      // -
    Multiply,   // *
    Divide,     // /

    GE, // >=
    GT, // >
    EQ, // ==
    LE, // <=
    LT, // <

    SemiColon,  // ;
    LeftParen,  // (
    RightParen, // )

    Assignment, // =

    If,
    Else,

    Int,

    Identifier, // 标示符

    IntLiteral,     // int 字面量
    StringLiteral,  // String 字面量
}
