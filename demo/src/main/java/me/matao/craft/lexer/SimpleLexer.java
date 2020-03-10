package me.matao.craft.lexer;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.*;

public class SimpleLexer {

    private List<Token> tokens; // 保存解析数来的token
    private StringBuilder tokenText; // 保存当前正在解析的token文本
    private SimpleToken token;  // 当前正在解析的token

    /**
     * 有限状态机进入初始状态。
     * 这个初始状态其实并不做停留，它马上进入其他状态。
     * <p>
     * 开始解析的时候，进入初始状态；
     * 某个Token解析完毕，也进入初始状态，在这里把Token记下来，然后建立一个新的Token。
     */
    private DfaState initToken(char ch) {
        if (tokenText.length() > 0) {
            token.setText(tokenText.toString());
            tokens.add(token);

            tokenText = new StringBuilder();
            token = new SimpleToken();
        }

        DfaState newState = DfaState.Initial;
        if (isLetter(ch)) {     // 第一个字符是字母
            if (ch == 'i') {
                newState = DfaState.Id_int1;
            } else {
                newState = DfaState.Id;
            }
            token.setType(TokenType.Identifier);
            tokenText.append(ch);
        } else if (isDigit(ch)) {   // 第一个字符是数字
            newState = DfaState.IntLiteral;
            token.setType(TokenType.IntLiteral);
            tokenText.append(ch);
        } else if (ch == '>') {     // //第一个字符是 >
            newState = DfaState.GT;
            token.setType(TokenType.GT);
            tokenText.append(ch);
        } else if (ch == '+') {
            newState = DfaState.Plus;
            token.setType(TokenType.Plus);
            tokenText.append(ch);
        } else if (ch == '-') {
            newState = DfaState.Minus;
            token.setType(TokenType.Minus);
            tokenText.append(ch);
        } else if (ch == '*') {
            newState = DfaState.Multiply;
            token.setType(TokenType.Multiply);
            tokenText.append(ch);
        } else if (ch == '/') {
            newState = DfaState.Divide;
            token.setType(TokenType.Divide);
            tokenText.append(ch);
        } else if (ch == ';') {
            newState = DfaState.SemiColon;
            token.setType(TokenType.SemiColon);
            tokenText.append(ch);
        } else if (ch == '(') {
            newState = DfaState.LeftParen;
            token.setType(TokenType.LeftParen);
            tokenText.append(ch);
        } else if (ch == ')') {
            newState = DfaState.RightParen;
            token.setType(TokenType.RightParen);
            tokenText.append(ch);
        } else if (ch == '=') {
            newState = DfaState.Assignment;
            token.setType(TokenType.Assignment);
            tokenText.append(ch);
        }
        return newState;
    }

    /**
     * 解析字符串，形成Token。
     * 这是一个有限状态自动机，在不同的状态中迁移。
     */
    public SimpleTokenReader tokenize(String code) {
        tokens = new ArrayList<>();
        tokenText = new StringBuilder();
        token = new SimpleToken();

        CharArrayReader reader = new CharArrayReader(code.toCharArray());

        int ich = 0;
        char ch = 0;
        DfaState state = DfaState.Initial;
        try {
            while ((ich = reader.read()) != -1) {
                ch = (char) ich;

                // state 为当前状态，ch为下一个字符。此处处理状态迁移
                switch (state) {
                    case Initial:
                    case GE:
                    case Assignment:
                    case Plus:
                    case Minus:
                    case Multiply:
                    case Divide:
                    case SemiColon:
                    case LeftParen:
                    case RightParen:
                        state = initToken(ch);
                        break;
                    case Id:
                        if (isLetter(ch) || isDigit(ch)) {
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case GT:
                        if (ch == '=') {
                            token.setType(TokenType.GE);
                            state = DfaState.GE;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case IntLiteral:
                        if (isDigit(ch)) {
                            tokenText.append(ch);   // 继续保持在数字字面量状态
                        } else {
                            state = initToken(ch);  // 退出当前状态，并保存Token
                        }
                        break;
                    case Id_int1:
                        if (ch == 'n') {
                            state = DfaState.Id_int2;
                            tokenText.append(ch);
                        } else if (isDigit(ch) || isLetter(ch)) {
                            state = DfaState.Id;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case Id_int2:
                        if (ch == 't') {
                            state = DfaState.Id_int3;
                            tokenText.append(ch);
                        } else if (isDigit(ch) || isLetter(ch)) {
                            state = DfaState.Id;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case Id_int3:
                        if (isWhitespace(ch)) {
                            token.setType(TokenType.Int);
                            state = initToken(ch);
                        } else {
                            state = DfaState.Id;
                            tokenText.append(ch);
                        }
                        break;
                    default:
                }
            }

            // 把最后一个token送进去
            if (tokenText.length() > 0) {
                initToken(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SimpleTokenReader(tokens);
    }

    public void dump(SimpleTokenReader tokenReader) {
        System.out.println("text\ttype");
        Token token = null;
        while ((token = tokenReader.read()) != null) {
            System.out.println(token.getText() + "\t\t" + token.getType());
        }
    }
}
