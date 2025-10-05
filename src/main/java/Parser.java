import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Scanner scan;
    private Token currentToken;
    private List<String> output = new ArrayList<>();
    
    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }
    
    private void nextToken() {
        currentToken = scan.nextToken();
    }
    
    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error");
        }
    }
    
    void number() {
        output.add("push " + currentToken.lexeme);
        match(TokenType.NUMBER);
    }
    
    void oper() {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            output.add("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            output.add("sub");
            oper();
        }
    }
    
    void expr() {
        term();
        oper();
    }
    
    void term() {
        if (currentToken.type == TokenType.NUMBER)
            number();
        else if (currentToken.type == TokenType.IDENT) {
            output.add("push " + currentToken.lexeme);
            match(TokenType.IDENT);
        }
        else
            throw new Error("syntax error - unexpected token: " + currentToken.type + " (" + currentToken.lexeme + ")");
    }
    
    void letStatement() {
        match(TokenType.LET);
        var id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);
        expr();
        output.add("pop " + id);
        match(TokenType.SEMICOLON);
    }
    
    void printStatement() {
        match(TokenType.PRINT);
        expr();
        output.add("print");
        match(TokenType.SEMICOLON);
    }
    
    void statement() {
        if (currentToken.type == TokenType.PRINT) {
            printStatement();
        } else if (currentToken.type == TokenType.LET) {
            letStatement();
        } else {
            throw new Error("syntax error");
        }
    }
    
    void statements() {
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }
    
    public void parse() {
        statements();
    }
    
    public String output() {
        return String.join(System.getProperty("line.separator"), output);
    }
}