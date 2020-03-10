package me.matao.craft.lexer;

public class SimpleToken implements Token {

    private TokenType type;
    private String text;

    @Override
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
