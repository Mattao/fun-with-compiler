package me.matao.craft.lexer;

import java.util.List;

public class SimpleTokenReader implements TokenReader {

    private List<Token> tokens;
    private int position;

    public SimpleTokenReader(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Token read() {
        if (position < tokens.size()) {
            return tokens.get(position++);
        }
        return null;
    }

    @Override
    public Token peek() {
        if (position < tokens.size()) {
            return tokens.get(position);
        }
        return null;
    }

    @Override
    public void unread() {
        if (position > 0) {
            position--;
        }
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        if (position >= 0 && position < tokens.size()) {
            this.position = position;
        }
    }
}
