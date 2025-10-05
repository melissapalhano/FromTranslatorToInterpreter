public class App {
    public static void main(String[] args) {
        /*
        String input = """
        push 10
        push 20
        add
        pop a
        push 45
        push a
        sub
        print
        """;
        */
        String input = "let a = 42 + 2;\nlet b = 15 + 3;\nprint a + b;";

        Parser p = new Parser(input.getBytes());
        p.parse();
        Interpretador i = new Interpretador(p.output());
        i.run();
    }
}