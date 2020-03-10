package me.matao.craft.lexer;

public interface Token {

    /**
     * 获取 Token 的类型
     */
    TokenType getType();

    /**
     * 获取 Token 的文本
     */
    String getText();
}
