package analyzer;

import java_cup.runtime.Symbol;
import managefiles.ManageFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.*;

public class Analyzer {
    private JPanel rootPanel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton btnLexicalAnalysis;
    private JTextArea textArea3;
    private JButton btnSyntacticAnalysis;
    private JButton btnUploadFile;

    public Analyzer() {
        btnLexicalAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int count = 1;
                File file = new File("textForLexicalAnalysis.txt");
                PrintWriter writer;

                try {
                    writer = new PrintWriter(file);
                    writer.print(textArea1.getText());
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Reader readerForLexer = new BufferedReader(new FileReader("textForLexicalAnalysis.txt"));
                    Lexer lexer = new Lexer(readerForLexer);
                    String result = "";
                    while (true) {
                        TokenType token = lexer.yylex();
                        if (token == null) {
                            result += "EOF";
                            textArea2.setText(result);
                            return;
                        }
                        switch (token) {
                            case Linea:
                                count++;
                                result += "Linea: " + count + "\n";
                                break;
                            case Binario:
                                result += "Binario: " + lexer.lexeme + " Es un numero binario" + "\n";
                                break;
                            case BinarioPar:
                                result += "Par: " + lexer.lexeme + " Es un numero binario par" + "\n";
                                break;
                            case BinarioImpar:
                                result += "Impar: " + lexer.lexeme + " Es un numero binario impar" + "\n";
                                break;
                            default:
                                result += "  < " + lexer.lexeme + " >\n";
                                break;
                        }
                        textArea2.setText(result);
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btnSyntacticAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String textToAnalyze = textArea1.getText();
                Sintax stx = new Sintax(new MathLexer(new StringReader(textToAnalyze)));
                try {
                    stx.parse();
                    textArea3.setText("Analisis Sintactico Correcto");
                    textArea3.setForeground(new Color(25, 111, 61));
                } catch (Exception ex) {
                    Symbol sym = stx.getS();
                    textArea3.setText("Error de Sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1) + " Texto: \"" + sym.value + "\"");
                    textArea3.setForeground(Color.red);
                }
            }
        });
        btnUploadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String filePath = ManageFiles.chooseFile();
                if (filePath != null) {
                    try {
                        String extractedText = ManageFiles.readFile(filePath);
                        textArea1.setText(extractedText);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analyzer");
        frame.setContentPane(new Analyzer().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
